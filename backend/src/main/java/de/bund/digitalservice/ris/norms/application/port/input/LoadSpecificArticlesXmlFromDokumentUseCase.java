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
  record Options(DokumentExpressionEli eli, List<String> refersTo) {}

  /** Indicates that the Dokument was found but does not include articles of the specified types. */
  @Getter
  class NoArticlesOfTypesFoundException extends RuntimeException implements NormsAppException {

    private final String eli;
    private final List<String> articleTypes;

    public NoArticlesOfTypesFoundException(final String eli, final List<String> articleTypes) {
      super(
        "Dokument with eli %s does not contain articles of any of the types %s".formatted(
            eli,
            String.join(", ", articleTypes)
          )
      );
      this.eli = eli;
      this.articleTypes = articleTypes;
    }

    @Override
    public URI getType() {
      return URI.create("/errors/no-articles-of-types-found");
    }

    @Override
    public String getTitle() {
      return "No articles of specific types found";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of("eli", getEli(), "articleTypes", getArticleTypes());
    }
  }
}
