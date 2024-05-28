package de.bund.digitalservice.ris.norms.application.port.input;

import java.util.Optional;

/** Use case for loading a norm's articles */
public interface LoadArticleHtmlUseCase {
  Optional<String> loadArticleHtml(Query query);

  // TODO Hannes: tests missing

  /**
   * Query for loading a norm's article via the use case
   *
   * @param eli ELI of the norm to load the article from
   * @param eid EID of the article to load from the norm
   * @param atIsoDate Apply the norm's passive mods up to this date before loading the article
   */
  // TODO Hannes: remove the optional parameter. Use the multiple constructors pattern.
  record Query(String eli, String eid, Optional<String> atIsoDate) {}
}
