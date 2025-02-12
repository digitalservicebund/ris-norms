package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
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
  private void removePassiveModificationsThatStemFromSource(
    Norm norm,
    DokumentExpressionEli sourceNormEli
  ) {
    norm
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getPassiveModifications().stream())
      .orElse(Stream.empty())
      .filter(passiveModification ->
        passiveModification
          .getSourceHref()
          .flatMap(Href::getExpressionEli)
          .equals(Optional.of(sourceNormEli))
      )
      .forEach(passiveModification -> {
        norm.deleteByEId(passiveModification.getEid());

        final var temporalGroup = passiveModification
          .getForcePeriodEid()
          .flatMap(norm::deleteTemporalGroupIfUnused);

        temporalGroup
          .flatMap(m -> m.getTimeInterval().getEventRefEId())
          .ifPresent(norm::deleteEventRefIfUnused);
      });
  }

  @Override
  public Norm updateOnePassiveModification(UpdatePassiveModificationsUseCase.Query query) {
    var norm = query.zf0Norm();

    // clean up existing passive modifications stemming from the amending zf0Norm
    removePassiveModificationsThatStemFromSource(
      norm,
      query.amendingNorm().getRegelungstext1ExpressionEli()
    );
    EidConsistencyGuardian.correctEids(norm.getDocument());

    final List<TextualMod> activeModificationsToAdd = query
      .amendingNorm()
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .filter(activeModification ->
        activeModification
          .getDestinationHref()
          .flatMap(Href::getExpressionEli)
          .filter(eli -> eli.asNormEli().equals(query.targetNormEli()))
          .isPresent()
      )
      .toList();

    // create temporal groups
    Map<String, String> amendingNormTemporalGroupEidsToNormTemporalGroupEids = new HashMap<>();
    activeModificationsToAdd
      .stream()
      .flatMap(activeModification -> activeModification.getForcePeriodEid().stream())
      .distinct()
      .forEach(forcePeriodEid -> {
        final var startDate = query
          .amendingNorm()
          .getStartDateForTemporalGroup(forcePeriodEid)
          .map(LocalDate::parse);

        if (startDate.isEmpty()) {
          return;
        }

        final var temporalGroup = norm.addTimeBoundary(startDate.get(), EventRefType.AMENDMENT);
        amendingNormTemporalGroupEidsToNormTemporalGroupEids.put(
          forcePeriodEid,
          temporalGroup.getEid()
        );
      });

    // create the passive modifications
    activeModificationsToAdd.forEach(activeModification ->
      norm
        .getMeta()
        .getOrCreateAnalysis()
        .addPassiveModification(
          activeModification.getType().orElseThrow(),
          new Href.Builder()
            .setEli(query.amendingNorm().getRegelungstext1ExpressionEli())
            .setEId(activeModification.getSourceHref().flatMap(Href::getEId).orElseThrow())
            .setFileExtension("xml")
            .buildAbsolute()
            .value(),
          new Href.Builder()
            .setEId(activeModification.getDestinationHref().flatMap(Href::getEId).orElseThrow())
            .setCharacterRange(
              activeModification.getDestinationHref().flatMap(Href::getCharacterRange).orElse(null)
            )
            .buildInternalReference()
            .value(),
          activeModification
            .getForcePeriodEid()
            .map(amendingNormTemporalGroupEidsToNormTemporalGroupEids::get)
            .map(eId -> new Href.Builder().setEId(eId).buildInternalReference().value())
            .orElse(null),
          activeModification
            .getDestinationUpTo()
            .map(upToHref ->
              new Href.Builder()
                .setEId(upToHref.getEId().orElseThrow())
                .buildInternalReference()
                .value()
            )
            .orElse(null)
        )
    );
    return norm;
  }
}
