package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
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
   * @param command The command specifying the ELI to identify the xml representation of an amending
   *     law to be loaded.
   * @return An {@link Optional} containing the saved XML of an {@link AmendingLaw} if found, or
   *     empty if not found.
   */
  AmendingLaw updateAmendingLawByEli(final Command command);

  /**
   * A record representing the command for updating an amending law.
   *
   * @param amendingLawDto The new XML representation of the amending law.
   */
  record Command(AmendingLawDto amendingLawDto) {}
}
