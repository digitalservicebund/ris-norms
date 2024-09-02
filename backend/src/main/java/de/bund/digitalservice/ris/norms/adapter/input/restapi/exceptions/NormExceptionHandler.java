package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticlesXmlFromNormUseCase;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
   * Exception handler method for handling {@link MissingAttributeValidationException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ProblemDetail} with an HTTP 422 status and the exception message.
   */
  @ExceptionHandler(MissingAttributeValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final MissingAttributeValidationException e) {

    log.error("MissingAttributeValidationException: {}", e.getMessage(), e);

    ProblemDetail problemDetail =
        ProblemDetailFactory.createProblemDetail(e, HttpStatus.UNPROCESSABLE_ENTITY);
    problemDetail.setProperty("eli", e.getEli());
    problemDetail.setProperty("eId", e.getEId());
    problemDetail.setProperty("attributeName", e.getAttributeName());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link MalformedAttributeValidationException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ProblemDetail} with an HTTP 422 status and the exception message.
   */
  @ExceptionHandler(MalformedAttributeValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final MalformedAttributeValidationException e) {

    log.error("MalformedAttributeValidationException: {}", e.getMessage(), e);

    ProblemDetail problemDetail =
        ProblemDetailFactory.createProblemDetail(e, HttpStatus.UNPROCESSABLE_ENTITY);
    problemDetail.setProperty("eli", e.getEli());
    problemDetail.setProperty("eId", e.getEId());
    problemDetail.setProperty("attributeName", e.getAttributeName());
    problemDetail.setProperty("attributeValue", e.getAttributeValue());
    return problemDetail;
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
  public ProblemDetail handleException(final NormNotFoundException e) {

    log.error("NormNotFoundException: {}", e.getMessage(), e);

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    problemDetail.setTitle("Norm not found");
    problemDetail.setType(URI.create("/errors/norm-not-found"));
    problemDetail.setProperty("eli", e.getEli());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link ArticleNotFoundException}.
   *
   * @param e The exception that occured.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(ArticleNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleException(final ArticleNotFoundException e) {
    log.error("ArticleNotFoundException: {}", e.getMessage(), e);

    final ProblemDetail problemDetail =
        ProblemDetailFactory.createProblemDetail(e, HttpStatus.NOT_FOUND);
    problemDetail.setInstance(URI.create(e.getEli()));
    problemDetail.setProperty("eid", e.getEid());

    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link
   * LoadSpecificArticlesXmlFromNormUseCase.ArticleOfTypeNotFoundException}.
   *
   * @param e The exception that occured.
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
}
