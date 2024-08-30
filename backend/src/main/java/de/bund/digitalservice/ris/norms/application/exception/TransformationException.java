package de.bund.digitalservice.ris.norms.application.exception;

/** This exception indicates that something went wrong while transforming the xml into html */
public class TransformationException extends RuntimeException {

  public TransformationException(final String message, final Exception e) {
    super(message, e);
  }
}
