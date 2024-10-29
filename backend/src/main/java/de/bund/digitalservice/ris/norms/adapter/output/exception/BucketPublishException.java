package de.bund.digitalservice.ris.norms.adapter.output.exception;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.PublishException;
import lombok.Getter;

/**
 * Exception thrown when a {@link Norm} entity fails to upload to the specified S3 bucket.
 * This exception captures and reports details about the bucket and the specific norm
 * manifestation that could not be published.
 */
@Getter
public class BucketPublishException extends PublishException {

  private final String bucketName;
  private final transient ManifestationEli manifestationEli;

  public BucketPublishException(
    final String bucketName,
    final ManifestationEli manifestationEli,
    final Exception e
  ) {
    super("Norm %s could not be uploaded to bucket %s".formatted(manifestationEli, bucketName), e);
    this.bucketName = bucketName;
    this.manifestationEli = manifestationEli;
  }
}
