package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.Optional;

/**
 * Interface representing the use case for loading a {@link AmendingLaw}. Implementations of this
 * interface should provide functionality to load a amending law based on a given query.
 */
public interface LoadAmendingLawUseCase {

  /**
   * Loads a {@link AmendingLaw} based on the provided query.
   *
   * @param query The query specifying the amending law to be loaded.
   * @return An {@link Optional} containing the loaded {@link AmendingLaw} if found, or empty if not
   *     found.
   */
  Optional<AmendingLaw> loadAmendingLaw(Query query);

  /**
   * A record representing the query for loading a amending law. The query includes the ELI
   * (European Legislation Identifier) to identify the amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     query.
   */
  record Query(String eli) {}
}
