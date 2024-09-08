package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/** Indicates that the requested norm does not exist. */
@Getter
public class NormNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;

  public NormNotFoundException(final String eli) {
    super("Norm with eli %s does not exist".formatted(eli));
    this.eli = eli;
  }
}
