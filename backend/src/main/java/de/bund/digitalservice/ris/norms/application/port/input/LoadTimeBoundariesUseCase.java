package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import java.util.List;

/**
 * Interface representing the use case for loading a list of {@link TimeBoundary}. Implementations
 * of this interface should provide functionality to load all time boundaries related to a norm
 * based on a given query.
 */
public interface LoadTimeBoundariesUseCase {

  /**
   * Retrieves a list of time boundaries related to the specified norm based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return A list of {@link TimeBoundary} entities related to the specified norm.
   */
  List<TimeBoundary> loadTimeBoundariesOfNorm(Query query);

  /**
   * A record representing the parameters needed to query time boundaries related to a norm.
   *
   * @param eli The ELI used to identify the norm in the query.
   */
  record Query(String eli) {}
}
