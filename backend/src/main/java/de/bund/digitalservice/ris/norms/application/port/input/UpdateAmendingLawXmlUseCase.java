package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.Optional;

/**
 * Interface representing the use case for updating the XML representation of an amending law.
 * Implementations of this interface should provide functionality to update the XML based on a given
 * query.
 */
public interface UpdateAmendingLawXmlUseCase {

  /**
   * Updates a xml representation of {@link AmendingLaw} based on the provided query.
   *
   * @param query The query specifying the amending law to be loaded.
   * @return An {@link Optional} containing the saved xml representation of {@link AmendingLaw} if
   *     found, or empty if not found.
   */
  Optional<String> updateAmendingLawXml(Query query);

  /**
   * A record representing the query for updating the XML representation of an amending law. The
   * query includes the ELI (European Legislation Identifier) to identify the amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     query.
   * @param xml The new XML representation of the amending law.
   */
  record Query(String eli, String xml) {}
}
