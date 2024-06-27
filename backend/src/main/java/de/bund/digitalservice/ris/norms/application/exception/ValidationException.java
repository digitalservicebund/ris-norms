package de.bund.digitalservice.ris.norms.application.exception;

/** This exception indicates that there was a validation error. */
public class ValidationException extends RuntimeException {

  public ValidationException(String message) {
    super(message);
  }
}
