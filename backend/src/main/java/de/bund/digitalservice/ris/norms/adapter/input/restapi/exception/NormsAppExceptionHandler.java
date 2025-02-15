package de.bund.digitalservice.ris.norms.adapter.input.restapi.exception;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticlesXmlFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class NormsAppExceptionHandler {

  /**
   * Exception handler method for handling {@link InvalidUpdateException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status and the exception message.
   */
  @ExceptionHandler(InvalidUpdateException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final InvalidUpdateException e) {
    log.error("InvalidUpdateException: {}", e.getMessage(), e);
    return ProblemDetailFactory.createProblemDetail(e, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  /**
   * Exception handler method for handling {@link AnnouncementNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(AnnouncementNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleException(final AnnouncementNotFoundException e) {
    log.error("AnnouncementNotFoundException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.NOT_FOUND
    );
    problemDetail.setProperty("eli", e.getEli());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link ValidationException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status and the exception message.
   */
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final ValidationException e) {
    log.error("ValidationException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    Arrays
      .stream(e.getFields())
      .forEach(field -> problemDetail.setProperty(field.getKey().getName(), field.getValue()));
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link XmlProcessingException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 500 status and the exception message.
   */
  @ExceptionHandler(XmlProcessingException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ProblemDetail handleException(final XmlProcessingException e) {
    log.error("XmlProcessingException: {}", e.getMessage(), e);
    return ProblemDetailFactory.createProblemDetail(e, HttpStatus.INTERNAL_SERVER_ERROR);
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
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.NOT_FOUND
    );
    problemDetail.setProperty("eli", e.getEli());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link DokumentNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(DokumentNotFoundException.class)
  public ProblemDetail handleException(final DokumentNotFoundException e) {
    log.error("DokumentNotFoundException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.NOT_FOUND
    );
    problemDetail.setProperty("eli", e.getEli());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link RegelungstextNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(RegelungstextNotFoundException.class)
  public ProblemDetail handleException(final RegelungstextNotFoundException e) {
    log.error("RegelungstextNotFoundException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.NOT_FOUND
    );
    problemDetail.setProperty("eli", e.getEli());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link ArticleNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(ArticleNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleException(final ArticleNotFoundException e) {
    log.error("ArticleNotFoundException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.NOT_FOUND
    );
    problemDetail.setProperty("eli", e.getEli());
    problemDetail.setProperty("eid", e.getEid());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link ElementNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(ElementNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleException(final ElementNotFoundException e) {
    log.error("ElementNotFoundException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.NOT_FOUND
    );
    problemDetail.setProperty("eli", e.getEli());
    problemDetail.setProperty("eid", e.getEid());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link
   * LoadSpecificArticlesXmlFromDokumentUseCase.ArticleOfTypeNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 404 status and the exception message.
   */
  @ExceptionHandler(LoadSpecificArticlesXmlFromDokumentUseCase.ArticleOfTypeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleException(
    final LoadSpecificArticlesXmlFromDokumentUseCase.ArticleOfTypeNotFoundException e
  ) {
    log.error("ArticleOfTypeNotFoundException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.NOT_FOUND
    );
    problemDetail.setProperty("eli", e.getEli());
    problemDetail.setProperty("articleType", e.getType());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link MandatoryNodeNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(MandatoryNodeNotFoundException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final MandatoryNodeNotFoundException e) {
    log.error("MandatoryNodeNotFoundException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    problemDetail.setProperty("xpath", e.getXpath());
    if (StringUtils.isNotEmpty(e.getEli())) {
      problemDetail.setProperty("eli", e.getEli());
    }
    if (StringUtils.isNotEmpty(e.getNode())) {
      problemDetail.setProperty("nodeName", e.getNode());
    }
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link NormExistsAlreadyException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(NormExistsAlreadyException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final NormExistsAlreadyException e) {
    log.error("NormExistsAlreadyException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    problemDetail.setProperty("eli", e.getEli());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link NormWithGuidAlreadyExistsException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(NormWithGuidAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final NormWithGuidAlreadyExistsException e) {
    log.error("NormWithGuidAlreadyExistsException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    problemDetail.setProperty("guid", e.getGuid());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link ActiveModDestinationNormNotFoundException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(ActiveModDestinationNormNotFoundException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final ActiveModDestinationNormNotFoundException e) {
    log.error("ActiveModDestinationNormNotFoundException: {}", e.getMessage(), e);
    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    problemDetail.setProperty("eli", e.getEli());
    problemDetail.setProperty("destinationEli", e.getDestinationEli());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link NotAXmlFileException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(NotAXmlFileException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final NotAXmlFileException e) {
    log.error("NotAXmlFileException: {}", e.getMessage(), e);

    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    problemDetail.setProperty("fileName", e.getFileName());
    problemDetail.setProperty("contentType", e.getContentType());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link NotLdmlDeXmlFileException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(NotLdmlDeXmlFileException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final NotLdmlDeXmlFileException e) {
    log.error("NotLdmlDeXmlFileException: {}", e.getMessage(), e);

    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    problemDetail.setProperty("fileName", e.getFileName());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link LdmlDeNotValidException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(LdmlDeNotValidException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final LdmlDeNotValidException e) {
    log.error("LdmlDeNotValidException: {}", e.getMessage(), e);

    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    problemDetail.setProperty("errors", e.getErrors());
    return problemDetail;
  }

  /**
   * Exception handler method for handling {@link LdmlDeSchematronException}.
   *
   * @param e The exception that occurred.
   * @return A {@link ResponseEntity} with an HTTP 422 status code and the exception message.
   */
  @ExceptionHandler(LdmlDeSchematronException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail handleException(final LdmlDeSchematronException e) {
    log.error("LdmlDeSchematronException: {}", e.getMessage(), e);

    final ProblemDetail problemDetail = ProblemDetailFactory.createProblemDetail(
      e,
      HttpStatus.UNPROCESSABLE_ENTITY
    );
    problemDetail.setProperty("errors", e.getErrors());
    return problemDetail;
  }
}
