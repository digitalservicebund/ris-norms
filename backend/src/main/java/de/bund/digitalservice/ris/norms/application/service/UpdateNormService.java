package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.UpdateActiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
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
  private void removePassiveModificationsThatStemFromSource(
    Norm norm,
    ExpressionEli sourceNormEli
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
        norm.deleteByEId(passiveModification.getEid().orElseThrow());

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
    removePassiveModificationsThatStemFromSource(norm, query.amendingNorm().getExpressionEli());
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
          .filter(eli -> eli.equals(query.targetNormEli()))
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
          temporalGroup.getEid().orElseThrow()
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
            .setEli(query.amendingNorm().getExpressionEli())
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

  @Override
  public Norm updateOneActiveModification(UpdateActiveModificationsUseCase.Query query) {
    final Norm amendingNorm = query.amendingNorm();

    // Edit mod in meta
    amendingNorm
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .filter(activeMod ->
        activeMod.getSourceHref().flatMap(Href::getEId).equals(Optional.of(query.eId()))
      )
      .findFirst()
      .ifPresent(activeMod -> {
        if (query.destinationHref() != null) {
          activeMod.setDestinationHref(query.destinationHref().toString());
        } else {
          activeMod.setDestinationHref(null);
        }
        activeMod.setForcePeriodEid(query.timeBoundaryEid());
        if (query.destinationUpTo() != null) {
          activeMod.setDestinationUpTo(query.destinationUpTo().toString());
        } else {
          activeMod.setDestinationUpTo(null);
        }
      });

    // Edit mod in meta
    amendingNorm
      .getMods()
      .stream()
      .filter(mod -> mod.getEid().isPresent() && mod.getEid().get().equals(query.eId()))
      .findFirst()
      .ifPresent(inTextMod -> {
        if (
          inTextMod.usesQuotedText() &&
          !query.newContent().equals(inTextMod.getNewText().orElse(""))
        ) {
          inTextMod.setTargetRefHref(query.destinationHref().toString());
          inTextMod.setNewText(query.newContent());
        }
        if (inTextMod.usesQuotedStructure()) {
          if (inTextMod.hasRref()) {
            updateQuotedStructureSubstitutionRangeTarget(query, inTextMod);
          } else {
            updateQuotedStructureSubstitutionSingleTarget(query, inTextMod);
          }
        }
      });
    return amendingNorm;
  }

  private static void updateQuotedStructureSubstitutionSingleTarget(
    final UpdateActiveModificationsUseCase.Query query,
    final Mod inTextMod
  ) {
    if (
      query.destinationUpTo() != null && StringUtils.isNotEmpty(query.destinationUpTo().toString())
    ) {
      inTextMod.replaceRefWithRref(
        query.destinationHref().toString(),
        query.destinationUpTo().toString()
      );
    } else if (query.destinationHref() != null) {
      inTextMod.setTargetRefHref(query.destinationHref().toString());
    } else {
      inTextMod.setTargetRefHref(null);
    }
  }

  private static void updateQuotedStructureSubstitutionRangeTarget(
    final UpdateActiveModificationsUseCase.Query query,
    final Mod inTextMod
  ) {
    if (
      query.destinationUpTo() != null && StringUtils.isNotEmpty(query.destinationUpTo().toString())
    ) {
      inTextMod.setTargetRrefFrom(query.destinationHref().toString());
      inTextMod.setTargetRrefUpTo(query.destinationUpTo().toString());
    } else {
      inTextMod.replaceRrefWithRef(query.destinationHref().toString());
    }
  }
}
