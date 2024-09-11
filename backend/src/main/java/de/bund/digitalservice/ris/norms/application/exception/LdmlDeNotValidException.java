package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import org.xml.sax.SAXParseException;

/** The given xml is not a valid LDML.de 1.6 document. */
@Getter
public class LdmlDeNotValidException extends RuntimeException implements NormsAppException {
  private final List<ValidationErrorMessage> errors;

  public LdmlDeNotValidException(List<SAXParseException> validationErrors) {
    super("The provided xml is not a valid LDML.de 1.6 document.");
    this.errors =
        validationErrors.stream()
            .map(
                e ->
                    new ValidationErrorMessage(
                        "/errors/ldml-de-not-valid/" + e.getMessage().split(":")[0],
                        e.getLineNumber(),
                        e.getColumnNumber(),
                        e.getMessage()))
            .toList();
  }

  /**
   * A specific schema validation error
   *
   * @param type the type of the error
   * @param lineNumber the line of the xml file that causes the error
   * @param columnNumber the column of the line of the xml file that causes the error
   * @param detail the error message
   */
  public record ValidationErrorMessage(String type, int lineNumber, int columnNumber, String detail)
      implements Serializable {}
}
