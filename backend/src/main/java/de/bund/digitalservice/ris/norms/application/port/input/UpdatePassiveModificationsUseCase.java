package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for updating the akn:passiveModifications of a {@link Norm}
 * by another {@link Norm}'s akn:activeModifications.
 */
public interface UpdatePassiveModificationsUseCase {

  /**
   * Update the passive modifications of the zf0Norm.
   *
   * @param query The query contains the zf0Norm and the zf0Norm whose active modifications should
   *     be used to update the passive modifications.
   * @return A {@link Norm} with the update passive modifications
   */
  Norm updatePassiveModifications(Query query);

  /**
   * A record representing the query for updating the passive modifications of a zf0Norm.
   *
   * @param zf0Norm The zf0Norm to update.
   * @param amendingNorm The zf0Norm containing activeModifications, that should be used to update
   *     the zf0Norm.
   * @param targetNormEli the eli of the target norm to filter the active mods out of the amending
   *     law.
   */
  record Query(Norm zf0Norm, Norm amendingNorm, String targetNormEli) {}
}
