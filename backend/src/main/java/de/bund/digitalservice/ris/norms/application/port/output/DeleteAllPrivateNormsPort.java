package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the output port for deleting multiple {@link Norm} entities from a storage location designated
 * for private data.
 */
public interface DeleteAllPrivateNormsPort {
  /**
   * Deletes the specified {@link Norm} entities from a designated private storage location.
   *
   */
  void deleteAllPrivateNorms();
}
