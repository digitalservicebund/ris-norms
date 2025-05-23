package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import java.util.Optional;

/**
 * Interface representing a port for updating a {@link Dokument}.
 */
public interface UpdateDokumentPort {
  /**
   * Updates a {@link Dokument} based on the provided data in the options.
   *
   * @param options The options specifying the {@link Dokument} to be updated.
   * @return An {@link Optional} containing the {@link Dokument} if found, or empty if not found.
   */
  Optional<Dokument> updateDokument(final Options options);

  /**
   * A record representing the options for updating a {@link Dokument}.
   *
   * @param dokument The updated {@link Dokument}.
   */
  record Options(Dokument dokument) {}
}
