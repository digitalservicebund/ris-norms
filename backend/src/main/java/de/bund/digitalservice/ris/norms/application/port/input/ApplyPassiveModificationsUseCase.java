package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import java.time.Instant;
import java.util.Set;

/**
 * Interface representing the use case for applying passive changes to a {@link Regelungstext} up until a
 * given date.
 */
public interface ApplyPassiveModificationsUseCase {
  /**
   * Applies the passive modifications of the regelungstext. Only applies "aenderungsbefehl-ersetzen".
   *
   * @param query The query contains the regelungstext and date until which passive modifications are applied
   * @return A {@link Regelungstext} with its passive changes applied that are in effect before the date
   */
  Regelungstext applyPassiveModifications(Query query);

  /**
   * A record representing the query for applying the passive modifications of the regelungstext.
   *
   * @param regelungstext The regelungstext which contains the passive modifications.
   * @param date The date until which the passive modifications are applied.
   * @param customRegelungstexte A set of regelungstexte. When looking for regelungstexte these regelungstexte are used instead of the
   *     persisted once.
   */
  record Query(Regelungstext regelungstext, Instant date, Set<Regelungstext> customRegelungstexte) {
    public Query(Regelungstext regelungstext, Instant date) {
      this(regelungstext, date, Set.of());
    }
  }
}
