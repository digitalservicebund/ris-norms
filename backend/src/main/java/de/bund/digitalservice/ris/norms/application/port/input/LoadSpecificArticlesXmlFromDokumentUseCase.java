package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.util.List;
import lombok.Getter;

/**
 * Interface representing the use case for loading a specific type of article in a Dokument.
 */
public interface LoadSpecificArticlesXmlFromDokumentUseCase {
  /**
   * Retrieves articles of a specific type based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the dokument.
   * @return An {@link List} containing the loaded Xml-{@link String}s if found, or empty if not
   *     found.
   */
  List<String> loadSpecificArticlesXmlFromDokument(Query query);

  /**
   * A record representing the query for loading a dokument. The query includes the ELI (European
   * Legislation Identifier) to identify the dokument.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the dokument in the query.
   * @param refersTo Specifies the type of article.
   */
  record Query(DokumentExpressionEli eli, String refersTo) {}

  /** Indicates that the Dokument was found but does not include articles of that type. */
  @Getter
  class ArticleOfTypeNotFoundException extends RuntimeException implements NormsAppException {

    private final String eli;
    private final String type;

    public ArticleOfTypeNotFoundException(final String eli, final String type) {
      super("Dokument with eli %s does not contain articles of type %s".formatted(eli, type));
      this.eli = eli;
      this.type = type;
    }
  }
}
