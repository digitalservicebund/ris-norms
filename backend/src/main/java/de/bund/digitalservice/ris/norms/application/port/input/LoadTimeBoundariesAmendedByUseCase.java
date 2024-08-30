package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import java.util.List;

/**
 * Interface representing the use case for loading a list of {@link TimeBoundary} of a norm
 * containing passive mods. It will filter out those time boundaries that were introduced by the
 * given amending law.
 */
public interface LoadTimeBoundariesAmendedByUseCase {
  /**
   * Retrieves a list of time boundaries related to the specified norm filtered by the amending law
   * eli.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the norm.
   * @return A list of {@link TimeBoundary} entities related to the specified norm.
   */
  List<TimeBoundary> loadTimeBoundariesAmendedBy(Query query);

  /**
   * A record representing the parameters needed to query time boundaries related to a norm.
   *
   * @param eli The ELI used to identify the norm in the query.
   * @param amendingLawEli The ELI of the amending law.
   */
  record Query(String eli, String amendingLawEli) {}
}
