package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for loading a ZF0 version of a target law in the form of a
 * {@link Norm}. If not present, it will be created anew but only in memory. Therefore, if needed it
 * should be persisted separately. TEST
 */
public interface LoadZf0UseCase {
  /**
   * It loads or creates the ZF0 norm for a given target law and amending law.
   *
   * @param query The query containing both the amending law and the target law.
   * @return A {@link Norm} of the created ZF0 version.
   */
  Norm loadZf0(Query query);

  /**
   * A record representing the query for loading/creating the ZF0.
   *
   * @param amendingLaw The {@link Norm} of an amending law.
   * @param targetLaw The {@link Norm} of the target law.
   */
  record Query(Norm amendingLaw, Norm targetLaw) {}
}
