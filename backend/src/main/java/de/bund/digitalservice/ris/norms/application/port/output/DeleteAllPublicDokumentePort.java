package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import java.time.Instant;

/**
 * Interface representing the output port for deleting all {@link Dokument} entities from a storage location designated
 * for public data.
 */
public interface DeleteAllPublicDokumentePort {
  /**
   * Deletes all {@link Dokument} entities that have not been edited since the given date from a designated public
   * storage location.
   *
   * @param command command for deleting Dokumente
   */
  void deleteAllPublicDokumente(DeleteAllPublicDokumentePort.Command command);

  /**
   * Command for deleting dokumente
   *
   * @param lastChangeBefore Dokumente last edited after the given date are not deleted.
   */
  record Command(Instant lastChangeBefore) {}
}
