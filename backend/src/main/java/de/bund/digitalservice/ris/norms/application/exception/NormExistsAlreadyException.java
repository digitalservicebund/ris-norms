package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/**
 * A norm with the given eli already exists in the system and no two norms can have the same eli.
 */
@Getter
public class NormExistsAlreadyException extends RuntimeException implements NormsAppException {

  private final String eli;

  public NormExistsAlreadyException(String eli) {
    super("A norm with the eli %s already exists.".formatted(eli));
    this.eli = eli;
  }
}
