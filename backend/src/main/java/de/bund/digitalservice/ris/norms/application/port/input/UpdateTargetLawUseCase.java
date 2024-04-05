package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.w3c.dom.Document;

/**
 * Interface representing the use case for updating a {@link TargetLaw}. Implementations of this
 * interface should provide functionality to load and update a target law based on a given query.
 */
public interface UpdateTargetLawUseCase {

  /**
   * Updates the target law based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the target law
   *     and an updated version of the xml.
   * @return An {@link Optional} containing the loaded {@link String} if found, or empty if not
   *     found.
   */
  Optional<Document> updateTargetLaw(Query query);

  /**
   * A record representing the parameters needed to query a target law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the target law in the
   *     query.
   * @param newTargetLawXml the updated target law
   */
  record Query(String eli, Document newTargetLawXml) {}
}
