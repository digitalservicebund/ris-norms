package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import lombok.Getter;

/** The given xml is not a valid LDML.de 1.6 document. */
@Getter
public class LdmlDeNotValidException extends RuntimeException implements NormsAppException {
  private final List<ValidationError> errors;
  public static final URI TYPE = URI.create("/errors/ldml-de-not-valid");

  public LdmlDeNotValidException(List<ValidationError> errors) {
    super("The provided xml is not a valid LDML.de 1.6 document.");
    this.errors = errors;
  }

  /**
   * A specific schema validation error
   *
   * @param type the type of the error
   * @param lineNumber the line of the xml file that causes the error
   * @param columnNumber the column of the line of the xml file that causes the error
   * @param detail the error message
   */
  public record ValidationError(URI type, int lineNumber, int columnNumber, String detail)
      implements Serializable {
    public ValidationError(URI type, int lineNumber, int columnNumber, String detail) {
      this.type = URI.create(LdmlDeNotValidException.TYPE + "/").resolve(type);
      this.lineNumber = lineNumber;
      this.columnNumber = columnNumber;
      this.detail = detail;
    }
  }
}
