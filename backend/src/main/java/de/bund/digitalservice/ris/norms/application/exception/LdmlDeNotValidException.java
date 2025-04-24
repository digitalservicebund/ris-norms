package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import lombok.Getter;

/**
 * The given xml is not a valid LDML.de 1.7 document.
 */
@Getter
public class LdmlDeNotValidException extends RuntimeException implements NormsAppException {

  private final List<ValidationError> errors;
  public static final URI TYPE = URI.create("/errors/ldml-de-not-valid");

  public LdmlDeNotValidException(List<ValidationError> errors) {
    super(
      """
      The provided xml is not a valid LDML.de 1.7.2 document:
      %s
      """.formatted(
          errors.stream().map(ValidationError::toString).collect(Collectors.joining("\n"))
        )
    );
    this.errors = errors;
  }

  @Override
  public URI getType() {
    return TYPE;
  }

  @Override
  public String getTitle() {
    return "The provided xml is not a valid LDML.de 1.7.2 document";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("errors", getErrors());
  }

  /**
   * A specific schema validation error
   *
   * @param type the type of the error
   * @param lineNumber the line of the xml file that causes the error
   * @param columnNumber the column of the line of the xml file that causes the error
   * @param detail the error message
   * @param file the name of the file in which this error happened. Might be null if the file is not known.
   */
  public record ValidationError(
    URI type,
    int lineNumber,
    int columnNumber,
    String detail,
    @Nullable String file
  )
    implements Serializable {
    public ValidationError(URI type, int lineNumber, int columnNumber, String detail) {
      this(type, lineNumber, columnNumber, detail, null);
    }
  }
}
