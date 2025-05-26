package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import java.time.Instant;

/**
 * Interface representing the output port for deleting all {@link Dokument} entities from a storage location designated.
 */
public interface DeleteAllPublishedDokumentePort {
  /**
   * Deletes all {@link Dokument} entities that have not been edited since the given date from a designated
   * storage location.
   *
   * @param options options for deleting Dokumente
   */
  void deleteAllPublishedDokumente(Options options);

  /**
   * Options for deleting dokumente
   *
   * @param lastChangeBefore Dokumente last edited after the given date are not deleted.
   */
  record Options(Instant lastChangeBefore) {}
}
