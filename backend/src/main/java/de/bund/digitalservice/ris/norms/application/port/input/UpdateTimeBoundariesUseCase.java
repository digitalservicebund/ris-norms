package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundaryChangeData;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;

/**
 * Interface representing the use case for updating a list of {@link TimeBoundary}. Implementations
 * of this interface should provide functionality to update all time boundaries related to a regelungstext
 * based on a given query.
 */
public interface UpdateTimeBoundariesUseCase {
  /**
   * Updates a list of time boundaries related to the specified regelungstext based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the regelungstext.
   * @return A list of {@link TimeBoundary} entities related to the specified regelungstext.
   */
  List<TimeBoundary> updateTimeBoundariesOfRegelungstext(Query query);

  /**
   * A record representing the parameters needed to update time boundaries related to a regelungstext.
   *
   * @param eli The ELI used to identify the regelungstext in the query.
   * @param timeBoundaries The list of the changed time boundaries.
   */
  record Query(DokumentExpressionEli eli, List<TimeBoundaryChangeData> timeBoundaries) {}
}
