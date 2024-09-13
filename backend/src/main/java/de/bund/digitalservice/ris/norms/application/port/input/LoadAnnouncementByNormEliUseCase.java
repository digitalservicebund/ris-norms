package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;

/**
 * Interface representing the use case for loading an {@link Announcement}. Implementations of this
 * interface should provide functionality to load an {@link Announcement} based on a given query.
 */
public interface LoadAnnouncementByNormEliUseCase {
  /**
   * Retrieves an {@link Announcement} based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm of the
   *     {@link Announcement}.
   * @return The loaded {@link Announcement}.
   */
  Announcement loadAnnouncementByNormEli(Query query);

  /**
   * A record representing the query for loading an {@link Announcement}. The query includes the ELI
   * (European Legislation Identifier) to identify the norm of the {@link Announcement}.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm of the {@link
   *     Announcement} in the query.
   */
  record Query(String eli) {}
}
