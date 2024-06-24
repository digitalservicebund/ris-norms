package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.common.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlProcessor;
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
        LoadNormByGuidUseCase,
        LoadNormXmlUseCase,
        LoadNextVersionOfNormUseCase,
        UpdateNormXmlUseCase,
        LoadSpecificArticleXmlFromNormUseCase,
        UpdateModUseCase {
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
  public Norm loadNorm(final LoadNormUseCase.Query query) throws NormNotFoundException {
    return loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));
  }

  @Override
  public Norm loadNormByGuid(final LoadNormByGuidUseCase.Query query) throws NormNotFoundException {
    return loadNormByGuidPort.loadNormByGuid(new LoadNormByGuidPort.Command(query.guid()));
  }

  @Override
  public String loadNormXml(final LoadNormXmlUseCase.Query query) throws NormNotFoundException {
    return XmlProcessor.toString(
        loadNormPort.loadNorm(new LoadNormPort.Command(query.eli())).getDocument());
  }

  @Override
  public Norm loadNextVersionOfNorm(LoadNextVersionOfNormUseCase.Query query)
      throws NormNotFoundException {
    final var norm = loadNorm(new LoadNormUseCase.Query(query.eli()));
    return loadNormByGuid(
        new LoadNormByGuidUseCase.Query(
            norm.getMeta().getFRBRExpression().getFRBRaliasNextVersionId()));
  }

  @Override
  public String updateNormXml(UpdateNormXmlUseCase.Query query)
      throws InvalidUpdateException, NormNotFoundException {
    var existingNorm = loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));

    var updatedNorm = Norm.builder().document(XmlProcessor.toDocument(query.xml())).build();

    if (!existingNorm.getEli().equals(updatedNorm.getEli())) {
      throw new UpdateNormXmlUseCase.InvalidUpdateException("Changing the ELI is not supported.");
    }

    if (!existingNorm.getGuid().equals(updatedNorm.getGuid())) {
      throw new UpdateNormXmlUseCase.InvalidUpdateException("Changing the GUID is not supported.");
    }

    return XmlProcessor.toString(
        updateNormPort.updateNorm(new UpdateNormPort.Command(updatedNorm)).getDocument());
  }

  @Override
  public List<String> loadSpecificArticles(LoadSpecificArticleXmlFromNormUseCase.Query query)
      throws NormNotFoundException {
    final List<Article> articles =
        loadNormPort.loadNorm(new LoadNormPort.Command(query.eli())).getArticles();
    if (query.refersTo() == null) {
      return articles.stream().map(a -> XmlProcessor.toString(a.getNode())).toList();
    } else {
      return articles.stream()
          .filter(a -> Objects.equals(a.getRefersTo().orElse(""), query.refersTo()))
          .map(a -> XmlProcessor.toString(a.getNode()))
          .toList();
    }
  }

  @Override
  public Optional<UpdateModUseCase.Result> updateMod(UpdateModUseCase.Query query)
      throws NormNotFoundException {

    final Norm amendingNorm = loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));
    final Optional<String> targetNormEliOptional = new Href(query.destinationHref()).getEli();
    if (targetNormEliOptional.isEmpty()) {
      return Optional.empty();
    }
    final Norm targetNorm =
        loadNormPort.loadNorm(new LoadNormPort.Command(targetNormEliOptional.get()));
    final Norm zf0Norm = loadZf0Service.loadZf0(new LoadZf0UseCase.Query(amendingNorm, targetNorm));

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
            XmlProcessor.toString(amendingNorm.getDocument()),
            XmlProcessor.toString(zf0Norm.getDocument())));
  }
}
