package de.bund.digitalservice.ris.norms.domain.exceptions;

/** This exception indicates that a proposed change to a norm is not allowed */
public class InvalidNormUpdateException extends Exception {
  public InvalidNormUpdateException(String message) {
    super(message);
  }
}
