package de.bund.digitalservice.ris.norms.application.exception;

/** Indicates that an article was not found based on the provided parameters. */
public class ArticleNotFoundException extends RuntimeException {
  public ArticleNotFoundException(final String eli, final String eid) {
    super("Article with eid %s does not exist in norm with eli %s".formatted(eid, eli));
  }
}
