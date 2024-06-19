package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import de.bund.digitalservice.ris.norms.utils.exceptions.ValidationException;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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

    log.error("Bad request: {}", e.getMessage(), e);
    throw e;
  }

  /**
   * Exception handler method for letting Spring handle this exception.
   *
   * @param e The exception that occurred.
   */
  @ExceptionHandler(HandlerMethodValidationException.class)
  public void handleHandlerMethodValidationException(final HandlerMethodValidationException e) {
    log.error("Bad request: {}", e.getMessage());

    e.getAllValidationResults()
        .forEach(
            validationResults ->
                validationResults
                    .getResolvableErrors()
                    .forEach(
                        resolvableErrors ->
                            log.error(
                                "Validation Error: {}", resolvableErrors.getDefaultMessage())));

    log.error("Stacktrace: ", e);

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

    log.error("Internal server error with message: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
  }

  /**
   * Exception handler method for handling XmlContentExceptions.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status and a custom error message.
   */
  @ExceptionHandler(XmlContentException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<String> handleException(final XmlContentException e) {

    log.error("Unable to process contained instructions: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body("{\"message\": \"%s\"}".formatted(e.getMessage()));
  }

  /**
   * Exception handler method for handling ValidationException.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status and a custom error message.
   */
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<String> handleException(final ValidationException e) {

    log.error("Validation exception: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body("{\"message\": \"%s\"}".formatted(e.getMessage()));
  }
}
