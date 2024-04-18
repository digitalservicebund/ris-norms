package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.util.List;

/**
 * Interface representing the use case for loading all {@link Announcement}s. Implementations of
 * this interface should provide functionality to load all {@link Announcement}s available in the
 * system.
 */
public interface LoadAllAnnouncementsUseCase {

  /**
   * Loads all {@link Announcement}s available in the system.
   *
   * @return A {@link List} of {@link Announcement} objects, which may be empty if no {@link
   *     Announcement}s are found.
   */
  List<Announcement> loadAllAnnouncements();
}
