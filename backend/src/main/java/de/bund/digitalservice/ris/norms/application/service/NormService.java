package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        LoadNormByGuidUseCase,
        LoadNormXmlUseCase,
        LoadNextVersionOfNormUseCase,
        UpdateNormXmlUseCase,
        LoadSpecificArticleXmlFromNormUseCase,
        UpdateModsUseCase {
  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final UpdateNormPort updateNormPort;
  private final SingleModValidator singleModValidator;
  private final UpdateNormService updateNormService;
  private final LoadZf0Service loadZf0Service;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;

  public NormService(
      LoadNormPort loadNormPort,
      LoadNormByGuidPort loadNormByGuidPort,
      UpdateNormPort updateNormPort,
      SingleModValidator singleModValidator,
      UpdateNormService updateNormService,
      LoadZf0Service loadZf0Service,
      UpdateOrSaveNormPort updateOrSaveNormPort) {
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
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
  public Optional<Norm> loadNormByGuid(final LoadNormByGuidUseCase.Query query) {
    return loadNormByGuidPort.loadNormByGuid(new LoadNormByGuidPort.Command(query.guid()));
  }

  @Override
  public Optional<String> loadNormXml(final LoadNormXmlUseCase.Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(Norm::getDocument)
        .map(XmlMapper::toString);
  }

  @Override
  public Optional<Norm> loadNextVersionOfNorm(LoadNextVersionOfNormUseCase.Query query) {
    return loadNorm(new LoadNormUseCase.Query(query.eli()))
        .map(n -> n.getMeta().getFRBRExpression().getFRBRaliasNextVersionId())
        .flatMap(
            nextVersionGuid -> loadNormByGuid(new LoadNormByGuidUseCase.Query(nextVersionGuid)));
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
  public List<String> loadSpecificArticles(LoadSpecificArticleXmlFromNormUseCase.Query query) {
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

  @Override
  public Optional<UpdateModsUseCase.Result> updateMods(UpdateModsUseCase.Query query) {

    final Optional<Norm> amendingNormOptional =
        loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));

    if (amendingNormOptional.isEmpty()) {
      return Optional.empty();
    }
    final Norm amendingNorm = amendingNormOptional.get();

    // Map between a target norm eli, and it's zf0 norm. Used to ensure that all changes to a zf0
    // modified by multiple mods are applied to the same norm.
    final Map<String, Norm> zf0Norms = new HashMap<>();

    for (Map.Entry<String, UpdateModsUseCase.NewModData> entry : query.mods().entrySet()) {
      final String eId = entry.getKey();
      final UpdateModsUseCase.NewModData newModData = entry.getValue();

      final var targetNormEli = new Href(newModData.destinationHref()).getEli();
      if (targetNormEli.isEmpty()) {
        return Optional.empty();
      }

      final var zf0Norm =
          zf0Norms.computeIfAbsent(
              targetNormEli.get(),
              eli -> {
                final Norm targetNorm =
                    loadNormPort.loadNorm(new LoadNormPort.Command(eli)).orElseThrow();
                return loadZf0Service.loadZf0(new LoadZf0UseCase.Query(amendingNorm, targetNorm));
              });

      // Update active mods (meta and body) in amending law
      updateNormService.updateActiveModifications(
          new UpdateActiveModificationsUseCase.Query(
              amendingNorm,
              eId,
              newModData.destinationHref(),
              newModData.timeBoundaryEid(),
              newModData.newText()));

      // Update passiv mods in ZF0
      updateNormService.updatePassiveModifications(
          new UpdatePassiveModificationsUseCase.Query(zf0Norm, amendingNorm, targetNormEli.get()));

      // Validate changes on ZF0
      final Mod selectedMod =
          amendingNorm.getMods().stream()
              .filter(m -> m.getEid().isPresent() && m.getEid().get().equals(eId))
              .findFirst()
              .orElseThrow();
      singleModValidator.validate(zf0Norm, selectedMod);
    }

    // Don't save changes when dryRun (when preview is being generated but changes not saved)
    if (!query.dryRun()) {
      updateNormPort.updateNorm(new UpdateNormPort.Command(amendingNorm));
      zf0Norms.values().stream()
          .map(UpdateOrSaveNormPort.Command::new)
          .forEach(updateOrSaveNormPort::updateOrSave);
    }

    return Optional.of(
        new UpdateModsUseCase.Result(
            XmlMapper.toString(amendingNorm.getDocument()),
            zf0Norms.values().stream().map(Norm::getDocument).map(XmlMapper::toString).toList()));
  }
}
