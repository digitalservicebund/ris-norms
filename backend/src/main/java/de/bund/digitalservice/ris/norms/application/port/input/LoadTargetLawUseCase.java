package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;

/**
 * Interface representing the use case for loading a {@link TargetLaw}. Implementations of this
 * interface should provide functionality to load a target law based on a given query.
 */
public interface LoadTargetLawUseCase {

  /**
   * Retrieves an article related to the specified amending law based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the target law.
   * @return An {@link Optional} containing the loaded {@link TargetLaw} if found, or empty if not
   *     found.
   */
  Optional<TargetLaw> loadTargetLaw(Query query);

  /**
   * A record representing the parameters needed to query a target law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the target law in the
   *     query.
   */
  record Query(String eli) {}
}
