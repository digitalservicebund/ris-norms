package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.LoadElementsByTypeFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticlesXmlFromNormUseCase;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.net.URI;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

/**
 * Factory class responsible for creating {@link ProblemDetail} instances for various exceptions.
 * This class provides a static method to create standardized {@link ProblemDetail} objects that
 * conform to RFC 9457 - Problem Details for HTTP APIs. Each exception type is mapped to a specific
 * type in form of a {@link URI} and a title. Handling {@link ValidationException} varies a bit
 * because that exception has many different types.
 */
public class ProblemDetailFactory {
  /**
   * Creates a {@link ProblemDetail} instance for the given exception and HTTP status. The {@link
   * ProblemDetail} is populated with information from a predefined mapping based on the exception
   * type.
   *
   * @param e The exception for which the {@link ProblemDetail} is being created.
   * @param status The HTTP status code associated with the exception.
   * @return A {@link ProblemDetail} object containing information about the problem, including its
   *     type, title, and detailed message.
   */
  public static ProblemDetail createProblemDetail(
      final NormsAppException e, final HttpStatus status) {
    final ProblemMapping problemMapping = ProblemMapping.getInstance(e.getClass());
    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
    if (e instanceof ValidationException validationException) {
      problemDetail.setType(URI.create(validationException.getErrorType().getType()));
    } else {
      problemDetail.setType(problemMapping.getType());
    }
    problemDetail.setTitle(problemMapping.getTitle());
    return problemDetail;
  }

  @Getter
  private enum ProblemMapping {
    NORM_NOT_FOUND(
        NormNotFoundException.class, URI.create("/errors/norm-not-found"), "Norm not found"),
    ARTICLE_NOT_FOUND(
        ArticleNotFoundException.class,
        URI.create("/errors/article-not-found"),
        "Article not found"),
    ARTICLE_OF_TYPE_NOT_FOUND(
        LoadSpecificArticlesXmlFromNormUseCase.ArticleOfTypeNotFoundException.class,
        URI.create("/errors/article-of-type-not-found"),
        "Article of specific type not found"),
    ANNOUNCEMENT_NOT_FOUND(
        AnnouncementNotFoundException.class,
        URI.create("/errors/announcement-not-found"),
        "Announcement not found"),
    ELEMENT_NOT_FOUND(
        ElementNotFoundException.class,
        URI.create("/errors/element-not-found"),
        "Element not found"),
    MANDATORY_NODE_NOT_FOUND(
        MandatoryNodeNotFoundException.class,
        URI.create("/errors/mandatory-node-not-foud"),
        "Mandatory node not found"),
    INVALID_UPDATE(
        InvalidUpdateException.class,
        URI.create("/errors/invalidate-update"),
        "Invalid update in XML"),
    XML_PROCESSING_ERROR(
        XmlProcessingException.class,
        URI.create("/errors/xml-processing-error"),
        "XML processing error"),
    UNSUPPORTED_ELEMENT_TYPE(
        LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException.class,
        URI.create("/errors/unsupported-element-type"),
        "Unsupported element type"),
    VALIDATION_ERROR(ValidationException.class, null, "Validation error");

    /**
     * Creates the Enum
     *
     * @param clazz - Class to create the enum
     * @return A {@link ProblemMapping} enum
     */
    public static ProblemMapping getInstance(Class<? extends NormsAppException> clazz) {
      for (ProblemMapping mapping : ProblemMapping.values()) {
        if (mapping.clazz.equals(clazz)) {
          return mapping;
        }
      }
      throw new IllegalArgumentException(
          "No matching ProblemMapping found for class: " + clazz.getName());
    }

    private final Class<? extends NormsAppException> clazz;
    private final URI type;
    private final String title;

    ProblemMapping(Class<? extends NormsAppException> clazz, URI type, String title) {
      this.clazz = clazz;
      this.type = type;
      this.title = title;
    }
  }
}
