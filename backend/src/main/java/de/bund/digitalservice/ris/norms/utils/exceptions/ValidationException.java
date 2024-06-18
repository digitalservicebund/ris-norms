package de.bund.digitalservice.ris.norms.utils.exceptions;

import lombok.Getter;

/** This exception indicates that there was a validation error. */
@Getter
public class ValidationException extends RuntimeException {

  public ValidationException(String message) {
    super(message);
  }
}
