package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/** The norm referenced in an akn:activeModifications is not in the database. */
@Getter
public class ActiveModDestinationNormNotFoundException extends RuntimeException
    implements NormsAppException {

  private final String eli;

  public ActiveModDestinationNormNotFoundException(String destinationEli) {
    super("Destination norm (%s) for active modification not found.".formatted(destinationEli));
    this.eli = destinationEli;
  }
}
