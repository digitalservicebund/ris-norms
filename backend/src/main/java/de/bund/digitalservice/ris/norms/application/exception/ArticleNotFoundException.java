package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
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
}
