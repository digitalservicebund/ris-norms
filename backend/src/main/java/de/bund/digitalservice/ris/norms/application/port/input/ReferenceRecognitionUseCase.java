package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for running the regex pattern recognition for references in
 * the xml representation of a {@link Norm}.
 */
public interface ReferenceRecognitionUseCase {
  /**
   * Runs the regex pattern recognition for references in the norm by the given ELI and updates the
   * XML of it in the DB.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return The updated XML of the {@link Norm}.
   */
  String findAndCreateReferences(Query query);

  /**
   * A record representing the query for loading the xml representation of a norm. The query
   * includes the ELI (European Legislation Identifier) to identify the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm in the query.
   */
  record Query(String eli) {}
}
