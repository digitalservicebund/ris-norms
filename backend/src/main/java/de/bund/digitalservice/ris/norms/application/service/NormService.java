package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class NormService
    implements LoadNormUseCase,
        LoadNormXmlUseCase,
        UpdateNormXmlUseCase,
        UpdateModUseCase,
        UpdateModsUseCase {
  private final LoadNormPort loadNormPort;
  private final UpdateNormPort updateNormPort;
  private final SingleModValidator singleModValidator;
  private final UpdateNormService updateNormService;
  private final LoadZf0Service loadZf0Service;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;

  public NormService(
      LoadNormPort loadNormPort,
      UpdateNormPort updateNormPort,
      SingleModValidator singleModValidator,
      UpdateNormService updateNormService,
      LoadZf0Service loadZf0Service,
      UpdateOrSaveNormPort updateOrSaveNormPort) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
    this.singleModValidator = singleModValidator;
    this.updateNormService = updateNormService;
    this.loadZf0Service = loadZf0Service;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
  }

  @Override
  public Norm loadNorm(final LoadNormUseCase.Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .orElseThrow(() -> new NormNotFoundException(query.eli()));
  }

  @Override
  public String loadNormXml(final LoadNormXmlUseCase.Query query) {
    final Norm norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    return XmlMapper.toString(norm.getDocument());
  }

  @Override
  public String updateNormXml(UpdateNormXmlUseCase.Query query) {
    var existingNorm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    var normToBeUpdated = Norm.builder().document(XmlMapper.toDocument(query.xml())).build();

    if (!existingNorm.getEli().equals(normToBeUpdated.getEli())) {
      throw new InvalidUpdateException("Changing the ELI is not supported.");
    }

    if (!existingNorm.getGuid().equals(normToBeUpdated.getGuid())) {
      throw new InvalidUpdateException("Changing the GUID is not supported.");
    }
    var updatedNorm =
        updateNormPort
            .updateNorm(new UpdateNormPort.Command(normToBeUpdated))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    return XmlMapper.toString(updatedNorm.getDocument());
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
      String destinationHref,
      String destinationUpTo,
      String timeBoundaryEId,
      String newContent) {
    var targetNormEli = new Href(destinationHref).getEli().orElse("");

    final Mod selectedMod =
        amendingNorm.getMods().stream()
            .filter(m -> m.getEid().equals(eId))
            .findFirst()
            .orElseThrow(
                () ->
                    new ValidationException(
                        ValidationException.ErrorType.META_MOD_NOT_FOUND,
                        Pair.of(ValidationException.FieldName.EID, eId),
                        Pair.of(ValidationException.FieldName.ELI, amendingNorm.getEli())));

    // Update active mods (meta and body) in amending law
    updateNormService.updateActiveModifications(
        new UpdateActiveModificationsUseCase.Query(
            amendingNorm, eId, destinationHref, destinationUpTo, timeBoundaryEId, newContent));

    // Update passiv mods in ZF0
    updateNormService.updatePassiveModifications(
        new UpdatePassiveModificationsUseCase.Query(zf0Norm, amendingNorm, targetNormEli));

    // Validate changes on ZF0
    singleModValidator.validate(zf0Norm, selectedMod);
  }

  @Override
  public UpdateModsUseCase.Result updateMods(UpdateModsUseCase.Query query) {

    final Norm amendingNorm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    final String queryModEId = query.mods().stream().findAny().orElseThrow().eId();
    final Mod modObject =
        amendingNorm
            .getNodeByEId(queryModEId)
            .map(Mod::new)
            .orElseThrow(
                () ->
                    new InvalidUpdateException(
                        "Mod with eId %s not found in amending law %s"
                            .formatted(queryModEId, amendingNorm.getEli())));

    final String targetNormEli =
        modObject
            .getTargetRefHref()
            .or(modObject::getTargetRrefFrom)
            .flatMap(Href::getEli)
            .orElseThrow(
                () ->
                    new InvalidUpdateException(
                        "No eli found in href of mod %s".formatted(queryModEId)));

    if (!query.mods().stream()
        .allMatch(
            modData -> {
              final var modNode = amendingNorm.getNodeByEId(modData.eId()).map(Mod::new);
              final var eli =
                  modNode
                      .flatMap(mod -> mod.getTargetRefHref().or(mod::getTargetRrefFrom))
                      .flatMap(Href::getEli)
                      .orElseThrow(
                          () ->
                              new InvalidUpdateException(
                                  "No eli found in href of mod %s".formatted(modData.eId())));
              return eli.equals(targetNormEli);
            })) {
      throw new InvalidUpdateException(
          "Currently not supported: Not all mods have the same target norm");
    }

    final Norm targetNorm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(targetNormEli))
            .orElseThrow(() -> new NormNotFoundException(targetNormEli));
    final Norm zf0Norm =
        loadZf0Service.loadOrCreateZf0(new LoadZf0UseCase.Query(amendingNorm, targetNorm));

    query
        .mods()
        .forEach(
            newModData -> {
              final Mod mod =
                  amendingNorm.getNodeByEId(newModData.eId()).map(Mod::new).orElseThrow();

              this.updateModInPlace(
                  amendingNorm,
                  zf0Norm,
                  newModData.eId(),
                  mod.getTargetRefHref().or(mod::getTargetRrefFrom).map(Href::value).orElse(null),
                  null,
                  newModData.timeBoundaryEId(),
                  mod.getNewText().orElse(null));
            });

    // Don't save changes when dryRun (when preview is being generated but changes not saved)
    if (!query.dryRun()) {
      updateNormPort.updateNorm(new UpdateNormPort.Command(amendingNorm));
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(zf0Norm));
    }

    return new UpdateModsUseCase.Result(
        XmlMapper.toString(amendingNorm.getDocument()), XmlMapper.toString(zf0Norm.getDocument()));
  }

  @Override
  public UpdateModUseCase.Result updateMod(UpdateModUseCase.Query query) {
    final Norm amendingNorm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    final var targetNormEli =
        new Href(query.destinationHref())
            .getEli()
            .orElseThrow(
                () ->
                    new ValidationException(
                        ValidationException.ErrorType.ELI_NOT_IN_HREF,
                        Pair.of(
                            ValidationException.FieldName.DESTINATION_HREF,
                            query.destinationHref())));

    final Norm targetNorm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(targetNormEli))
            .orElseThrow(() -> new NormNotFoundException(targetNormEli));
    final Norm zf0Norm =
        loadZf0Service.loadOrCreateZf0(new LoadZf0UseCase.Query(amendingNorm, targetNorm));

    this.updateModInPlace(
        amendingNorm,
        zf0Norm,
        query.eid(),
        query.destinationHref(),
        query.destinationUpTo(),
        query.timeBoundaryEid(),
        query.newContent());

    // Don't save changes when dryRun (when preview is being generated but changes not saved)
    if (!query.dryRun()) {
      updateNormPort.updateNorm(new UpdateNormPort.Command(amendingNorm));
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(zf0Norm));
    }

    return new UpdateModUseCase.Result(
        XmlMapper.toString(amendingNorm.getDocument()), XmlMapper.toString(zf0Norm.getDocument()));
  }
}
