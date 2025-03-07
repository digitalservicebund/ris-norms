package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Use case for publishing {@link Norm}s to the portal prototype.
 */
public interface PublishNormsToPortalPrototypeUseCase {
  /**
   * Publish all published {@link Norm}s to the portal prototype.
   *
   */
  void publishNormsToPortalPrototype();
}
