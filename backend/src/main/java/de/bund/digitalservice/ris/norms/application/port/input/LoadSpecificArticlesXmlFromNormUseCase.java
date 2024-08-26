package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;

/**
 * Interface representing the use case for loading a specific type of article in a {@link Norm}.
 * Implementations of this interface should provide functionality to load articles based on a given
 * query.
 */
public interface LoadSpecificArticlesXmlFromNormUseCase {

  /**
   * Retrieves articles of a specific type based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return An {@link List} containing the loaded Xml-{@link String}s if found, or empty if not
   *     found.
   */
  List<String> loadSpecificArticlesXmlFromNorm(Query query);

  /**
   * A record representing the query for loading a norm. The query includes the ELI (European
   * Legislation Identifier) to identify the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm in the query.
   * @param refersTo Specifies the type of article.
   */
  record Query(String eli, String refersTo) {}
}
