package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentEli;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Regelungstext} based on the ELI (European Legislation
 * Identifier).
 */
public interface LoadRegelungstextPort {
  /**
   * Loads a {@link Regelungstext} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the regelungstext to be loaded.
   * @return An {@link Optional} containing the loaded {@link Regelungstext} if found, or empty if not found.
   */
  Optional<Regelungstext> loadRegelungstext(final Command command);

  /**
   * A record representing the command for loading a regelungstext. The command includes the ELI (European
   * Legislation Identifier) to identify the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the regelungstext in the command.
   */
  record Command(DokumentEli eli) {}
}
