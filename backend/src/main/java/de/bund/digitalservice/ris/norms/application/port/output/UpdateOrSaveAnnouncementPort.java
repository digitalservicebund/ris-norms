package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;

/**
 * Interface representing a port for saving or updating a {@link Announcement}. Implementations of
 * this interface should provide functionality to update an announcement using the specified
 * command.
 */
public interface UpdateOrSaveAnnouncementPort {
  /**
   * Updates or saves a {@link Announcement} based on the provided data in the command.
   *
   * @param command The command specifying the announcement to be saved.
   * @return the saved {@link Announcement}.
   */
  Announcement updateOrSaveAnnouncement(final Command command);

  /**
   * A record representing the command for updating or saving an announcement.
   *
   * @param announcement The announcement to be saved/updated
   */
  record Command(Announcement announcement) {}
}
