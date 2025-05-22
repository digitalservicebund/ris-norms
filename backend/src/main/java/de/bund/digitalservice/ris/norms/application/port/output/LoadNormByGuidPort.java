package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing a port for loading a {@link Norm} based on the GUID.
 */
public interface LoadNormByGuidPort {
  /**
   * Loads a {@link Norm} based on the provided GUID specified in the options.
   *
   * @param options The options specifying the GUID to identify the norm to be loaded.
   * @return An {@link Optional} containing the loaded {@link Norm} if found, or empty if not found.
   */
  Optional<Norm> loadNormByGuid(final Options options);

  /**
   * A record representing the options for loading a norm.
   *
   * @param guid The GUID used to identify the norm.
   */
  record Options(UUID guid) {}
}
