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
   * Loads a {@link Regelungstext} based on the provided ELI specified in the options.
   *
   * @param options The options specifying the ELI to identify the regelungstext to be loaded.
   * @return An {@link Optional} containing the loaded {@link Regelungstext} if found, or empty if not found.
   */
  Optional<Regelungstext> loadRegelungstext(final Options options);

  /**
   * A record representing the options for loading a regelungstext.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the regelungstext.
   */
  record Options(DokumentEli eli) {}
}
