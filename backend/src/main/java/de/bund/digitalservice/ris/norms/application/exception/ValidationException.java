package de.bund.digitalservice.ris.norms.application.exception;

import lombok.Getter;

/** This exception indicates that there was a validation error. */
@Getter
public class ValidationException extends RuntimeException {

  public ValidationException(final String message) {
    super(message);
  }
}
