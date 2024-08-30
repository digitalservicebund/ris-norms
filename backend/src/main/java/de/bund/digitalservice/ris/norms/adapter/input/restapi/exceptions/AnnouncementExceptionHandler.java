package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import de.bund.digitalservice.ris.norms.application.exception.AnnouncementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception handler for handling exceptions that are related to an Announcement. This class
 * is annotated with {@link ControllerAdvice} to allow centralized exception handling for all
 * controllers in the application.
 */
@Order(1)
@ControllerAdvice
@Slf4j
public class AnnouncementExceptionHandler {

  private static final String CONTENT_FORMAT_TEMPLATE = "{\"message\": \"%s\"}";

  /**
   * Exception handler method for handling {@link AnnouncementNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(AnnouncementNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handleException(final AnnouncementNotFoundException e) {
    log.error("AnnouncementNotFoundException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }
}
