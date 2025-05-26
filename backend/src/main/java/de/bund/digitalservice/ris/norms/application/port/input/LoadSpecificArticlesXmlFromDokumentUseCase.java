package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * Interface representing the use case for loading a specific type of article in a Dokument.
 */
public interface LoadSpecificArticlesXmlFromDokumentUseCase {
  /**
   * Retrieves articles of a specific type based on the provided options.
   *
   * @param options The options containing the ELI (European Legislation Identifier) of the dokument.
   * @return An {@link List} containing the loaded Xml-{@link String}s if found, or empty if not
   *     found.
   */
  List<String> loadSpecificArticlesXmlFromDokument(Options options);

  /**
   * A record representing the options for loading a dokument.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the dokument.
   * @param refersTo Specifies the type of article.
   */
  record Options(DokumentExpressionEli eli, String refersTo) {}

  /** Indicates that the Dokument was found but does not include articles of that type. */
  @Getter
  class ArticleOfTypeNotFoundException extends RuntimeException implements NormsAppException {

    private final String eli;
    private final String articleType;

    public ArticleOfTypeNotFoundException(final String eli, final String articleType) {
      super(
        "Dokument with eli %s does not contain articles of type %s".formatted(eli, articleType)
      );
      this.eli = eli;
      this.articleType = articleType;
    }

    @Override
    public URI getType() {
      return URI.create("/errors/article-of-type-not-found");
    }

    @Override
    public String getTitle() {
      return "Article of specific type not found";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of("eli", getEli(), "articleType", getArticleType());
    }
  }
}
