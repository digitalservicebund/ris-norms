package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for updating the akn:passiveModifications of a {@link Norm}
 * by another {@link Norm}'s akn:activeModifications.
 */
public interface UpdatePassiveModificationsUseCase {

  /**
   * Update the passive modifications of the norm.
   *
   * @param query The query contains the norm and the norm whose active modifications should be used
   *     to update the passive modifications.
   * @return A {@link Norm} with the update passive modifications
   */
  Norm updatePassiveModifications(Query query);

  /**
   * A record representing the query for updating the passive modifications of a norm.
   *
   * @param norm The norm to update.
   * @param amendingNorm The norm containing activeModifications, that should be used to update the
   *     norm.
   */
  record Query(Norm norm, Norm amendingNorm) {}
}
