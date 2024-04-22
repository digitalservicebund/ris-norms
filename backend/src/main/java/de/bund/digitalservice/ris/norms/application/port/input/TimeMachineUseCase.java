package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;

/**
 * Interface representing the use case for applying the time machine on a {@link Norm} using the
 * modifications defined in the amending law
 */
public interface TimeMachineUseCase {

  /**
   * Starts the application of the time machine.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of a norm and the
   *     xml of the amending law
   * @return An {@link String} containing the updated {@link Norm}.
   */
  Optional<String> applyTimeMachine(Query query);

  /**
   * A record representing the query for loading the xml representation of a norm and the string
   * representation of the amending law xml. The query includes the ELI (European Legislation
   * Identifier) to identify the target law.
   *
   * @param targetLawEli The ELI (European Legislation Identifier) used to identify the norm in the
   *     query.
   * @param amendingLawXml The String representation of the amending law xml
   */
  record Query(String targetLawEli, String amendingLawXml) {}
}
