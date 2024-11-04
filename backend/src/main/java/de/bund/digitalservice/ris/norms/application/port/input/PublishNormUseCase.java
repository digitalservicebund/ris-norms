package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for publishing {@link Norm}s to the S3 buckets that have a state of QUEUED.
 */
public interface PublishNormUseCase {
  /**
   * Publish all {@link Norm}s to the S3 buckets.
   *
   */
  void processQueuedFilesForPublish();
}
