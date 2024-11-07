package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class NormService
  implements
    LoadNormUseCase, LoadNormXmlUseCase, UpdateNormXmlUseCase, UpdateModUseCase, UpdateModsUseCase {

  private final LoadNormPort loadNormPort;
  private final UpdateNormPort updateNormPort;
  private final SingleModValidator singleModValidator;
  private final UpdateNormService updateNormService;
  private final TimeMachineService timeMachineService;

  public NormService(
    LoadNormPort loadNormPort,
    UpdateNormPort updateNormPort,
    SingleModValidator singleModValidator,
    UpdateNormService updateNormService,
    TimeMachineService timeMachineService
  ) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
    this.singleModValidator = singleModValidator;
    this.updateNormService = updateNormService;
    this.timeMachineService = timeMachineService;
  }

  @Override
  public Norm loadNorm(final LoadNormUseCase.Query query) {
    return loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));
  }

  @Override
  public String loadNormXml(final LoadNormXmlUseCase.Query query) {
    final Norm norm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));

    return XmlMapper.toString(norm.getDocument());
  }

  @Override
  public String updateNormXml(UpdateNormXmlUseCase.Query query) {
    var existingNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));

    var normToBeUpdated = Norm.builder().document(XmlMapper.toDocument(query.xml())).build();

    if (!existingNorm.getExpressionEli().equals(normToBeUpdated.getExpressionEli())) {
      throw new InvalidUpdateException("Changing the ELI is not supported.");
    }

    if (!existingNorm.getGuid().equals(normToBeUpdated.getGuid())) {
      throw new InvalidUpdateException("Changing the GUID is not supported.");
    }
    var updatedNorm = updateNorm(normToBeUpdated);

    return XmlMapper.toString(updatedNorm.get(query.eli()).getDocument());
  }

  /**
   * It not only saves a {@link Norm} but makes sure that all Eids are consistent and if it is an amending norm makes sure
   * that all target norms have a passive modification for every active modification in the amending norm.
   *
   * @param normToBeUpdated the norm which shall be saved
   * @return An {@link Map} containing the updated and saved {@link Norm}
   * @throws NormNotFoundException if the norm cannot be found
   */
  public Map<ExpressionEli, Norm> updateNorm(Norm normToBeUpdated) {
    // Collect all target norms' ELI without duplications
    Set<ExpressionEli> allTargetLawsEli = normToBeUpdated
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .map(TextualMod::getDestinationHref)
      .flatMap(Optional::stream)
      .map(Href::getExpressionEli)
      .flatMap(Optional::stream)
      .collect(Collectors.toSet());

    // Load all target norms
    Map<ExpressionEli, Norm> zf0s = allTargetLawsEli
      .stream()
      .map(expressionEli -> {
        Norm zf0 = loadNorm(new LoadNormUseCase.Query(expressionEli));
        return new AbstractMap.SimpleImmutableEntry<>(expressionEli, zf0);
      })
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    // Update passive modifications for each target norm
    zf0s.forEach((eli, zf0) ->
      updateNormService.updateOnePassiveModification(
        new UpdatePassiveModificationsUseCase.Query(zf0, normToBeUpdated, eli)
      )
    );

    // Add the norm to be updated to the map of updated norms
    Map<ExpressionEli, Norm> updatedNorms = new HashMap<>(zf0s);
    updatedNorms.put(normToBeUpdated.getExpressionEli(), normToBeUpdated);

    return updatedNorms
      .entrySet()
      .stream()
      .map(entry -> {
        Norm norm = entry.getValue();
        EidConsistencyGuardian.eliminateDeadReferences(norm.getDocument());
        EidConsistencyGuardian.correctEids(norm.getDocument());

        Norm savedNorm = updateNormPort
          .updateNorm(new UpdateNormPort.Command(norm))
          .orElseThrow(() -> new NormNotFoundException(norm.getManifestationEli().toString()));

        return new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), savedNorm);
      })
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Updates the akn:mod, akn:activeModifications and akn:passiveModifications with the given eId.
   * The amendingNorm and zf0Norm are updated in place.
   *
   * @param amendingNorm the norm in which the akn:mod exists
   * @param zf0Norm the zf0 version of the norm targeted by the akn:mod
   * @param eId the eId of the akn:mod
   * @param destinationHref the new destination href of the akn:mod
   * @param destinationUpTo the last element that should be replaced
   * @param timeBoundaryEId the eid of the new time-boundary of the akn:mod
   * @param newContent the new future text of the akn:mod
   */
  private void updateModInPlace(
    Norm amendingNorm,
    Norm zf0Norm,
    String eId,
    Href destinationHref,
    @Nullable Href destinationUpTo,
    String timeBoundaryEId,
    String newContent
  ) {
    var targetNormEli = destinationHref
      .getExpressionEli()
      .orElseThrow(() ->
        new ValidationException(
          ValidationException.ErrorType.ELI_NOT_IN_HREF,
          Pair.of(ValidationException.FieldName.DESTINATION_HREF, destinationHref.toString())
        )
      );

    final Mod selectedMod = amendingNorm
      .getMods()
      .stream()
      .filter(m -> m.getEid().equals(eId))
      .findFirst()
      .orElseThrow(() ->
        new ValidationException(
          ValidationException.ErrorType.META_MOD_NOT_FOUND,
          Pair.of(ValidationException.FieldName.EID, eId),
          Pair.of(ValidationException.FieldName.ELI, amendingNorm.getExpressionEli().toString())
        )
      );

    // Updates one active mod (meta and body) in amending law
    updateNormService.updateOneActiveModification(
      new UpdateActiveModificationsUseCase.Query(
        amendingNorm,
        eId,
        destinationHref,
        destinationUpTo,
        timeBoundaryEId,
        newContent
      )
    );

    updateNormService.updateOnePassiveModification(
      new UpdatePassiveModificationsUseCase.Query(zf0Norm, amendingNorm, targetNormEli)
    );

    // Validate changes on the future version valid one day before the time boundary of this mod
    final Instant atDate = amendingNorm
      .getTimeBoundaries()
      .stream()
      .filter(timeBoundary ->
        timeBoundary.getTemporalGroupEid().isPresent() &&
        timeBoundary.getTemporalGroupEid().get().equals(timeBoundaryEId)
      )
      .findFirst()
      .map(filtered -> filtered.getEventRef().getDate())
      .flatMap(date -> date.map(d -> d.minusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant()))
      .orElse(Instant.MIN);
    final Norm futureVersionAtDate = timeMachineService.applyPassiveModifications(
      new ApplyPassiveModificationsUseCase.Query(zf0Norm, atDate)
    );
    singleModValidator.validate(futureVersionAtDate, selectedMod);
  }

  @Override
  public UpdateModsUseCase.Result updateMods(UpdateModsUseCase.Query query) {
    final Norm amendingNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));

    final String queryModEId = query.mods().stream().findAny().orElseThrow().eId();
    final Mod modObject = amendingNorm
      .getNodeByEId(queryModEId)
      .map(Mod::new)
      .orElseThrow(() ->
        new InvalidUpdateException(
          "Mod with eId %s not found in amending law %s".formatted(
              queryModEId,
              amendingNorm.getExpressionEli()
            )
        )
      );

    final ExpressionEli targetNormEli = modObject
      .getTargetRefHref()
      .or(modObject::getTargetRrefFrom)
      .flatMap(Href::getExpressionEli)
      .orElseThrow(() ->
        new InvalidUpdateException("No eli found in href of mod %s".formatted(queryModEId))
      );

    if (
      !query
        .mods()
        .stream()
        .allMatch(modData -> {
          final var modNode = amendingNorm.getNodeByEId(modData.eId()).map(Mod::new);
          final var eli = modNode
            .flatMap(mod -> mod.getTargetRefHref().or(mod::getTargetRrefFrom))
            .flatMap(Href::getExpressionEli)
            .orElseThrow(() ->
              new InvalidUpdateException("No eli found in href of mod %s".formatted(modData.eId()))
            );
          return eli.equals(targetNormEli);
        })
    ) {
      throw new InvalidUpdateException(
        "Currently not supported: Not all mods have the same target norm"
      );
    }

    final Norm targetNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(targetNormEli))
      .orElseThrow(() -> new NormNotFoundException(targetNormEli.toString()));

    query
      .mods()
      .forEach(newModData -> {
        final Mod mod = amendingNorm.getNodeByEId(newModData.eId()).map(Mod::new).orElseThrow();

        this.updateModInPlace(
            amendingNorm,
            targetNorm,
            newModData.eId(),
            mod.getTargetRefHref().or(mod::getTargetRrefFrom).orElse(null),
            null,
            newModData.timeBoundaryEId(),
            mod.getNewText().orElse(null)
          );
      });

    // Don't save changes when dryRun (when preview is being generated but changes not saved)
    if (!query.dryRun()) {
      // Since we only update time boundaries here (and not destination elis) it is fine to only update the zf0s and
      // not create them when they are missing
      updateNorm(amendingNorm);
    }

    return new UpdateModsUseCase.Result(
      XmlMapper.toString(amendingNorm.getDocument()),
      XmlMapper.toString(targetNorm.getDocument())
    );
  }

  @Override
  public UpdateModUseCase.Result updateMod(UpdateModUseCase.Query query) {
    final Norm amendingNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));

    final var targetNormEli = query
      .destinationHref()
      .getExpressionEli()
      .orElseThrow(() ->
        new ValidationException(
          ValidationException.ErrorType.ELI_NOT_IN_HREF,
          Pair.of(
            ValidationException.FieldName.DESTINATION_HREF,
            query.destinationHref().toString()
          )
        )
      );

    final Norm targetNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(targetNormEli))
      .orElseThrow(() -> new NormNotFoundException(targetNormEli.toString()));

    this.updateModInPlace(
        amendingNorm,
        targetNorm,
        query.eid(),
        query.destinationHref(),
        query.destinationUpTo(),
        query.timeBoundaryEid(),
        query.newContent()
      );

    // Don't save changes when dryRun (when preview is being generated but changes not saved)
    if (!query.dryRun()) {
      updateNorm(amendingNorm);
    }

    return new UpdateModUseCase.Result(
      XmlMapper.toString(amendingNorm.getDocument()),
      XmlMapper.toString(targetNorm.getDocument())
    );
  }
}
