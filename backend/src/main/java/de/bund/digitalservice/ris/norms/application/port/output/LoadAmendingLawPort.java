package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLawWithArticles;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link AmendingLawWithArticles} based on the ELI
 * (European Legislation Identifier). Implementations of this interface should provide functionality
 * to load a amending law using the specified command.
 */
public interface LoadAmendingLawPort {

  /**
   * Loads a {@link AmendingLawWithArticles} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the amending law to be loaded.
   * @return An {@link Optional} containing the loaded {@link AmendingLawWithArticles} if found, or
   *     empty if not found.
   */
  Optional<AmendingLawWithArticles> loadAmendingLawByEli(final Command command);

  /**
   * A record representing the command for loading a amending law. The command includes the ELI
   * (European Legislation Identifier) to identify the amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     command.
   */
  record Command(String eli) {}
}
