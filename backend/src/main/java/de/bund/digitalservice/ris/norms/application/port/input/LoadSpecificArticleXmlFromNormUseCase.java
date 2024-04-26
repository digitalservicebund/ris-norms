package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import java.util.Optional;

/**
 * Interface representing the use case for loading a specific type of Article in a {@link Norm}.
 * Implementations of this interface should provide functionality to load articles based on a given
 * query.
 */
public interface LoadSpecificArticleXmlFromNormUseCase {

  /**
   * Retrieves a norm based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return An {@link Optional} containing the loaded Xml-{@link String} if found, or empty if not
   *     found.
   */
  List<String> loadSpecificArticles(Query query);

  /**
   * A record representing the query for loading a norm. The query includes the ELI (European
   * Legislation Identifier) to identify the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm in the query.
   * @param refersTo Specifies the type of article.
   */
  record Query(String eli, String refersTo) {}
}
