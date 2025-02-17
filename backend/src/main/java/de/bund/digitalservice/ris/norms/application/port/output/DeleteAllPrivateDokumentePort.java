package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;

/**
 * Interface representing the output port for deleting multiple {@link Dokument} entities from a storage location designated
 * for private data.
 */
public interface DeleteAllPrivateDokumentePort {
  /**
   * Deletes the specified {@link Dokument} entities from a designated private storage location.
   *
   */
  void deleteAllPrivateDokumente();
}
