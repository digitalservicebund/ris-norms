package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/**
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying LDML.de
 * "modifications" to LDML.de files. For details on LDML.de modifications, cf. <a href=
 * "https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads">LDML-details</a>
 */
@Service
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
              if (mod.getTargetHref().isEmpty()
                  || mod.getTargetHref().get().getEId().isEmpty()
                  || mod.getOldText().isEmpty()
                  || mod.getNewContent().isEmpty()) {
                return;
              }

              final var targetEid = mod.getTargetHref().get().getEId().get();
              final var targetNode =
                  NodeParser.getNodeFromExpression(
                      String.format("//*[@eId='%s']", targetEid), norm.getDocument());

              if (targetNode.isEmpty()) {
                return;
              }

              final var nodeToChange =
                  NodeParser.getNodeFromExpression(
                      String.format("//*[text()[contains(.,'%s')]]", mod.getOldText().get()),
                      targetNode.get());

              if (nodeToChange.isEmpty()) {
                return;
              }

              var childNodeToModify =
                  findFirstDeepestChildNodeWithText(nodeToChange.get(), mod.getOldText().get());

              childNodeToModify.ifPresent(
                  childNode ->
                      childNode.setTextContent(
                          childNode
                              .getTextContent()
                              .replaceFirst(mod.getOldText().get(), mod.getNewContent().get())));
            });

    return norm;
  }

  private Optional<Node> findFirstDeepestChildNodeWithText(Node node, String textToFind) {
    if (node.hasChildNodes()) {
      return NodeParser.nodeListToList(node.getChildNodes()).stream()
          .flatMap(childNode -> findFirstDeepestChildNodeWithText(childNode, textToFind).stream())
          .findFirst();
    }

    if (node.getTextContent().contains(textToFind)) {
      return Optional.of(node);
    }

    return Optional.empty();
  }
}
