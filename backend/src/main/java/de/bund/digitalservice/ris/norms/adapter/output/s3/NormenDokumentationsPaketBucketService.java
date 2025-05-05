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
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Service responsible for uploading Normendokumentationspakete (zip and sig) files in designated AWS S3 buckets.
 *
 */
@Slf4j
@Service
public class NormenDokumentationsPaketBucketService
  implements SaveNormendokumentationspaketPort, LoadNormendokumentationspaketPort {

  private final String bucketName;
  private final S3Client s3Client;

  public NormenDokumentationsPaketBucketService(
    @Qualifier("eVerkuendungS3Client") S3Client s3Client,
    @Value("${otc.obs.everkuendung.bucket-name}") String bucketName
  ) {
    this.s3Client = s3Client;
    this.bucketName = bucketName;
  }

  private static final String ZIP_FILE_NAME = "file.zip";
  private static final String SIGNATURE_FILE_NAME = "signature.sig";

  @Override
  public void saveNormendokumentationspaket(SaveNormendokumentationspaketPort.Command command)
    throws IOException {
    uploadToBucket(
      command.processId() + "/" + ZIP_FILE_NAME,
      RequestBody.fromInputStream(command.file().getInputStream(), command.file().contentLength())
    );

    uploadToBucket(
      command.processId() + "/" + SIGNATURE_FILE_NAME,
      RequestBody.fromInputStream(
        command.signature().getInputStream(),
        command.signature().contentLength()
      )
    );
  }

  @Override
  public Result loadNormendokumentationspaket(LoadNormendokumentationspaketPort.Command command) {
    byte[] file = loadFromBucket(command.processId() + "/" + ZIP_FILE_NAME);
    byte[] signature = loadFromBucket(command.processId() + "/" + SIGNATURE_FILE_NAME);
    return new Result(file, signature);
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

  private byte[] loadFromBucket(final String key) {
    try {
      final GetObjectRequest request = GetObjectRequest
        .builder()
        .bucket(bucketName)
        .key(key)
        .build();
      return s3Client.getObjectAsBytes(request).asByteArray();
    } catch (final Exception e) {
      throw new BucketException(
        BucketException.Operation.GET,
        bucketName,
        "Key %s could not be loaded".formatted(key),
        e
      );
    }
  }
}
