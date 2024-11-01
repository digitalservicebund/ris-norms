package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.Eli;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Release} based on the ELI (European Legislation
 * Identifier). Implementations of this interface should provide functionality to load a release using
 * the specified command.
 */
public interface LoadLatestReleasePort {
  /**
   * Loads the latests {@link Release} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the announcement whose latest Release should be loaded.
   * @return An {@link Optional} containing the loaded {@link Release} if found, or empty if not found.
   */
  Optional<Release> loadLatestRelease(final Command command);

  /**
   * A record representing the command for loading a release. The command includes the ELI (European
   * Legislation Identifier) to identify the announcement whose latest Release should be loaded.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the release in the command.
   */
  record Command(Eli eli) {}
}
