package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/**
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying LDML.de
 * "modifications" to LDML.de files. For details on LDML.de modifications, cf. <a href=
 * "https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads">LDML-details</a>
 */
@Service
@Slf4j
public class TimeMachineService implements ApplyPassiveModificationsUseCase {

  private final NormService normService;

  public TimeMachineService(NormService normService) {
    this.normService = normService;
  }

  /**
   * Applies the passive modifications of the norm. Only applies "aenderungsbefehl-ersetzen".
   *
   * @param query An ApplyPassiveModificationsUsecase.Query containing the norm and a date
   * @return the Norm with the applied passive modifications that are in effect before the date
   */
  public Norm applyPassiveModifications(ApplyPassiveModificationsUseCase.Query query) {

    var norm = query.norm();
    var date = query.date();
    var customNorms =
        query.customNorms().stream()
            .collect(Collectors.toMap(Norm::getEli, customNorm -> customNorm));

    var actualDate = date.equals(Instant.MAX) ? Instant.MAX : date.plus(Duration.ofDays(1));

    try {
      norm.getMeta();
    } catch (final MandatoryNodeNotFoundException e) {
      return norm;
    }

    norm.getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
        .filter(
            (TextualMod passiveModification) -> {
              final var startDate =
                  passiveModification
                      .getForcePeriodEid()
                      .flatMap(norm::getStartDateForTemporalGroup)
                      .map(dateString -> Instant.parse(dateString + "T00:00:00.000Z"));

              // when no start date exists we do not want to apply the mod
              return startDate.isPresent() && startDate.get().isBefore(actualDate);
            })
        .sorted(
            Comparator.comparing(
                (TextualMod passiveModification) ->
                    passiveModification
                        .getForcePeriodEid()
                        .flatMap(norm::getStartDateForTemporalGroup)
                        .orElseThrow(
                            () ->
                                new ValidationException(
                                    "Did not find a start date for textual mod with eId %s"
                                        .formatted(passiveModification.getEid())))))
        .flatMap(
            (TextualMod passiveModification) -> {
              var sourceEli =
                  passiveModification
                      .getSourceHref()
                      .flatMap(Href::getEli)
                      .orElseThrow(
                          () ->
                              new ValidationException(
                                  "Did not find source href for textual mod with eId %s"
                                      .formatted(passiveModification.getEid())));

              Norm amendingLaw;
              if (customNorms.containsKey(sourceEli)) {
                amendingLaw = customNorms.get(sourceEli);
              } else {
                amendingLaw =
                    normService
                        .loadNorm(new LoadNormUseCase.Query(sourceEli))
                        .orElseThrow(() -> new NormNotFoundException(sourceEli));
              }

              var sourceEid = passiveModification.getSourceHref().flatMap(Href::getEId);
              return amendingLaw.getMods().stream().filter(mod -> mod.getEid().equals(sourceEid));
            })
        .forEach(
            mod -> {
              try {
                String targetEid =
                    mod.getTargetHref()
                        .orElseThrow(
                            () ->
                                new MandatoryNodeNotFoundException(
                                    "./ref/@href", mod.getMandatoryEid(), norm.getEli()))
                        .getEId()
                        .orElseThrow(
                            () ->
                                new MandatoryNodeNotFoundException(
                                    "eId in href", mod.getMandatoryEid(), norm.getEli()));

                final Node targetNode =
                    NodeParser.getMandatoryNodeFromExpression(
                        String.format("//*[@eId='%s']", targetEid), norm.getDocument());

                if (mod.usesQuotedText()) applyQuotedText(mod, targetNode);

              } catch (final MandatoryNodeNotFoundException e) {
                log.debug("Mandatory Node not found: ", e);
              }
            });

    return norm;
  }

  private void applyQuotedText(Mod mod, Node targetNode) {
    String oldText =
        mod.getOldText()
            .orElseThrow(
                () -> new MandatoryNodeNotFoundException("normalize-space(./quotedText[1])"));
    String newText =
        mod.getNewText()
            .orElseThrow(
                () -> new MandatoryNodeNotFoundException("normalize-space(./quotedText[2])"));

    String xPathOldText = String.format("//*[text()[contains(.,'%s')]]", oldText);
    final Node nodeToChange = NodeParser.getMandatoryNodeFromExpression(xPathOldText, targetNode);

    final String modifiedTextContent = nodeToChange.getTextContent().replaceFirst(oldText, newText);
    nodeToChange.setTextContent(modifiedTextContent);
  }
}
