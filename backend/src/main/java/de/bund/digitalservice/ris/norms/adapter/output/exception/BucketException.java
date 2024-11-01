package de.bund.digitalservice.ris.norms.adapter.output.exception;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.exceptions.StorageException;
import lombok.Getter;

/**
 * Exception thrown when a {@link Norm} entity fails to upload to the specified S3 bucket.
 * This exception captures and reports details about the bucket and the specific norm
 * manifestation that could not be published.
 */
@Getter
public class BucketException extends StorageException {

  /**
   * Enum for the type of operation when communicating with the buckets.
   */
  public enum Operation {
    PUT,
    DELETE,
  }

  public BucketException(
    final Operation operation,
    final String bucketName,
    final String key,
    final Exception e
  ) {
    super(
      "%s operation unsuccessful for bucket %s and key %s".formatted(
          operation.name(),
          bucketName,
          key
        ),
      e
    );
  }
}
