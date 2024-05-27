package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.EventRefType;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TemporalGroup;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Service for updating norms. */
@Service
public class UpdateNormService implements UpdatePassiveModificationsUseCase {
  /**
   * Remove passive modifications form the zf0Norm, who originate from the zf0Norm with the given
   * source.
   *
   * @param norm the zf0Norm from which to remove passive modifications
   * @param sourceNormEli the eli which the removed passive modifications should have as a source
   */
  private void removePassiveModificationsThatStemFromSource(Norm norm, String sourceNormEli) {
    norm.getPassiveModifications().stream()
        .filter(
            passiveModification ->
                passiveModification
                    .getSourceHref()
                    .flatMap(Href::getEli)
                    .equals(Optional.of(sourceNormEli)))
        .forEach(
            passiveModification -> {
              norm.deleteByEId(passiveModification.getEid().orElseThrow());

              final var temporalGroup =
                  passiveModification
                      .getForcePeriodEid()
                      .flatMap(norm::deleteTemporalGroupIfUnused);

              temporalGroup
                  .flatMap(TemporalGroup::getEventRefEId)
                  .ifPresent(norm::deleteEventRefIfUnused);
            });
  }

  @Override
  public Norm updatePassiveModifications(Query query) {
    var norm = query.zf0Norm();

    // clean up existing passive modifications stemming from the amending zf0Norm
    removePassiveModificationsThatStemFromSource(norm, query.amendingNorm().getEli().orElseThrow());

    final var activeModificationsToAdd =
        query.amendingNorm().getActiveModifications().stream()
            .filter(
                activeModification ->
                    activeModification
                        .getDestinationHref()
                        .flatMap(Href::getEli)
                        .filter(eli -> eli.equals(query.targetNormEli()))
                        .isPresent())
            .toList();

    // create temporal groups
    Map<String, String> amendingNormTemporalGroupEidsToNormTemporalGroupEids = new HashMap<>();
    activeModificationsToAdd.stream()
        .flatMap(activeModification -> activeModification.getForcePeriodEid().stream())
        .distinct()
        .forEach(
            forcePeriodEid -> {
              final var temporalGroup =
                  norm.addTimeBoundary(
                      LocalDate.parse(
                          query
                              .amendingNorm()
                              .getStartDateForTemporalGroup(forcePeriodEid)
                              .orElseThrow()),
                      EventRefType.AMENDMENT);

              amendingNormTemporalGroupEidsToNormTemporalGroupEids.put(
                  forcePeriodEid, temporalGroup.getEid().orElseThrow());
            });

    // create the passive modifications
    activeModificationsToAdd.forEach(
        activeModification ->
            norm.addPassiveModification(
                activeModification.getType().orElseThrow(),
                new Href.Builder()
                    .setEli(query.amendingNorm().getEli().orElseThrow())
                    .setEId(activeModification.getSourceHref().flatMap(Href::getEId).orElseThrow())
                    .setFileExtension("xml")
                    .buildAbsolute()
                    .value(),
                new Href.Builder()
                    .setEId(
                        activeModification.getDestinationHref().flatMap(Href::getEId).orElseThrow())
                    .setCharacterRange(
                        activeModification
                            .getDestinationHref()
                            .flatMap(Href::getCharacterRange)
                            .orElseThrow())
                    .buildRelative()
                    .value(),
                activeModification
                    .getForcePeriodEid()
                    .map(amendingNormTemporalGroupEidsToNormTemporalGroupEids::get)
                    .map(eId -> new Href.Builder().setEId(eId).buildRelative().value())
                    .orElse(null)));

    return norm;
  }
}
