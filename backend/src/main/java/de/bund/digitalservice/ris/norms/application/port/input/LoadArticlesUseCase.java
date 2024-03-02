package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.List;

/**
 * Interface representing the use case for loading a list of {@link Article}. Implementations of
 * this interface should provide functionality to load all articles of an amending law based on a
 * given query.
 */
public interface LoadArticlesUseCase {

  /**
   * Retrieves a list of articles related to the specified amending law based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the amending
   *     law.
   * @return A list of {@link Article} entities related to the specified amending law.
   */
  List<Article> loadArticlesOfAmendingLaw(Query query);

  /**
   * A record representing the parameters needed to query articles related to an amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     query.
   */
  record Query(String eli) {}
}
