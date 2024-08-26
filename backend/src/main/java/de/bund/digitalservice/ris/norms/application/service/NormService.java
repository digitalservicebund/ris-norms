package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        LoadSpecificArticlesXmlFromNormUseCase,
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
  public Optional<Norm> loadNorm(final LoadNormUseCase.Query query) {
    return loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));
  }

  @Override
  public Optional<String> loadNormXml(final LoadNormXmlUseCase.Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(Norm::getDocument)
        .map(XmlMapper::toString);
  }

  @Override
  public Optional<String> updateNormXml(UpdateNormXmlUseCase.Query query)
      throws InvalidUpdateException {
    var existingNorm = loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));

    if (existingNorm.isEmpty()) {
      return Optional.empty();
    }

    var updatedNorm = Norm.builder().document(XmlMapper.toDocument(query.xml())).build();

    if (!existingNorm.get().getEli().equals(updatedNorm.getEli())) {
      throw new UpdateNormXmlUseCase.InvalidUpdateException("Changing the ELI is not supported.");
    }

    if (!existingNorm.get().getGuid().equals(updatedNorm.getGuid())) {
      throw new UpdateNormXmlUseCase.InvalidUpdateException("Changing the GUID is not supported.");
    }

    return updateNormPort
        .updateNorm(new UpdateNormPort.Command(updatedNorm))
        .map(Norm::getDocument)
        .map(XmlMapper::toString);
  }

  @Override
  public List<String> loadSpecificArticlesXmlFromNorm(
      LoadSpecificArticlesXmlFromNormUseCase.Query query) {
    List<Article> articles =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .map(Norm::getArticles)
            .orElse(List.of());

    if (query.refersTo() == null) {
      return articles.stream().map(a -> XmlMapper.toString(a.getNode())).toList();
    } else {
      return articles.stream()
          .filter(a -> Objects.equals(a.getRefersTo().orElse(""), query.refersTo()))
          .map(a -> XmlMapper.toString(a.getNode()))
          .toList();
    }
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
    var targetNormEli = new Href(destinationHref).getEli();
    if (targetNormEli.isEmpty()) {
      throw new ValidationException("The destinationHref does not contain a eli");
    }

    // Update active mods (meta and body) in amending law
    updateNormService.updateActiveModifications(
        new UpdateActiveModificationsUseCase.Query(
            amendingNorm, eId, destinationHref, destinationUpTo, timeBoundaryEId, newContent));

    // Update passiv mods in ZF0
    updateNormService.updatePassiveModifications(
        new UpdatePassiveModificationsUseCase.Query(zf0Norm, amendingNorm, targetNormEli.get()));

    // Validate changes on ZF0
    final Mod selectedMod =
        amendingNorm.getMods().stream()
            .filter(m -> m.getEid().isPresent() && m.getEid().get().equals(eId))
            .findFirst()
            .orElseThrow(
                () ->
                    new ValidationException(
                        "Did not find a textual mod in the norm %s"
                            .formatted(amendingNorm.getEli())));
    singleModValidator.validate(zf0Norm, selectedMod);
  }

  @Override
  public Optional<UpdateModsUseCase.Result> updateMods(UpdateModsUseCase.Query query) {

    if (query.mods().isEmpty()) {
      return Optional.empty();
    }

    final Optional<Norm> amendingNormOptional =
        loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));
    if (amendingNormOptional.isEmpty()) {
      return Optional.empty();
    }
    final Norm amendingNorm = amendingNormOptional.get();

    final var targetNormEli =
        amendingNorm
            .getNodeByEId(query.mods().stream().findAny().orElseThrow().eId())
            .map(Mod::new)
            .flatMap(mod -> mod.getTargetRefHref().or(mod::getTargetRrefFrom))
            .flatMap(Href::getEli);
    if (targetNormEli.isEmpty()) {
      return Optional.empty();
    }

    if (!query.mods().stream()
        .allMatch(
            modData -> {
              final var modNode = amendingNorm.getNodeByEId(modData.eId()).map(Mod::new);
              final var eli =
                  modNode
                      .flatMap(mod -> mod.getTargetRefHref().or(mod::getTargetRrefFrom))
                      .flatMap(Href::getEli);
              return eli.equals(targetNormEli);
            })) {
      throw new IllegalArgumentException("Not all mods have the same target norm");
    }

    final Norm targetNorm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(targetNormEli.get()))
            .orElseThrow(() -> new NormNotFoundException(targetNormEli.get()));
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

    return Optional.of(
        new UpdateModsUseCase.Result(
            XmlMapper.toString(amendingNorm.getDocument()),
            XmlMapper.toString(zf0Norm.getDocument())));
  }

  @Override
  public Optional<UpdateModUseCase.Result> updateMod(UpdateModUseCase.Query query) {
    final Optional<Norm> amendingNormOptional =
        loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));
    if (amendingNormOptional.isEmpty()) {
      throw new NormNotFoundException(query.eli());
    }
    final Norm amendingNorm = amendingNormOptional.get();

    final var targetNormEli = new Href(query.destinationHref()).getEli();
    if (targetNormEli.isEmpty()) {
      return Optional.empty();
    }

    final Norm targetNorm =
        loadNormPort.loadNorm(new LoadNormPort.Command(targetNormEli.get())).orElseThrow();
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

    return Optional.of(
        new UpdateModUseCase.Result(
            XmlMapper.toString(amendingNorm.getDocument()),
            XmlMapper.toString(zf0Norm.getDocument())));
  }
}
