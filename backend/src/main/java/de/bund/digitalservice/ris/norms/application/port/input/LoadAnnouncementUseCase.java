package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.exception.AnnouncementNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;

/**
 * Interface representing the use case for loading a single {@link Announcement}s using its expression eli.
 */
public interface LoadAnnouncementUseCase {
  /**
   * Retrieves an announcement based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the dokument.
   * @return The loaded {@link Announcement}
   * @throws AnnouncementNotFoundException if {@link Announcement} could not be found
   */
  Announcement loadAnnouncement(Query query);

  /**
   * A record representing the query for loading an announcement. The query includes the ELI (European
   * Legislation Identifier) to identify the dokument.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the announcement in the query.
   */
  record Query(NormExpressionEli eli) {}
}
