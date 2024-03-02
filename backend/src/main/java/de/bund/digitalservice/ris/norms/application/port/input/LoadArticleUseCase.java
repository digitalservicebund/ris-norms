package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.Optional;

/**
 * Interface representing the use case for loading an {@link Article}. Implementations of this
 * interface should provide functionality to load an article of an amending law based on a given
 * query.
 */
public interface LoadArticleUseCase {

  /**
   * Retrieves an article related to the specified amending law based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the amending law
   *     and the eId of the article.
   * @return An {@link Optional} containing the loaded {@link Article} if found, or empty if not
   *     found.
   */
  Optional<Article> loadArticle(Query query);

  /**
   * A record representing the parameters needed to query one specific article of an amending law
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     query.
   * @param eId The expression level identifier of the article within the XML.
   */
  record Query(String eli, String eId) {}
}
