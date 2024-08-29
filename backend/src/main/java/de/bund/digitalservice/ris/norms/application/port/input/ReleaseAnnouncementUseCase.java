package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for releasing an {@link Announcement} and the corresponding
 * {@link Norm}s.
 */
public interface ReleaseAnnouncementUseCase {

  /**
   * Releases an {@link Announcement} and the corresponding {@link Norm}s based on the provided
   * query.
   *
   * @param query The query specifying the {@link Announcement} to be loaded.
   * @return The released {@link Announcement}.
   */
  Announcement releaseAnnouncement(Query query);

  /**
   * A record representing the query for releasing an {@link Announcement}. The query includes the
   * ELI (European Legislation Identifier) to identify the {@link Norm} of the {@link Announcement}.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the {@link Norm} of the
   *     {@link Announcement}.
   */
  record Query(String eli) {}
}
