package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Norm} based on the ELI (European Legislation
 * Identifier). Implementations of this interface should provide functionality to load a norm using
 * the specified command.
 */
public interface LoadNormPort {
  /**
   * Loads a {@link Norm} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the norm to be loaded.
   * @return An {@link Optional} containing the loaded {@link Norm} if found, or empty if not found.
   */
  Optional<Norm> loadNorm(final Command command);

  /**
   * A record representing the command for loading a norm. The command includes the ELI (European
   * Legislation Identifier) to identify the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm in the command.
   */
  record Command(String eli) {}
}
