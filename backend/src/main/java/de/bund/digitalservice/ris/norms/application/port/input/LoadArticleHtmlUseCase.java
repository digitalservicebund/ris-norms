package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import java.time.Instant;
import org.springframework.lang.Nullable;

/** Use case for loading a norm's article */
public interface LoadArticleHtmlUseCase {
  /**
   * Loads the HTML of an article from a norm.
   *
   * @param query The article to load
   * @return HTML string of the article
   * @throws NormNotFoundException If the norm is not found
   * @throws ArticleNotFoundException If the norm exists but does not contain the article
   */
  String loadArticleHtml(Query query) throws ArticleNotFoundException, NormNotFoundException;

  /**
   * Query for loading a norm's article via the use case
   *
   * @param eli ELI of the norm to load the article from
   * @param eid EID of the article to load from the norm
   * @param atIsoDate (Optional) Apply the norm's passive mods up to this date before loading the
   *     article
   */
  record Query(String eli, String eid, @Nullable Instant atIsoDate) {
    public Query(String eli, String eid) {
      this(eli, eid, null);
    }
  }
}
