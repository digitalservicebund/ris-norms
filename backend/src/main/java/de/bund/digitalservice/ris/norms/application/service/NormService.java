package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNextVersionOfNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormByGuidUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticleXmlFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateModUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
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
        LoadNormByGuidUseCase,
        LoadNormXmlUseCase,
        LoadNextVersionOfNormUseCase,
        UpdateNormXmlUseCase,
        LoadSpecificArticleXmlFromNormUseCase,
        UpdateModUseCase {
  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final UpdateNormPort updateNormPort;

  public NormService(
      LoadNormPort loadNormPort,
      LoadNormByGuidPort loadNormByGuidPort,
      UpdateNormPort updateNormPort) {
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.updateNormPort = updateNormPort;
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
        .flatMap(Norm::getNextVersionGuid)
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

    if (updatedNorm.getEli().isEmpty()
        || !existingNorm.get().getEli().orElseThrow().equals(updatedNorm.getEli().get())) {
      throw new UpdateNormXmlUseCase.InvalidUpdateException("Changing the ELI is not supported.");
    }

    if (updatedNorm.getGuid().isEmpty()
        || !existingNorm.get().getGuid().orElseThrow().equals(updatedNorm.getGuid().get())) {
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
  public Optional<String> updateMod(UpdateModUseCase.Query query) {

    final Optional<Norm> existingNorm =
        loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));

    if (existingNorm.isEmpty()) {
      return Optional.empty();
    } else {
      return existingNorm.flatMap(
          norm -> {

            // Edit mod in metadata
            norm.getActiveModifications().stream()
                .filter(
                    activeMod ->
                        activeMod.getSourceEid().isPresent()
                            && activeMod.getSourceEid().get().equals(query.eid()))
                .findFirst()
                .ifPresent(
                    activeMod -> {
                      activeMod.setDestinationHref(query.destinationHref());
                      activeMod.setForcePeriodEid(query.timeBoundaryEid());
                    });

            // Edit mod in body
            norm.getMods().stream()
                .filter(mod -> mod.getEid().isPresent() && mod.getEid().get().equals(query.eid()))
                .findFirst()
                .ifPresent(
                    mod -> {
                      mod.setTargetHref(query.destinationHref());
                      mod.setNewText(query.newText());
                    });

            // Return updated amending law document
            return Optional.of(XmlMapper.toString(norm.getDocument()));
          });
    }
  }
}
