package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;

/** Use case for loading a norm's article */
public interface LoadArticleHtmlUseCase {
  /**
   * Loads the HTML of an article from a norm.
   *
   * @param options The article to load
   * @return HTML string of the article
   */
  String loadArticleHtml(Options options);

  /**
   * Options for loading a norm's article via the use case
   *
   * @param eli ELI of the norm to load the article from
   * @param eid EID of the article to load from the norm
   */
  record Options(DokumentExpressionEli eli, EId eid) {}
}
