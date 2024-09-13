package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.util.Optional;

/**
 * Interface representing a port for updating an {@link Announcement}. Implementations of this
 * interface should provide functionality to update an announcement using the specified command.
 */
public interface UpdateAnnouncementPort {
  /**
   * Updates a {@link Announcement} based on the provided data in the command.
   *
   * @param command The command specifying the announcement to be updated.
   * @return An {@link Optional} containing the {@link Announcement} if found, or empty if not
   *     found.
   */
  Optional<Announcement> updateAnnouncement(final Command command);

  /**
   * A record representing the command for updating an announcement.
   *
   * @param announcement The updated announcement.
   */
  record Command(Announcement announcement) {}
}
