package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAmendingLawsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAmendingLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticleUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticlesUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAmendingLawsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadArticlePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadArticlesPort;
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
        LoadAllAmendingLawsUseCase,
        LoadArticleUseCase,
        LoadArticlesUseCase {

  private final LoadAmendingLawPort loadAmendingLawPort;
  private final LoadAllAmendingLawsPort loadAllAmendingLawsPort;
  private final LoadArticlesPort loadArticlesPort;
  private final LoadArticlePort loadArticlePort;

  /**
   * Constructs a new {@link AmendingLawService} instance.
   *
   * @param loadAmendingLawPort The port for loading individual amending laws.
   * @param loadAllAmendingLawsPort The port for loading all amending laws.
   */
  public AmendingLawService(
      LoadAmendingLawPort loadAmendingLawPort,
      LoadAllAmendingLawsPort loadAllAmendingLawsPort,
      LoadArticlesPort loadArticlesPort,
      LoadArticlePort loadArticlePort) {
    this.loadAmendingLawPort = loadAmendingLawPort;
    this.loadAllAmendingLawsPort = loadAllAmendingLawsPort;
    this.loadArticlesPort = loadArticlesPort;
    this.loadArticlePort = loadArticlePort;
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
}
