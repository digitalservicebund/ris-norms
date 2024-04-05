package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;

import org.w3c.dom.Document;

/**
 * Interface representing the use case for loading the xml representation of a {@link TargetLaw}.
 * Implementations of this interface should provide functionality to load the xml representation of
 * a target law based on a given query.
 */
public interface LoadTargetLawXmlUseCase {

  /**
   * Retrieves the xml representation of a target law based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the target law.
   * @return An {@link Optional} containing the loaded {@link TargetLaw} if found, or empty if not
   *     found.
   */
  Optional<Document> loadTargetLawXml(Query query);

  /**
   * A record representing the query for loading the xml representation of a target law. The query
   * includes the ELI (European Legislation Identifier) to identify the target law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     query.
   */
  record Query(String eli) {}
}
