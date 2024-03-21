package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * Interface representing a port for setting the release timestamp an {@link AmendingLaw} based on
 * the ELI (European Legislation Identifier). Implementations of this interface should provide
 * functionality to set the release timestamp using the specified command.
 */
public interface SetAmendingLawReleasedAtTimestampPort {

  /**
   * Updates the release timestamp of an {@link AmendingLaw} based on the provided ELI specified in
   * the command.
   *
   * @param command The command specifying the ELI to identify the amending law where we set the
   *     relase timestamp.
   * @return An {@link Optional} containing the release timestamp of the {@link AmendingLaw} if
   *     found, or empty if not found.
   */
  Optional<Timestamp> setAmendingLawReleasedAt(final Command command);

  /**
   * A record representing the command for setting the release timestamp of an amending law. The
   * command includes the ELI (European Legislation Identifier) to identify the amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     command.
   * @param timestamp The timestamp of the release of the amending law
   */
  record Command(String eli, Timestamp timestamp) {}
}
