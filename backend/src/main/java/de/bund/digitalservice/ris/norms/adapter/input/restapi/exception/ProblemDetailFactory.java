package de.bund.digitalservice.ris.norms.adapter.input.restapi.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

/**
 * Factory class responsible for creating {@link ProblemDetail} instances for various exceptions.
 * This class provides a static method to create standardized {@link ProblemDetail} objects that
 * conform to RFC 9457 - Problem Details for HTTP APIs. Each exception type is mapped to a specific
 * type in form of a {@link URI} and a title.
 */
public class ProblemDetailFactory {

  private ProblemDetailFactory() {}

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
    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
    problemDetail.setType(e.getType());
    problemDetail.setTitle(e.getTitle());
    problemDetail.setProperties(e.getProperties());
    return problemDetail;
  }
}
