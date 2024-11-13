package de.bund.digitalservice.ris.norms.adapter.output.s3;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.bund.digitalservice.ris.norms.adapter.output.exception.BucketException;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

/**
 * Service responsible for uploading, deleting, and managing {@link Norm} XML documents in designated private and public AWS S3 buckets.
 * This service provides methods to publish norms, delete norms (single or batch) and also for publishing changelogs.
 * <p>
 * Each bucket is associated with a dedicated S3 client, configured through Spring and injected based on
 * specific application profiles for staging, UAT, and production environments. The service uses AWS SDK
 * to interact with the S3 service and to manage document storage, utilizing XML transformation utilities
 * for document conversion and changelog management.
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
 * This service is used by invoking the appropriate methods to publish or delete norm documents, or managing the changelogs
 * In case of a failure during any operation (upload, delete, etc.), a {@link BucketException} is thrown, encapsulating the bucket name
 * and norm details.
 */
@Service
@Slf4j
public class BucketService
  implements
    PublishPublicNormPort,
    PublishPrivateNormPort,
    DeletePublicNormPort,
    DeletePrivateNormPort,
    DeleteAllPublicNormsPort,
    DeleteAllPrivateNormsPort,
    PublishChangelogsPort {

  @Value("${otc.obs.private.bucket-name:private}")
  private String privateBucketName;

  @Value("${otc.obs.public.bucket-name:public}")
  private String publicBucketName;

  private final S3Client privateS3Client;
  private final S3Client publicS3Client;
  private Changelog publicChangelog = null;
  private Changelog privateChangelog = null;

  public BucketService(
    @Qualifier("privateS3Client") S3Client privateS3Client,
    @Qualifier("publicS3Client") S3Client publicS3Client
  ) {
    this.privateS3Client = privateS3Client;
    this.publicS3Client = publicS3Client;
  }

  @Override
  public void publishPublicNorm(PublishPublicNormPort.Command command) throws BucketException {
    if (publicChangelog == null) {
      publicChangelog = loadChangelog(publicS3Client, publicBucketName);
    }
    uploadToBucket(
      publicS3Client,
      publicBucketName,
      command.norm().getManifestationEli().toString(),
      XmlMapper.toString(command.norm().getDocument())
    );
    publicChangelog.addContent(Changelog.CHANGED, command.norm().getManifestationEli().toString());
  }

  @Override
  public void publishPrivateNorm(PublishPrivateNormPort.Command command) throws BucketException {
    if (privateChangelog == null) {
      privateChangelog = loadChangelog(privateS3Client, privateBucketName);
    }
    uploadToBucket(
      privateS3Client,
      privateBucketName,
      command.norm().getManifestationEli().toString(),
      XmlMapper.toString(command.norm().getDocument())
    );
    privateChangelog.addContent(Changelog.CHANGED, command.norm().getManifestationEli().toString());
  }

  @Override
  public void deletePrivateNorm(DeletePrivateNormPort.Command command) throws BucketException {
    deleteFromBucket(privateS3Client, privateBucketName, command.norm());
    privateChangelog.addContent(Changelog.DELETED, command.norm().getManifestationEli().toString());
  }

  @Override
  public void deletePublicNorm(DeletePublicNormPort.Command command) throws BucketException {
    deleteFromBucket(publicS3Client, publicBucketName, command.norm());
    publicChangelog.addContent(Changelog.DELETED, command.norm().getManifestationEli().toString());
  }

  @Override
  public void deleteAllPublicNorms() {
    deleteAllExceptChangelog(publicS3Client, publicBucketName);
  }

  @Override
  public void deleteAllPrivateNorms() {
    deleteAllExceptChangelog(privateS3Client, privateBucketName);
  }

  @Override
  public void publishChangelogs() {
    if (publicChangelog != null) {
      try {
        uploadToBucket(
          publicS3Client,
          publicBucketName,
          publicChangelog.getFileName(),
          publicChangelog.getContent()
        );
        publicChangelog = null;
        log.info("Successfully uploaded changelog to public bucket");
      } catch (final JsonProcessingException e) {
        log.error(
          "Failed to parse changelog with name %s for bucket %s with error %s".formatted(
              publicChangelog.getFileName(),
              publicBucketName,
              e
            )
        );
      }
    }
    if (privateChangelog != null) {
      try {
        uploadToBucket(
          privateS3Client,
          privateBucketName,
          privateChangelog.getFileName(),
          privateChangelog.getContent()
        );
        privateChangelog = null;
        log.info("Successfully uploaded changelog to private bucket");
      } catch (final JsonProcessingException e) {
        log.error(
          "Failed to parse changelog with name %s for bucket %s with error %s".formatted(
              privateChangelog.getFileName(),
              privateBucketName,
              e
            )
        );
      }
    }
  }

  private void uploadToBucket(
    final S3Client s3Client,
    final String bucketName,
    final String key,
    final String content
  ) {
    try {
      final PutObjectRequest request = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(key)
        .build();
      s3Client.putObject(request, RequestBody.fromString(content));
    } catch (final Exception e) {
      throw new BucketException(
        BucketException.Operation.PUT,
        bucketName,
        "Key %s could not be uploaded".formatted(key),
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
        "Key %s could not be deleted".formatted(norm.getManifestationEli().toString()),
        e
      );
    }
  }

  /**
   * Deletes all objects in the specified S3 bucket, except for those matching the changelog pattern.
   * The deletion process handles pagination automatically if there are more than 1,000 objects in the bucket.
   * <p>
   * AWS S3 allows a maximum of 1,000 keys to be processed per delete request. This method retrieves and deletes objects
   * in batches of up to 1,000 keys at a time, using pagination to handle more than 1,000 objects. If the number of objects
   * exceeds the 1,000-object limit, the method will continue deleting in subsequent requests until all objects have been deleted.
   *
   * @param s3Client the S3 client used to interact with the S3 service
   * @param bucketName the name of the S3 bucket where the objects are located
   */
  private void deleteAllExceptChangelog(final S3Client s3Client, final String bucketName) {
    try {
      ListObjectsV2Request listRequest = ListObjectsV2Request.builder().bucket(bucketName).build();
      ListObjectsV2Response listResponse;

      do {
        listResponse = s3Client.listObjectsV2(listRequest);
        final List<ObjectIdentifier> objectsToDelete = new ArrayList<>();

        for (S3Object s3Object : listResponse.contents()) {
          final String key = s3Object.key();
          if (!Changelog.FILE_NAME_PATTERN.matcher(key).matches()) {
            objectsToDelete.add(ObjectIdentifier.builder().key(key).build());
          }
        }

        if (!objectsToDelete.isEmpty()) {
          final DeleteObjectsRequest deleteRequest = DeleteObjectsRequest
            .builder()
            .bucket(bucketName)
            .delete(d -> d.objects(objectsToDelete))
            .build();
          s3Client.deleteObjects(deleteRequest);
        }

        listRequest =
        listRequest.toBuilder().continuationToken(listResponse.nextContinuationToken()).build();
      } while (listResponse.isTruncated() != null && listResponse.isTruncated());
    } catch (Exception e) {
      throw new BucketException(
        BucketException.Operation.DELETE,
        bucketName,
        "All norms could not be deleted",
        e
      );
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
      log.warn(
        "Changelog not found or failed to load with name %s in bucket %s, creating an empty changelog. Error: %s".formatted(
            changelog.getFileName(),
            bucketName,
            e
          )
      );
    }
    return changelog;
  }
}
