package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import java.time.LocalDate;

/**
 * Use case for updating metadata for a single element within the {@link Proprietary} node of a
 * {@link Norm}.
 */
public interface UpdateProprietarySingleElementFromNormUseCase {
  /**
   * Updates specific metadata for a single element from a {@link Norm}.
   *
   * @param query specifying the eli of ZF0, the eid of a single element, the date for the metadata
   *     as well as the metadata themselves.
   * @return Proprietary node of the norm with the updated metadata.
   */
  Proprietary updateProprietarySingleElementFromNorm(Query query);

  /**
   * Contains the parameters needed for loading proprietary metadata from a norm.
   *
   * @param eli The ELI used to identify the norm.
   * @param eid the eId of the single element within the ZF0
   * @param atDate the date at which the metadata are being updated.
   * @param metadata object containing the metadata to update
   */
  record Query(String eli, String eid, LocalDate atDate, Metadata metadata) {}

  /**
   * Record representing the list of metadata to update.
   *
   * @param artDerNorm - "Art der Norm" SN or ÄN or ÜN
   */
  record Metadata(String artDerNorm) {}
}
