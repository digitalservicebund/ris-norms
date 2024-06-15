package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.UpdateActiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.EventRefType;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

/** Service for updating norms. */
@Service
public class UpdateNormService
    implements UpdatePassiveModificationsUseCase, UpdateActiveModificationsUseCase {
  /**
   * Remove passive modifications form the zf0Norm, who originate from the zf0Norm with the given
   * source.
   *
   * @param norm the zf0Norm from which to remove passive modifications
   * @param sourceNormEli the eli which the removed passive modifications should have as a source
   */
  private void removePassiveModificationsThatStemFromSource(Norm norm, String sourceNormEli) {

    norm.getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
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
                  .flatMap(m -> m.getTimeInterval().getEventRefEId())
                  .ifPresent(norm::deleteEventRefIfUnused);
            });
  }

  @Override
  public Norm updatePassiveModifications(UpdatePassiveModificationsUseCase.Query query) {
    var norm = query.zf0Norm();

    // clean up existing passive modifications stemming from the amending zf0Norm
    removePassiveModificationsThatStemFromSource(norm, query.amendingNorm().getEli());

    final var activeModificationsToAdd =
        query
            .amendingNorm()
            .getMeta()
            .getAnalysis()
            .map(analysis -> analysis.getActiveModifications().stream())
            .orElse(Stream.empty())
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
              final var startDate =
                  query
                      .amendingNorm()
                      .getStartDateForTemporalGroup(forcePeriodEid)
                      .map(LocalDate::parse);

              if (startDate.isEmpty()) {
                return;
              }

              final var temporalGroup =
                  norm.addTimeBoundary(startDate.get(), EventRefType.AMENDMENT);
              amendingNormTemporalGroupEidsToNormTemporalGroupEids.put(
                  forcePeriodEid, temporalGroup.getEid().orElseThrow());
            });

    // create the passive modifications
    activeModificationsToAdd.forEach(
        activeModification ->
            norm.getMeta()
                .getOrCreateAnalysis()
                .addPassiveModification(
                    activeModification.getType().orElseThrow(),
                    new Href.Builder()
                        .setEli(query.amendingNorm().getEli())
                        .setEId(
                            activeModification.getSourceHref().flatMap(Href::getEId).orElseThrow())
                        .setFileExtension("xml")
                        .buildAbsolute()
                        .value(),
                    new Href.Builder()
                        .setEId(
                            activeModification
                                .getDestinationHref()
                                .flatMap(Href::getEId)
                                .orElseThrow())
                        .setCharacterRange(
                            activeModification
                                .getDestinationHref()
                                .flatMap(Href::getCharacterRange)
                                .orElseThrow())
                        .buildInternalReference()
                        .value(),
                    activeModification
                        .getForcePeriodEid()
                        .map(amendingNormTemporalGroupEidsToNormTemporalGroupEids::get)
                        .map(eId -> new Href.Builder().setEId(eId).buildInternalReference().value())
                        .orElse(null)));

    return norm;
  }

  @Override
  public Norm updateActiveModifications(UpdateActiveModificationsUseCase.Query query) {
    final Norm amendingNorm = query.amendingNorm();
    // Edit mod in meta
    amendingNorm
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getActiveModifications().stream())
        .orElse(Stream.empty())
        .filter(
            activeMod ->
                activeMod.getSourceHref().flatMap(Href::getEId).equals(Optional.of(query.eId())))
        .findFirst()
        .ifPresent(
            activeMod -> {
              activeMod.setDestinationHref(query.destinationHref());
              activeMod.setForcePeriodEid(query.timeBoundaryEid());
            });

    // Edit mod in body
    amendingNorm.getMods().stream()
        .filter(mod -> mod.getEid().isPresent() && mod.getEid().get().equals(query.eId()))
        .findFirst()
        .ifPresent(
            mod -> {
              mod.setTargetHref(query.destinationHref());
              mod.setNewText(query.newText());
            });
    return amendingNorm;
  }
}
