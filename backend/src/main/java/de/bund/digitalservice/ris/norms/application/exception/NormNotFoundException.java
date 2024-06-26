package de.bund.digitalservice.ris.norms.application.exception;

/** Indicates that the requested norm does not exist. */
public class NormNotFoundException extends Exception {
  public NormNotFoundException(String message) {
    super(message);
  }
}
