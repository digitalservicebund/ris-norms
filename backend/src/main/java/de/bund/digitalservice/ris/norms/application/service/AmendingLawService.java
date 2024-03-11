package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class AmendingLawService
    implements LoadAmendingLawUseCase,
        LoadAmendingLawXmlUseCase,
        LoadAllAmendingLawsUseCase,
        LoadArticleUseCase,
        LoadArticlesUseCase,
        UpdateAmendingLawXmlUseCase {

  private final LoadAmendingLawPort loadAmendingLawPort;
  private final LoadAmendingLawXmlPort loadAmendingLawXmlPort;
  private final LoadAllAmendingLawsPort loadAllAmendingLawsPort;
  private final LoadArticlesPort loadArticlesPort;
  private final LoadArticlePort loadArticlePort;
  private final UpdateAmendingLawXmlPort updateAmendingLawXmlPort;

  public AmendingLawService(
      LoadAmendingLawPort loadAmendingLawPort,
      LoadAmendingLawXmlPort loadAmendingLawXmlPort,
      LoadAllAmendingLawsPort loadAllAmendingLawsPort,
      LoadArticlesPort loadArticlesPort,
      LoadArticlePort loadArticlePort,
      UpdateAmendingLawXmlPort updateAmendingLawXmlPort) {
    this.loadAmendingLawPort = loadAmendingLawPort;
    this.loadAmendingLawXmlPort = loadAmendingLawXmlPort;
    this.loadAllAmendingLawsPort = loadAllAmendingLawsPort;
    this.loadArticlesPort = loadArticlesPort;
    this.loadArticlePort = loadArticlePort;
    this.updateAmendingLawXmlPort = updateAmendingLawXmlPort;
  }

  @Override
  public Optional<AmendingLaw> loadAmendingLaw(final LoadAmendingLawUseCase.Query query) {
    return loadAmendingLawPort.loadAmendingLawByEli(new LoadAmendingLawPort.Command(query.eli()));
  }

  @Override
  public List<AmendingLaw> loadAllAmendingLaws() {
    return loadAllAmendingLawsPort.loadAllAmendingLaws();
  }

  @Override
  public List<Article> loadArticlesOfAmendingLaw(final LoadArticlesUseCase.Query query) {
    return loadArticlesPort.loadArticlesByAmendingLaw(new LoadArticlesPort.Command(query.eli()));
  }

  @Override
  public Optional<Article> loadArticle(final LoadArticleUseCase.Query query) {
    return loadArticlePort.loadArticleByEliAndEid(
        new LoadArticlePort.Command(query.eli(), query.eId()));
  }

  @Override
  public Optional<String> loadAmendingLawXml(LoadAmendingLawXmlUseCase.Query query) {
    return loadAmendingLawXmlPort.loadAmendingLawXmlByEli(
        new LoadAmendingLawXmlPort.Command(query.eli()));
  }

  @Override
  public Optional<String> updateAmendingLawXml(UpdateAmendingLawXmlUseCase.Query query) {
    return updateAmendingLawXmlPort.updateAmendingLawXmlByEli(
        new UpdateAmendingLawXmlPort.Command(query.eli(), query.xml()));
  }
}
