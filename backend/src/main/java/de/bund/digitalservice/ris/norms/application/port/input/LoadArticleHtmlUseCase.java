package de.bund.digitalservice.ris.norms.application.port.input;

import java.util.Optional;

public interface LoadArticleHtmlUseCase {
  Optional<String> loadArticleHtml(Query query);

  // TODO Hannes: Implementation missing, tests missing
  record Query(String normEli, String articleEid, Optional<String> atIsoDate) {}
}
