package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;

/** The given norm is not an act */
public class NormNotAnActException extends RuntimeException implements NormsAppException {
  public NormNotAnActException() {
    super("The norm is not an akn:act.");
  }
}
