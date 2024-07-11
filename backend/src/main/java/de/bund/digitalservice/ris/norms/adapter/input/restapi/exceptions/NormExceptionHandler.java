package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.TransformationException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception handler for handling exceptions that are related to a norm. This class is
 * annotated with {@link ControllerAdvice} to allow centralized exception handling for all
 * controllers in the application.
 */
@Order(1)
@ControllerAdvice
@Slf4j
public class NormExceptionHandler {

  private static final String CONTENT_FORMAT_TEMPLATE = "{\"message\": \"%s\"}";

  /**
   * Exception handler method for handling {@link ValidationException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status and the exception message.
   */
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<String> handleException(final ValidationException e) {

    log.error("ValidationException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }

  /**
   * Exception handler method for handling {@link TransformationException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 500 status and the exception message.
   */
  @ExceptionHandler(TransformationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> handleException(final TransformationException e) {

    log.error("TransformationException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }

  /**
   * Exception handler method for handling {@link NormNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(NormNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handleException(final NormNotFoundException e) {

    log.error("NormNotFoundException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }
}
