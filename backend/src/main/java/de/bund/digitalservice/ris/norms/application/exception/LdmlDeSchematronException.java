package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;

/** The given xml is not a schematron-valid LDML.de 1.6 document. */
@Getter
public class LdmlDeSchematronException extends RuntimeException implements NormsAppException {
  private final List<ValidationError> errors;

  /**
   * Creates a new exception for failed schematron validation.
   *
   * @param errors the individual validation problems
   */
  public LdmlDeSchematronException(List<ValidationError> errors) {
    super("The provided xml is not a schematron-valid LDML.de 1.6 document.");
    this.errors = errors;
  }

  /**
   * A specific schematron validation error
   *
   * @param type the type of the error
   * @param xPath the xPath to the node that causes the error
   * @param details the error message
   * @param eId the eId of the node that causes the error
   */
  public record ValidationError(String type, String xPath, String details, String eId)
      implements Serializable {}
}
