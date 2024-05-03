package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.time.Instant;

/**
 * Interface representing the use case for applying passive changes to a {@link Norm} up until a
 * given date.
 */
public interface ApplyPassiveModificationsUseCase {

  /**
   * Applies the passive modifications of the norm. Only applies "aenderungsbefehl-ersetzen".
   *
   * @param query The query contains the norm and date until which passive modifications are applied
   * @return A {@link Norm} with its passive changes applied that are in effect before the date
   */
  Norm applyPassiveModifications(Query query);

  /**
   * A record representing the query for applying the passive modifications of the norm.
   *
   * @param norm The norm which contains the passive modifications.
   * @param date The date until which the passive modifications are applied.
   */
  record Query(Norm norm, Instant date) {}
}
