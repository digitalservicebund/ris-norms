package de.bund.digitalservice.ris.norms.adapter.output.exception;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.StorageException;
import lombok.Getter;

/**
 * Exception thrown when a {@link Norm} entity fails to upload to the specified S3 bucket.
 * This exception captures and reports details about the bucket and the specific norm
 * manifestation that could not be published.
 */
@Getter
public class BucketException extends StorageException {

  public BucketException(
    final String bucketName,
    final String operation,
    final ManifestationEli manifestationEli,
    final Exception e
  ) {
    super(
      "%s operation unsuccessful for bucket %s and norm %s".formatted(
          operation,
          manifestationEli,
          bucketName
        ),
      e
    );
  }
}
