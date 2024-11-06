package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the output port for deleting all {@link Norm} entities from a storage location designated
 * for public data.
 */
public interface DeleteAllPublicNormsPort {
  /**
   * Deletes all {@link Norm} entities from a designated public storage location.
   */
  void deleteAllPublicNorms();
}
