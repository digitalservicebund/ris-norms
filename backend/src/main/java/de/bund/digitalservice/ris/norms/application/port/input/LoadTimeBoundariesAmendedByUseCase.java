package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;

/**
 * Interface representing the use case for loading a list of {@link TimeBoundary} of a regelungstext
 * containing passive mods. It will filter out those time boundaries that were introduced by the
 * given amending law.
 */
public interface LoadTimeBoundariesAmendedByUseCase {
  /**
   * Retrieves a list of time boundaries related to the specified regelungstext filtered by the amending law
   * eli.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the regelungstext.
   * @return A list of {@link TimeBoundary} entities related to the specified regelungstext.
   */
  List<TimeBoundary> loadTimeBoundariesAmendedBy(Query query);

  /**
   * A record representing the parameters needed to query time boundaries related to a regelungstext.
   *
   * @param eli The ELI used to identify the regelungstext in the query.
   * @param amendingLawEli The ELI of the amending law.
   */
  record Query(DokumentExpressionEli eli, DokumentExpressionEli amendingLawEli) {}
}
