package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Release;

/**
 * Interface representing a port for saving a {@link Release} to an {@link Announcement}.
 */
public interface SaveReleaseToAnnouncementPort {
  /**
   * Saves a {@link Release} to the provided {@link Announcement}.
   *
   * @param command The command specifying the announcement and release.
   * @return the {@link Release} added to the announcement.
   */
  Release saveReleaseToAnnouncement(final Command command);

  /**
   * A record representing the command for saving a release to an announcement.
   *
   * @param release The release to be saved to the announcement
   * @param announcement The announcement to which the release should be saved
   */
  record Command(Release release, Announcement announcement) {}
}
