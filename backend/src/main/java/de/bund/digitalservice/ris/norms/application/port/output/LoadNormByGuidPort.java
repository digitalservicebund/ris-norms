package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing a port for loading a {@link Norm} based on the GUID. Implementations of
 * this interface should provide functionality to load a norm using the specified command.
 */
public interface LoadNormByGuidPort {

  /**
   * Loads a {@link Norm} based on the provided GUID specified in the command.
   *
   * @param command The command specifying the GUID to identify the norm to be loaded.
   * @return An {@link Optional} containing the loaded {@link Norm} if found, or empty if not found.
   */
  Optional<Norm> loadNormByGuid(final Command command);

  /**
   * A record representing the command for loading a norm. The command includes the GUID to identify
   * the norm.
   *
   * @param guid The GUID used to identify the norm in the command.
   */
  record Command(UUID guid) {}
}
