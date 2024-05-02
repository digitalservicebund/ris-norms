package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TimeMachineUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.PassiveModification;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying LDML.de
 * "modifications" to LDML.de files. For details on LDML.de modifications, cf. <a href=
 * "https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads">LDML-details</a>
 */
@Service
public class TimeMachineService implements TimeMachineUseCase {

  private final XmlDocumentService xmlDocumentService;
  private final NormService normService;

  public TimeMachineService(XmlDocumentService xmlDocumentService, NormService normService) {
    this.xmlDocumentService = xmlDocumentService;
    this.normService = normService;
  }

  @Override
  public Optional<String> applyTimeMachine(TimeMachineUseCase.Query query) {
    return normService
        .loadNormXml(new LoadNormXmlUseCase.Query(query.targetLawEli()))
        .map(targetNormXml -> this.apply(query.amendingLawXml(), targetNormXml));
  }

  /**
   * Applies the modifications of the amending law onto the target law.
   *
   * @param amendingLawString a Document that contains LDML.de modifications to be applied on the
   *     target law
   * @param targetLawString The Document that the modifications will be applied to
   * @return the Document that results in applying the amending law's modifications to the target
   *     law
   */
  public String apply(final String amendingLawString, final String targetLawString) {

    final Document amendingLaw = XmlMapper.toDocument(amendingLawString);
    final Document targetLaw = XmlMapper.toDocument(targetLawString);

    final Node firstModificationNodeInAmendingLaw =
        xmlDocumentService.getFirstModification(amendingLaw);

    final Document appliedTargetLaw =
        applyOneSubstitutionModification(firstModificationNodeInAmendingLaw, targetLaw);

    return XmlMapper.toString(appliedTargetLaw);
  }

  /**
   * Applies one substitution modification
   *
   * @param modificationNode the node containing the substitution
   * @param targetLaw the document version of the target law
   * @return the <code>targetLaw</code> with the applied modification
   */
  private Document applyOneSubstitutionModification(Node modificationNode, Document targetLaw) {
    final Document targetLawClone = xmlDocumentService.cloneDocument(targetLaw);

    final XmlDocumentService.ReplacementPair replacementPair =
        xmlDocumentService.getReplacementPair(modificationNode);

    final Node targetLawNodeToBeModified =
        xmlDocumentService.findTargetLawNodeToBeModified(targetLawClone, modificationNode);

    try {
      final String modifiedTextContent =
          targetLawNodeToBeModified
              .getTextContent()
              .replaceFirst(replacementPair.oldText(), replacementPair.newText());
      targetLawNodeToBeModified.setTextContent(modifiedTextContent);
      return targetLawClone;
    } catch (NullPointerException e) {
      throw new XmlProcessingException(
          "Target Law could not be modified since there is no according paragraph found", e);
    }
  }

  /**
   * Applies the passive modifications of the norm. Only applies "aenderungsbefehl-ersetzen".
   *
   * @param norm a Norm
   * @param date a date
   * @return the Norm with the applied passive modifications that are in effect before the date
   */
  public Norm applyPassiveModifications(Norm norm, Instant date) {
    var actualDate = date.equals(Instant.MAX) ? Instant.MAX : date.plus(Duration.ofDays(1));

    norm.getPassiveModifications().stream()
        .filter(
            (PassiveModification passiveModification) ->
                Instant.parse(
                        passiveModification
                                .getForcePeriodEid()
                                .flatMap(norm::getStartDateForTemporalGroup)
                                .orElseThrow()
                            + "T00:00:00.000Z")
                    .isBefore(actualDate))
        .sorted(
            Comparator.comparing(
                (PassiveModification passiveModification) ->
                    passiveModification
                        .getForcePeriodEid()
                        .flatMap(norm::getStartDateForTemporalGroup)
                        .orElseThrow()))
        .flatMap(
            (PassiveModification passiveModification) -> {
              var sourceEli = passiveModification.getSourceEli().orElseThrow();
              var amendingLaw =
                  normService.loadNorm(new LoadNormUseCase.Query(sourceEli)).orElseThrow();
              var sourceEid = passiveModification.getSourceEid();
              return amendingLaw.getMods().stream().filter(mod -> mod.getEid().equals(sourceEid));
            })
        .forEach(
            mod -> {
              if (mod.getTargetEid().isEmpty()
                  || mod.getOldText().isEmpty()
                  || mod.getNewText().isEmpty()) {
                return;
              }

              final var targetEid = mod.getTargetEid().get();
              final var targetNode =
                  NodeParser.getNodeFromExpression(
                      String.format("//*[@eId='%s']", targetEid), norm.getDocument());

              if (targetNode == null) {
                return;
              }

              final var nodeToChange =
                  NodeParser.getNodeFromExpression(
                      String.format(".//*[contains(text(),'%s')]", mod.getOldText().get()),
                      targetNode);

              if (nodeToChange == null) {
                return;
              }

              final var modifiedTextContent =
                  nodeToChange
                      .getTextContent()
                      .replaceFirst(mod.getOldText().get(), mod.getNewText().get());
              nodeToChange.setTextContent(modifiedTextContent);
            });

    return norm;
  }
}
