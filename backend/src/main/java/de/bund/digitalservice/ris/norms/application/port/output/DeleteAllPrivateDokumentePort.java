package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import java.time.Instant;

/**
 * Interface representing the output port for deleting multiple {@link Dokument} entities from a storage location designated
 * for private data.
 */
public interface DeleteAllPrivateDokumentePort {
  /**
   * Deletes all {@link Dokument} entities that have not been edited since the given date from a designated private
   * storage location.
   *
   * @param options options for deleting Dokumente
   */
  void deleteAllPrivateDokumente(Options options);

  /**
   * Options for deleting dokumente
   *
   * @param lastChangeBefore Dokumente last edited after the given date are not deleted.
   */
  record Options(Instant lastChangeBefore) {}
}
