package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundaryChangeData;
import java.util.List;

/**
 * Interface representing the use case for updating a list of {@link TimeBoundary}. Implementations
 * of this interface should provide functionality to update all time boundaries related to a norm
 * based on a given query.
 */
public interface UpdateTimeBoundariesUseCase {

  /**
   * Updates a list of time boundaries related to the specified norm based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return A list of {@link TimeBoundary} entities related to the specified norm.
   */
  List<TimeBoundary> updateTimeBoundariesOfNorm(Query query);

  /**
   * A record representing the parameters needed to update time boundaries related to a norm.
   *
   * @param eli The ELI used to identify the norm in the query.
   * @param timeBoundaries The list of the changed time boundaries.
   */
  record Query(String eli, List<TimeBoundaryChangeData> timeBoundaries) {}
}
