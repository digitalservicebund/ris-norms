package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import java.net.URI;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

/**
 * Configuration class responsible for creating ProblemDetail instances for various exceptions. This
 * class provides a static method to create standardized ProblemDetail objects that conform to RFC
 * 9457 - Problem Details for HTTP APIs. Each exception type is mapped to a specific
 * ProblemDetailDescriptor that contains the problem's type URI and title.
 */
public class ProblemDetailConfig {

  // Static and final initialization of mappings
  private static final Map<Class<? extends Throwable>, ProblemDetailDescriptor> MAPPINGS =
      Map.of(
          NormNotFoundException.class,
          new ProblemDetailDescriptor(URI.create("/errors/norm-not-found"), "Norm not found"),
          ArticleNotFoundException.class,
          new ProblemDetailDescriptor(URI.create("/errors/article-not-found"), "Article not found")

          // Add more mappings as needed
          );

  /**
   * Creates a ProblemDetail instance for the given exception and HTTP status. The ProblemDetail is
   * populated with information from a predefined mapping based on the exception type.
   *
   * @param e The exception for which the ProblemDetail is being created.
   * @param status The HTTP status code associated with the exception.
   * @return A ProblemDetail object containing information about the problem, including its type,
   *     title, and detailed message.
   */
  public static ProblemDetail createProblemDetail(final Throwable e, final HttpStatus status) {
    final ProblemDetailDescriptor descriptor = MAPPINGS.get(e.getClass());
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
    problemDetail.setType(descriptor.type());
    problemDetail.setTitle(descriptor.title());
    problemDetail.setDetail(e.getMessage());
    return problemDetail;
  }

  /**
   * A record that describes the details of a specific problem type. It contains the type URI and
   * the title of the problem, which are used to populate the ProblemDetail object.
   *
   * @param type The URI representing the type of the problem.
   * @param title The title of the problem.
   */
  public record ProblemDetailDescriptor(URI type, String title) {}
}
