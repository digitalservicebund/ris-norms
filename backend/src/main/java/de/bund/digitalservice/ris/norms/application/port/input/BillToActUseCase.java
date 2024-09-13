package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for converting a bill to an act both of which are a {@link
 * Norm}. This is necessary when receiving data from E-Gesetzgebung instead of E-Verkündung needed
 * for the "mini-cycle"
 */
public interface BillToActUseCase {
  /**
   * Converts a bill to an act.
   *
   * @param query The query contains the bill to be converted
   * @return A {@link Norm} which is in the state of an act
   */
  Norm convert(Query query);

  /**
   * A record representing the query for converting a bill into an act.
   *
   * @param norm The norm in the form of a bill
   */
  record Query(Norm norm) {}
}
