package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.util.List;

/** Port interface for loading all {@link Announcement}s from the storage. */
public interface LoadAllAnnouncementsPort {

  /**
   * Loads all {@link Announcement}s available in the system.
   *
   * @return A {@link List} of {@link Announcement}, which may be empty if no Announcement is found.
   */
  List<Announcement> loadAllAnnouncements();
}
