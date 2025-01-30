package de.bund.digitalservice.ris.norms.adapter.input.restapi.exception;

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
    final NormsAppException e,
    final HttpStatus status
  ) {
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
      NormNotFoundException.class,
      URI.create("/errors/norm-not-found"),
      "Norm not found"
    ),
    REGELUNGSTEXT_NOT_FOUND(
      RegelungstextNotFoundException.class,
      URI.create("/errors/regelungstext-not-found"),
      "Regelungstext not found"
    ),
    ARTICLE_NOT_FOUND(
      ArticleNotFoundException.class,
      URI.create("/errors/article-not-found"),
      "Article not found"
    ),
    ARTICLE_OF_TYPE_NOT_FOUND(
      LoadSpecificArticlesXmlFromNormUseCase.ArticleOfTypeNotFoundException.class,
      URI.create("/errors/article-of-type-not-found"),
      "Article of specific type not found"
    ),
    ANNOUNCEMENT_NOT_FOUND(
      AnnouncementNotFoundException.class,
      URI.create("/errors/announcement-not-found"),
      "Announcement not found"
    ),
    ELEMENT_NOT_FOUND(
      ElementNotFoundException.class,
      URI.create("/errors/element-not-found"),
      "Element not found"
    ),
    MANDATORY_NODE_NOT_FOUND(
      MandatoryNodeNotFoundException.class,
      URI.create("/errors/mandatory-node-not-found"),
      "Mandatory node not found"
    ),
    INVALID_UPDATE(
      InvalidUpdateException.class,
      URI.create("/errors/invalidate-update"),
      "Invalid update operation"
    ),
    XML_PROCESSING_ERROR(
      XmlProcessingException.class,
      URI.create("/errors/xml-processing-error"),
      "XML processing error"
    ),
    UNSUPPORTED_ELEMENT_TYPE(
      LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException.class,
      URI.create("/errors/unsupported-element-type"),
      "Unsupported element type"
    ),
    NORM_WITH_ELI_EXISTS_ALREADY(
      NormExistsAlreadyException.class,
      URI.create("/errors/norm-with-eli-exists-already"),
      "Norm with ELI exists already"
    ),
    NORM_WITH_GUID_EXISTS_ALREADY(
      NormWithGuidAlreadyExistsException.class,
      URI.create("/errors/norm-with-guid-exists-already"),
      "Norm with GUID exists already"
    ),
    ACTIVE_MOD_DESTINATION_NORM_NOT_FOUND(
      ActiveModDestinationNormNotFoundException.class,
      URI.create("/errors/active-mod/destination/norm-not-found"),
      "Destination norm not found"
    ),
    NOT_A_XML_FILE(
      NotAXmlFileException.class,
      URI.create("/errors/not-a-xml-file"),
      "The provided file is not a xml file"
    ),
    NOT_LDML_DE_FILE(
      NotLdmlDeXmlFileException.class,
      URI.create("/errors/not-a-ldml-de-xml-file"),
      "The provided xml file is not a LDML.de xml file"
    ),
    LDML_DE_NOT_VALID(
      LdmlDeNotValidException.class,
      LdmlDeNotValidException.TYPE,
      "The provided xml is not a valid LDML.de 1.7 document"
    ),
    LDML_DE_SCHEMATRON_EXCEPTION(
      LdmlDeSchematronException.class,
      URI.create("/errors/ldml-de-not-schematron-valid"),
      "The provided xml is not a schematron-valid LDML.de 1.7.2 document"
    ),
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
        "No matching ProblemMapping found for class: " + clazz.getName()
      );
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
