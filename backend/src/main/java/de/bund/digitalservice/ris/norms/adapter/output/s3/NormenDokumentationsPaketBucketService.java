package de.bund.digitalservice.ris.norms.adapter.output.s3;

import de.bund.digitalservice.ris.norms.adapter.output.exception.BucketException;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Service responsible for uploading Normendokumentationspakete (zip and sig) files in designated AWS S3 buckets.
 *
 */
@Slf4j
@Service
public class NormenDokumentationsPaketBucketService implements SaveNormendokumentationspaketPort {

  private final String bucketName;
  private final S3Client s3Client;

  public NormenDokumentationsPaketBucketService(
    @Qualifier("eVerkuendungS3Client") S3Client s3Client,
    @Value("${otc.obs.everkuendung.bucket-name}") String bucketName
  ) {
    this.s3Client = s3Client;
    this.bucketName = bucketName;
  }

  @Override
  public void saveNormendokumentationspaket(Command command) throws IOException {
    uploadToBucket(
      command.processId() + "/" + command.file().getFilename(),
      RequestBody.fromBytes(command.file().getContentAsByteArray())
    );

    uploadToBucket(
      command.processId() + "/" + command.signature().getFilename(),
      RequestBody.fromBytes(command.signature().getContentAsByteArray())
    );
  }

  private void uploadToBucket(final String key, final RequestBody requestBody) {
    try {
      final PutObjectRequest request = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(key)
        .build();
      s3Client.putObject(request, requestBody);
    } catch (final Exception e) {
      throw new BucketException(
        BucketException.Operation.PUT,
        bucketName,
        "Key %s could not be uploaded".formatted(key),
        e
      );
    }
  }
}
