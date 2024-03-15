package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for handling internal server errors. This class is annotated with {@link
 * ControllerAdvice} to allow centralized exception handling for all controllers in the application.
 */
@ControllerAdvice
@Slf4j
public class InternalErrorExceptionHandler {

  /**
   * Exception handler method for letting Spring handle this exception.
   *
   * @param e The exception that occurred.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public void handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {

    log.error("Internal server error", e);
    throw e;
  }

  /**
   * Exception handler method for handling general exceptions.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 500 status and a custom error message.
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> handleException(final Exception e) {

    log.error("Internal server error", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
  }
}
