package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;

/**
 * Interface representing the output port for deleting all {@link Dokument} entities from a storage location designated
 * for public data.
 */
public interface DeleteAllPublicDokumentePort {
  /**
   * Deletes all {@link Dokument} entities from a designated public storage location.
   */
  void deleteAllPublicDokumente();
}
