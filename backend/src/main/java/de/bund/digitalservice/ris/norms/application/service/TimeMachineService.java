package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

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

    norm.getPassiveModifications().stream()
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
                        .orElseThrow()))
        .flatMap(
            (TextualMod passiveModification) -> {
              var sourceEli =
                  passiveModification.getSourceHref().flatMap(Href::getEli).orElseThrow();

              Norm amendingLaw;
              if (customNorms.containsKey(sourceEli)) {
                amendingLaw = customNorms.get(sourceEli);
              } else {
                amendingLaw =
                    normService.loadNorm(new LoadNormUseCase.Query(sourceEli)).orElseThrow();
              }

              var sourceEid = passiveModification.getSourceHref().flatMap(Href::getEId);
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

              final var modifiedTextContent =
                  nodeToChange
                      .get()
                      .getTextContent()
                      .replaceFirst(mod.getOldText().get(), mod.getNewText().get());
              nodeToChange.get().setTextContent(modifiedTextContent);
            });

    return norm;
  }
}
