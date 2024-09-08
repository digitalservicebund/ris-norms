package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;

/** This exception indicates that a proposed change to a norm is not allowed */
public class InvalidUpdateException extends RuntimeException implements NormsAppException {

  public InvalidUpdateException(String message) {
    super(message);
  }
}
