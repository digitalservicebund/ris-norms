package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import de.bund.digitalservice.ris.norms.application.exception.AnnouncementNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ElementNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.TransformationException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadElementsByTypeFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticlesXmlFromNormUseCase;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
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

  /**
   * Exception handler method for handling {@link ElementNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(ElementNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handleException(final ElementNotFoundException e) {
    log.error("ElementNotFoundException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }

  /**
   * Exception handler method for handling {@link
   * LoadSpecificArticlesXmlFromNormUseCase.ArticleOfTypeNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(LoadSpecificArticlesXmlFromNormUseCase.ArticleOfTypeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handleException(
      final LoadSpecificArticlesXmlFromNormUseCase.ArticleOfTypeNotFoundException e) {
    log.error("ArticleOfTypeNotFoundException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }

  /**
   * Exception handler method for handling {@link
   * LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 400 status code and the exception message.
   */
  @ExceptionHandler(LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleException(
      final LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException e) {
    log.error("UnsupportedElementTypeException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(CONTENT_FORMAT_TEMPLATE.formatted(e.getMessage()));
  }

  /**
   * Exception handler method for handling {@link MandatoryNodeNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(MandatoryNodeNotFoundException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<String> handleException(final MandatoryNodeNotFoundException e) {
    log.error("MandatoryNodeNotFoundException: {}", e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(CONTENT_FORMAT_TEMPLATE.formatted((e.getMessage())));
  }
}
