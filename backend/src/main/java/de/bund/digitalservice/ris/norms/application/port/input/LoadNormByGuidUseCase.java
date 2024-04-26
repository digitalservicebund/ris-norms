package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing the use case for loading a {@link Norm}. Implementations of this interface
 * should provide functionality to load a norm based on a given query.
 */
public interface LoadNormByGuidUseCase {

  /**
   * Retrieves a norm based on the provided query.
   *
   * @param query The query containing the GUID of the norm.
   * @return An {@link Optional} containing the loaded {@link Norm} if found, or empty if not found.
   */
  Optional<Norm> loadNormByGuid(Query query);

  /**
   * A record representing the query for loading a norm. The query includes the GUID to identify the
   * norm.
   *
   * @param guid The GUID used to identify the norm in the query.
   */
  record Query(UUID guid) {}
}
