package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;

/**
 * Interface representing the use case for loading all target {@link Norm}s (zf0s) that are released
 * when an {@link Announcement} is released. Implementations of this interface should provide
 * functionality to load these {@link Norm}s based on a given query.
 */
public interface LoadTargetNormsAffectedByAnnouncementUseCase {

  /**
   * Retrieves all target {@link Norm}s that are released when an {@link Announcement} is released
   * based on the provided query. (These are the zf0 versions of the target laws)
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm of the
   *     {@link Announcement}.
   * @return A {@link List} containing the affected {@link Norm}s.
   */
  List<Norm> loadTargetNormsAffectedByAnnouncement(Query query);

  /**
   * A record representing the query for loading all target {@link Norm}s that are released when an
   * {@link Announcement} is released. The query includes the ELI (European Legislation Identifier)
   * to identify the norm of the {@link Announcement}.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm of the {@link
   *     Announcement} in the query.
   */
  record Query(String eli) {}
}
