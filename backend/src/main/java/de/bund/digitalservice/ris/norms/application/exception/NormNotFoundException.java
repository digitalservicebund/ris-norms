package de.bund.digitalservice.ris.norms.application.exception;

/** Indicates that the requested norm does not exist. */
public class NormNotFoundException extends RuntimeException {

  public NormNotFoundException(final String eli) {
    super("Norm with eli %s does not exist".formatted(eli));
  }
}
