package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
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
        LoadSpecificArticleXmlFromNormUseCase,
        UpdateModUseCase {
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
  public Optional<UpdateModUseCase.Result> updateMod(UpdateModUseCase.Query query) {

    final Optional<Norm> amendingNormOptional =
        loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));
    final Optional<String> targetNormEliOptional = new Href(query.destinationHref()).getEli();
    if (amendingNormOptional.isEmpty() || targetNormEliOptional.isEmpty()) {
      return Optional.empty();
    }
    final Norm amendingNorm = amendingNormOptional.get();
    final Norm targetNorm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(targetNormEliOptional.get()))
            .orElseThrow(() -> new NormNotFoundException(targetNormEliOptional.get()));
    final Norm zf0Norm =
        loadZf0Service.loadOrCreateZf0(new LoadZf0UseCase.Query(amendingNorm, targetNorm));

    // Update active mods (meta and body) in amending law
    updateNormService.updateActiveModifications(
        new UpdateActiveModificationsUseCase.Query(
            amendingNorm,
            query.eid(),
            query.destinationHref(),
            query.timeBoundaryEid(),
            query.newText()));

    // Update passiv mods in ZF0
    updateNormService.updatePassiveModifications(
        new UpdatePassiveModificationsUseCase.Query(
            zf0Norm, amendingNorm, targetNormEliOptional.get()));

    // Validate changes on ZF0
    final Mod selectedMod =
        amendingNorm.getMods().stream()
            .filter(m -> m.getEid().isPresent() && m.getEid().get().equals(query.eid()))
            .findFirst()
            .orElseThrow();
    singleModValidator.validate(zf0Norm, selectedMod);

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
