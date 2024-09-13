package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler for handling spring exceptions related to the input request schema and
 * also general {@link Exception}. This class is annotated with {@link ControllerAdvice} to allow
 * centralized exception handling for all controllers in the application.
 */
@Order(2)
@ControllerAdvice
@Slf4j
public class FrameWorkExceptionHandler {

  /**
   * Exception handler method for letting Spring handle {@link HttpMessageNotReadableException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 400 status and the exception message.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleHttpMessageNotReadableException(
    final HttpMessageNotReadableException e
  ) {
    log.error("HttpMessageNotReadableException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.BAD_REQUEST,
      e.getMessage()
    );
    problemDetail.setType(URI.create("/errors/http-message-not-readable-exception"));
    return problemDetail;
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
  public ProblemDetail handleHandlerMethodValidationException(
    final HandlerMethodValidationException e
  ) {
    final List<String> validationErrors = e
      .getAllValidationResults()
      .stream()
      .flatMap(validationResults -> validationResults.getResolvableErrors().stream())
      .map(MessageSourceResolvable::getDefaultMessage)
      .toList();

    final String concatenatedValidationErrors = String.join(";", validationErrors);
    log.error("HandlerMethodValidationException: {}", concatenatedValidationErrors, e);

    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.BAD_REQUEST,
      concatenatedValidationErrors
    );
    problemDetail.setTitle("Input validation error");
    problemDetail.setType(URI.create("/errors/input-validation-error"));
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link NoSuchElementException} thrown by {@link
   * java.util.Optional}.orElseThrow.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status and the exception message.
   */
  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final NoSuchElementException e) {
    log.error("A value necessary for the operation was not found: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.UNPROCESSABLE_ENTITY,
      e.getMessage()
    );
    problemDetail.setType(URI.create("/errors/necessary-value-missing"));
    return problemDetail;
  }

  /**
   * Exception handler method for {@link MethodArgumentTypeMismatchException} produced by spring
   * when the passed parameters could not be transformed to the expected types.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 400 status and the exception message.
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleTypeMismatchException(final MethodArgumentTypeMismatchException e) {
    log.error("Type mismatch: {}", e.getMessage(), e);
    final String invalidValue = e.getValue() != null ? e.getValue().toString() : "Unknown";
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.BAD_REQUEST,
      "Invalid request parameter: %s".formatted(invalidValue)
    );
    problemDetail.setTitle("Parameter Binding Error");
    problemDetail.setType(URI.create("/errors/parameter-binding-error"));
    return problemDetail;
  }

  /**
   * Exception handler method for handling general exceptions.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 500 status and the exception message.
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ProblemDetail handleException(final Exception e) {
    log.error("Internal server error: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.INTERNAL_SERVER_ERROR,
      e.getMessage()
    );
    problemDetail.setType(URI.create("/errors/internal-server-error"));
    return problemDetail;
  }

  /**
   * Exception handler method for ignoring {@link AsyncRequestNotUsableException}. Wrapper exception
   * from Spring for {@link org.apache.catalina.connector.ClientAbortException} For more details on
   * why Spring introduced this new wrapper, see <a
   * href="https://github.com/spring-projects/spring-framework/issues/32340">Github Issue
   * #32340</a>. No further handling needed, it is an exception coming from the Servlet container
   * and indicates response that is no longer usable. It would normally be handled by {@link
   * org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver} but since we have
   * a handler for {@link Exception} (see above) the default handler has no chance. Therefore, we
   * need this one that is returning nothing. For more context check the thread in <a
   * href="https://github.com/spring-projects/spring-framework/issues/32509">here</a>
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with no return
   */
  @ExceptionHandler(AsyncRequestNotUsableException.class)
  public ResponseEntity<Object> handleAsyncRequestNotUsableException(
    AsyncRequestNotUsableException e
  ) {
    log.debug("Async request was not usable: ", e);
    return null;
  }
}
