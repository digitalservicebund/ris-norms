package de.bund.digitalservice.ris.norms.utils.exceptions;

/** Indicates that the requested norm does not exist. */
public class NormNotFoundException extends Exception {
  public NormNotFoundException(String message) {
    super(message);
  }
}
