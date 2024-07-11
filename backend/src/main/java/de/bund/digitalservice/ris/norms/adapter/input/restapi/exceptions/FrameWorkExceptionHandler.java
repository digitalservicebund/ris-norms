package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * Global exception handler for handling spring exceptions related to the input request schema and
 * also general {@link Exception}. This class is annotated with {@link ControllerAdvice} to allow
 * centralized exception handling for all controllers in the application.
 */
@Order(2)
@ControllerAdvice
@Slf4j
public class FrameWorkExceptionHandler {

  private static final String CONTENT_FORMAT_TEMPLATE = "{\"message\": \"%s\"}";

  /**
   * Exception handler method for letting Spring handle {@link HttpMessageNotReadableException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 400 status and the exception message.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleHttpMessageNotReadableException(
      final HttpMessageNotReadableException e) {

    log.error("HttpMessageNotReadableException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }

  /**
   * Exception handler method for {@link HandlerMethodValidationException} produced by the jakarta
   * validation on the request schemas of controllers.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 400 status and the exception message.
   */
  @ExceptionHandler(HandlerMethodValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleHandlerMethodValidationException(
      final HandlerMethodValidationException e) {

    e.getAllValidationResults()
        .forEach(
            validationResults ->
                validationResults
                    .getResolvableErrors()
                    .forEach(
                        resolvableErrors ->
                            log.error(
                                "Validation Error: {}", resolvableErrors.getDefaultMessage())));

    log.error("HandlerMethodValidationException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }

  /**
   * Exception handler method for handling general exceptions.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 500 status and the exception message.
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> handleException(final Exception e) {

    log.error("Internal server error: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }
}
