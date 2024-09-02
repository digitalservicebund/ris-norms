package de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import java.net.URI;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

/**
 * Configuration class responsible for creating ProblemDetail instances for various exceptions. This
 * class provides a static method to create standardized ProblemDetail objects that conform to RFC
 * 9457 - Problem Details for HTTP APIs. Each exception type is mapped to a specific
 * ProblemDetailDescriptor that contains the problem's type URI and title.
 */
public class ProblemDetailFactory {
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
    final ProblemMapping problemMapping = ProblemMapping.getInstance(e.getClass());
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
    problemDetail.setType(problemMapping.getType());
    problemDetail.setTitle(problemMapping.getTitle());
    problemDetail.setDetail(e.getMessage());
    return problemDetail;
  }

  @Getter
  private enum ProblemMapping {
    NORM_NOT_FOUND(
        NormNotFoundException.class, URI.create("/errors/norm-not-found"), "Norm not found"),
    ARTICLE_NOT_FOUND(
        ArticleNotFoundException.class,
        URI.create("/errors/article-not-found"),
        "Article not found");

    /**
     * Creates the Enum
     *
     * @param clazz - Class to create the enum
     * @return A ProblemMapping enum
     */
    public static ProblemMapping getInstance(Class<? extends Throwable> clazz) {
      for (ProblemMapping mapping : ProblemMapping.values()) {
        if (mapping.clazz.equals(clazz)) {
          return mapping;
        }
      }
      throw new IllegalArgumentException(
          "No matching ProblemMapping found for class: " + clazz.getName());
    }

    private final Class<? extends Throwable> clazz;
    private final URI type;
    private final String title;

    ProblemMapping(Class<? extends Throwable> clazz, URI type, String title) {
      this.clazz = clazz;
      this.type = type;
      this.title = title;
    }
  }
}
