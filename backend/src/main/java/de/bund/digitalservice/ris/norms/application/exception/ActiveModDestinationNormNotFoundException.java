package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/** The norm referenced in an akn:activeModifications is not in the database. */
@Getter
public class ActiveModDestinationNormNotFoundException
  extends RuntimeException
  implements NormsAppException {

  private final String eli;
  private final String destinationEli;

  public ActiveModDestinationNormNotFoundException(String eli, String destinationEli) {
    super(
      "Destination norm (%s) for active modification in norm (%s) not found.".formatted(
          destinationEli,
          eli
        )
    );
    this.eli = eli;
    this.destinationEli = destinationEli;
  }
}
