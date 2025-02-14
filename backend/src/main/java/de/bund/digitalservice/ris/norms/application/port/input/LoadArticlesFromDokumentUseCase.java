package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;

/** Use case for getting a list of {@link Article}s from a {@link Dokument}. */
public interface LoadArticlesFromDokumentUseCase {
  /**
   * Load the list of articles from a dokument.
   *
   * @param query Query used for identifying the articles
   * @return List of articles (can be empty)
   */
  List<Article> loadArticlesFromDokument(Query query);

  /**
   * Contains the parameters needed for loading articles from a dokument.
   *
   * @param eli The ELI used to identify the dokument
   */
  record Query(DokumentExpressionEli eli) {}
}
