package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentEli;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Dokument} based on the ELI (European Legislation
 * Identifier).
 */
public interface LoadDokumentPort {
  /**
   * Loads a {@link Dokument} based on the provided ELI specified in the options.
   *
   * @param options The options specifying the ELI to identify the {@link Dokument} to be loaded.
   * @return An {@link Optional} containing the loaded {@link Dokument} if found, or empty if not found.
   */
  Optional<Dokument> loadDokument(final Options options);

  /**
   * A record representing the options for loading a {@link Dokument} including the ELI
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the regelungstext.
   */
  record Options(DokumentEli eli) {}
}
