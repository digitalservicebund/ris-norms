package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.UUID;

/**
 * Interface representing a port for deleting a {@link Norm} based on the GUID.
 */
public interface DeleteNormByGuidPort {
  /**
   * Deletes a {@link Norm} based on the provided GUID specified in the command.
   *
   * @param command The command specifying the GUID to identify the norm to be loaded.
   */
  void loadNormByGuid(final Command command);

  /**
   * A record representing the command for deleting a norm. The command includes the GUID to identify
   * the norm.
   *
   * @param guid The GUID used to identify the norm in the command.
   */
  record Command(UUID guid) {}
}
