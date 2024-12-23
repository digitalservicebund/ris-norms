package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.util.List;
import lombok.Getter;

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
  record Query(ExpressionEli eli, String refersTo) {}

  /** Indicates that the Norm was found but does not include articles of that type. */
  @Getter
  class ArticleOfTypeNotFoundException extends RuntimeException implements NormsAppException {

    private final String eli;
    private final String type;

    public ArticleOfTypeNotFoundException(final String eli, final String type) {
      super("Norm with eli %s does not contain articles of type %s".formatted(eli, type));
      this.eli = eli;
      this.type = type;
    }
  }
}
