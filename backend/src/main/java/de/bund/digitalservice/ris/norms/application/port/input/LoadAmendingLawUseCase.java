package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLawWithArticles;
import java.util.Optional;

/**
 * Interface representing the use case for loading a {@link AmendingLawWithArticles}.
 * Implementations of this interface should provide functionality to load a amending law based on a
 * given query.
 */
public interface LoadAmendingLawUseCase {

  /**
   * Loads a {@link AmendingLawWithArticles} based on the provided query.
   *
   * @param query The query specifying the amending law to be loaded.
   * @return An {@link Optional} containing the loaded {@link AmendingLawWithArticles} if found, or
   *     empty if not found.
   */
  Optional<AmendingLawWithArticles> loadAmendingLaw(Query query);

  /**
   * A record representing the query for loading a amending law. The query includes the ELI
   * (European Legislation Identifier) to identify the amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     query.
   */
  record Query(String eli) {}
}
