package de.bund.digitalservice.ris.norms.adapter.output.s3;

import de.bund.digitalservice.ris.norms.adapter.output.exception.BucketException;
import de.bund.digitalservice.ris.norms.application.port.output.DeletePrivateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.DeletePublicNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishPrivateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishPublicNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Service responsible for uploading {@link Norm} XML documents to designated private and public AWS S3 buckets.
 * This service provides methods to publish norms to both private and public storage locations by implementing
 * the {@link PublishPublicNormPort} and {@link PublishPrivateNormPort} interfaces.
 * <p>
 * Each bucket is associated with a dedicated S3 client configured through Spring and injected based on
 * specific application profiles for staging, UAT, and production environments. The service uses AWS SDK
 * to interact with the S3 service and to manage document storage, utilizing XML transformation utilities
 * for document conversion.
 * </p>
 *
 * <p>Configuration:</p>
 * <ul>
 *   <li>The bucket names are configured through application properties:
 *       <ul>
 *         <li>{@code otc.obs.private.bucket-name} for the private S3 bucket.</li>
 *         <li>{@code otc.obs.public.bucket-name} for the public S3 bucket.</li>
 *       </ul>
 *   </li>
 *   <li>S3 clients are injected with qualifiers {@code privateS3Client} and {@code publicS3Client}.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * This service is used by invoking either {@code publishPublicNorm} or {@code publishPrivateNorm}
 * to upload a norm document to the respective storage. In case of a failure during the upload,
 * a {@link BucketException} is thrown, encapsulating the bucket name and norm details.
 *
 * @see PublishPublicNormPort
 * @see PublishPrivateNormPort
 * @see BucketException
 */
@Service
@Slf4j
public class BucketService
  implements
    PublishPublicNormPort, PublishPrivateNormPort, DeletePublicNormPort, DeletePrivateNormPort {

  @Value("${otc.obs.private.bucket-name:private}")
  private String privateBucketName;

  @Value("${otc.obs.public.bucket-name:public}")
  private String publicBucketName;

  private final S3Client privateS3Client;
  private final S3Client publicS3Client;

  public BucketService(
    @Qualifier("privateS3Client") S3Client privateS3Client,
    @Qualifier("publicS3Client") S3Client publicS3Client
  ) {
    this.privateS3Client = privateS3Client;
    this.publicS3Client = publicS3Client;
  }

  @Override
  public void publishPublicNorm(PublishPublicNormPort.Command command) throws BucketException {
    uploadToBucket(publicS3Client, publicBucketName, command.norm());
    updateChangelogInS3(publicS3Client, publicBucketName, Changelog.CHANGED, command.norm());
  }

  @Override
  public void publishPrivateNorm(PublishPrivateNormPort.Command command) throws BucketException {
    uploadToBucket(privateS3Client, privateBucketName, command.norm());
    updateChangelogInS3(privateS3Client, privateBucketName, Changelog.CHANGED, command.norm());
  }

  @Override
  public void deletePrivateNorm(DeletePrivateNormPort.Command command) throws BucketException {
    deleteFromBucket(privateS3Client, privateBucketName, command.norm());
    updateChangelogInS3(privateS3Client, privateBucketName, Changelog.DELETED, command.norm());
  }

  @Override
  public void deletePublicNorm(DeletePublicNormPort.Command command) throws BucketException {
    deleteFromBucket(publicS3Client, publicBucketName, command.norm());
    updateChangelogInS3(publicS3Client, publicBucketName, Changelog.DELETED, command.norm());
  }

  private void uploadToBucket(final S3Client s3Client, final String bucketName, final Norm norm) {
    try {
      final PutObjectRequest request = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(norm.getManifestationEli().toString())
        .build();
      s3Client.putObject(request, RequestBody.fromString(XmlMapper.toString(norm.getDocument())));
    } catch (final Exception e) {
      throw new BucketException(
        BucketException.Operation.PUT,
        bucketName,
        norm.getManifestationEli().toString(),
        e
      );
    }
  }

  private void deleteFromBucket(final S3Client s3Client, final String bucketName, final Norm norm) {
    try {
      final DeleteObjectRequest request = DeleteObjectRequest
        .builder()
        .bucket(bucketName)
        .key(norm.getManifestationEli().toString())
        .build();
      s3Client.deleteObject(request);
    } catch (final Exception e) {
      throw new BucketException(
        BucketException.Operation.DELETE,
        bucketName,
        norm.getManifestationEli().toString(),
        e
      );
    }
  }

  private void updateChangelogInS3(
    final S3Client s3Client,
    final String bucketName,
    final String operation,
    final Norm norm
  ) {
    try {
      final Changelog changelog = loadChangelog(s3Client, bucketName);
      changelog.addContent(operation, norm.getManifestationEli().toString());
      final PutObjectRequest putRequest = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(changelog.getFileName())
        .build();
      s3Client.putObject(putRequest, RequestBody.fromString(changelog.getContent()));
    } catch (final Exception e) {
      log.error("Failed to update changelog in bucket %s with error %s".formatted(bucketName, e));
    }
  }

  private Changelog loadChangelog(final S3Client s3Client, final String bucketName) {
    final Changelog changelog = new Changelog();
    try {
      final GetObjectRequest getRequest = GetObjectRequest
        .builder()
        .bucket(bucketName)
        .key(changelog.getFileName())
        .build();
      final InputStream changelogStream = s3Client.getObject(getRequest);
      changelog.setContent(new String(changelogStream.readAllBytes(), StandardCharsets.UTF_8));
    } catch (final Exception e) {
      log.warn("Changelog not found or failed to load, creating an empty changelog.");
    }
    return changelog;
  }
}
