package de.bund.digitalservice.ris.norms.application.exception;

/** Marker interface for all custom exceptions thrown in the norms app */
public interface NormsAppException {

  /**
   * Should already be implemented by all {@link Throwable}
   *
   * @return message
   */
  String getMessage();
}
