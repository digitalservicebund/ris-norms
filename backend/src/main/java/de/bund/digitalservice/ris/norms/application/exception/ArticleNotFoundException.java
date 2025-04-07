package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import lombok.Getter;

/** Indicates that an article was not found based on the provided parameters. */
@Getter
public class ArticleNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;
  private final String eid;

  public ArticleNotFoundException(final String eli, final String eid) {
    super("Article with eid %s does not exist in norm with eli %s".formatted(eid, eli));
    this.eli = eli;
    this.eid = eid;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/article-not-found");
  }

  @Override
  public String getTitle() {
    return "Article not found";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("eli", getEli(), "eid", getEid());
  }
}
