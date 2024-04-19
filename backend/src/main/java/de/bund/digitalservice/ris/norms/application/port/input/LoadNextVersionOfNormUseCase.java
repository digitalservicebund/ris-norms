package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;

/** Interface representing the use case for loading the next version of a {@link Norm}. */
public interface LoadNextVersionOfNormUseCase {

  /**
   * Retrieves the next version of a norm based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return An {@link Optional} containing the next version of the {@link Norm} if found, or empty
   *     if not found.
   */
  Optional<Norm> loadNextVersionOfNorm(Query query);

  /**
   * A record representing the query for loading the next version of a {@link Norm}. The query
   * includes the ELI (European Legislation Identifier) to identify the {@link Norm}.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the {@link Norm} in the
   *     query.
   */
  record Query(String eli) {}
}
