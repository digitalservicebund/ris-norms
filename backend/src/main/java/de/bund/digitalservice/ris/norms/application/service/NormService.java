package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
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
    LoadNormUseCase,
    LoadRegelungstextXmlUseCase,
    UpdateRegelungstextXmlUseCase,
    UpdateModUseCase,
    LoadRegelungstextUseCase {

  private final LoadNormPort loadNormPort;
  private final UpdateNormPort updateNormPort;
  private final SingleModValidator singleModValidator;
  private final UpdateNormService updateNormService;
  private final TimeMachineService timeMachineService;
  private final LoadRegelungstextPort loadRegelungstextPort;

  public NormService(
    LoadNormPort loadNormPort,
    UpdateNormPort updateNormPort,
    SingleModValidator singleModValidator,
    UpdateNormService updateNormService,
    TimeMachineService timeMachineService,
    LoadRegelungstextPort loadRegelungstextPort
  ) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
    this.singleModValidator = singleModValidator;
    this.updateNormService = updateNormService;
    this.timeMachineService = timeMachineService;
    this.loadRegelungstextPort = loadRegelungstextPort;
  }

  @Override
  public Norm loadNorm(final LoadNormUseCase.Query query) {
    return loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));
  }

  @Override
  public Regelungstext loadRegelungstext(final LoadRegelungstextUseCase.Query query) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));
  }

  @Override
  public String loadRegelungstextXml(final LoadRegelungstextXmlUseCase.Query query) {
    final Regelungstext regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));

    return XmlMapper.toString(regelungstext.getDocument());
  }

  @Override
  public String updateRegelungstextXml(UpdateRegelungstextXmlUseCase.Query query) {
    var regelungstextToBeUpdated = new Regelungstext(XmlMapper.toDocument(query.xml()));

    var existingNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli().asNormEli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().asNormEli().toString()));
    var existingRegelungstext = existingNorm
      .getRegelungstextByEli(query.eli())
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));

    if (
      !existingRegelungstext.getExpressionEli().equals(regelungstextToBeUpdated.getExpressionEli())
    ) {
      throw new InvalidUpdateException("Changing the ELI is not supported.");
    }

    if (!existingRegelungstext.getGuid().equals(regelungstextToBeUpdated.getGuid())) {
      throw new InvalidUpdateException("Changing the GUID is not supported.");
    }

    var regelungstexte = new HashSet<>(existingNorm.getRegelungstexte());
    regelungstexte.remove(existingRegelungstext);
    regelungstexte.add(regelungstextToBeUpdated);
    existingNorm.setRegelungstexte(regelungstexte);

    var updatedNorm = updateNorm(existingNorm).get(query.eli().asNormEli());
    var updatedRegelungstext = updatedNorm.getRegelungstextByEli(query.eli()).orElseThrow();

    return XmlMapper.toString(updatedRegelungstext.getDocument());
  }

  /**
   * It not only saves a {@link Norm} but makes sure that all Eids are consistent and if it is an amending norm makes sure
   * that all target norms have a passive modification for every active modification in the amending norm.
   *
   * @param normToBeUpdated the norm which shall be saved
   * @return An {@link Map} containing the updated and saved {@link Norm}
   * @throws NormNotFoundException if the norm cannot be found
   */
  public Map<NormExpressionEli, Norm> updateNorm(Norm normToBeUpdated) {
    // Collect all target norms' ELI without duplications
    Set<NormExpressionEli> allTargetLawsEli = normToBeUpdated
      .getRegelungstext1()
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .map(TextualMod::getDestinationHref)
      .flatMap(Optional::stream)
      .map(Href::getExpressionEli)
      .flatMap(Optional::stream)
      .map(DokumentExpressionEli::asNormEli)
      .collect(Collectors.toSet());

    // Load all target norms
    Map<NormExpressionEli, Norm> zf0s = allTargetLawsEli
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
    Map<NormExpressionEli, Norm> updatedNorms = new HashMap<>(zf0s);
    updatedNorms.put(normToBeUpdated.getExpressionEli(), normToBeUpdated);

    return updatedNorms
      .entrySet()
      .stream()
      .map(entry -> {
        Norm norm = entry.getValue();
        norm
          .getRegelungstexte()
          .forEach(regelungstext -> {
            EidConsistencyGuardian.eliminateDeadReferences(regelungstext.getDocument());
            EidConsistencyGuardian.correctEids(regelungstext.getDocument());
          });

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
          Pair.of(
            ValidationException.FieldName.ELI,
            amendingNorm.getRegelungstext1ExpressionEli().toString()
          )
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
      new UpdatePassiveModificationsUseCase.Query(zf0Norm, amendingNorm, targetNormEli.asNormEli())
    );

    // Validate changes on the future version valid one day before the time boundary of this mod
    final Instant atDate = amendingNorm
      .getTimeBoundaries()
      .stream()
      .filter(timeBoundary -> timeBoundary.getTemporalGroupEid().equals(timeBoundaryEId))
      .findFirst()
      .map(filtered -> filtered.getEventRef().getDate())
      .flatMap(date -> date.map(d -> d.minusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant()))
      .orElse(Instant.MIN);
    final Regelungstext futureVersionAtDate = timeMachineService.applyPassiveModifications(
      new ApplyPassiveModificationsUseCase.Query(zf0Norm.getRegelungstext1(), atDate)
    );
    singleModValidator.validate(futureVersionAtDate, selectedMod);
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
