package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for updating the akn:activeModifications of a {@link Norm}.
 */
public interface UpdateActiveModificationsUseCase {

  /**
   * Update the active modifications of the amendingNorm.
   *
   * @param query The query contains the amendingNorm whose active modifications should be updated.
   * @return A {@link Norm} with the updated active modifications
   */
  Norm updateActiveModifications(Query query);

  /**
   * A record representing the query for updating the active modifications of an amendingNorm.
   *
   * @param amendingNorm The amendingNorm containing activeModifications, that should be updated
   * @param eId the eId of the akn:mod within the amending norm
   * @param destinationHref - the ELI + eid + character range of the target norm
   * @param timeBoundaryEid - the eId of the temporal group of the time boundary
   * @param newText - the new text to replace the old one
   */
  record Query(
      Norm amendingNorm,
      String eId,
      String destinationHref,
      String timeBoundaryEid,
      String newText) {}
}
