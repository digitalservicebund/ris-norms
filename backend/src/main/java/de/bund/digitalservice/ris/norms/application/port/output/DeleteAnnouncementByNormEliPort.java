package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing a port for deleting an {@link Announcement} and its corresponding {@link Norm}.
 */
public interface DeleteAnnouncementByNormEliPort {
  /**
   * Deletes an {@link Announcement} with its corresponding {@link Norm}
   *
   * @param command The command specifying the announcement to be deleted.
   */
  void deleteAnnouncementByNormEli(final Command command);

  /**
   * A record representing the command for deleting an announcement.
   *
   * @param eli of the corresponding norm.
   */
  record Command(String eli) {}
}
