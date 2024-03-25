package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.Optional;

/**
 * Interface representing a port for updating an {@link AmendingLaw}. Implementations of this
 * interface should provide functionality to update an amending law using the specified command.
 */
public interface UpdateAmendingLawPort {

  /**
   * Updates an {@link AmendingLaw} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the amending law to be updated.
   * @return An {@link Optional} containing the {@link AmendingLaw} if found, or empty if not found.
   */
  Optional<AmendingLaw> updateAmendingLaw(final Command command);

  /**
   * A record representing the command for updating an amending law.
   *
   * @param amendingLaw The updated amending law.
   */
  record Command(AmendingLaw amendingLaw) {}
}
