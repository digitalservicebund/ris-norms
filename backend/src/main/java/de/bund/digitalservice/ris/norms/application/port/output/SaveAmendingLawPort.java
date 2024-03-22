package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;

/**
 * Interface representing a port for saving an {@link AmendingLaw}. Implementations of this
 * interface should provide functionality to save an amending law using the specified command.
 */
public interface SaveAmendingLawPort {

  /**
   * Saves an {@link AmendingLaw} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the amending law to be loaded.
   * @return An {@link AmendingLaw}
   */
  AmendingLaw saveAmendingLawByEli(final Command command);

  /**
   * A record representing the command for updating an amending law.
   *
   * @param amendingLawDto The new XML representation of the amending law.
   */
  record Command(AmendingLawDto amendingLawDto) {}
}
