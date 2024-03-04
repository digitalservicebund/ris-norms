package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.Optional;

/**
 * Interface representing the use case for loading the xml representation of an {@link AmendingLaw}.
 * Implementations of this interface should provide functionality to load the xml representation of
 * an amending law based on a given query.
 */
public interface LoadAmendingLawXmlUseCase {

  /**
   * Loads a xml representation of {@link AmendingLaw} based on the provided query.
   *
   * @param query The query specifying the amending law to be loaded.
   * @return An {@link Optional} containing the loaded xml representation of {@link AmendingLaw} if
   *     found, or empty if not found.
   */
  Optional<String> loadAmendingLawXml(Query query);

  /**
   * A record representing the query for loading the xml representation of an amending law. The
   * query includes the ELI (European Legislation Identifier) to identify the amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     query.
   */
  record Query(String eli) {}
}
