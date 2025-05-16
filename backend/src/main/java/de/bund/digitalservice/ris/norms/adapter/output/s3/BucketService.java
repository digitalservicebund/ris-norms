package de.bund.digitalservice.ris.norms.adapter.output.s3;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.bund.digitalservice.ris.norms.adapter.output.exception.BucketException;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteAllPublishedDokumentePort;
import de.bund.digitalservice.ris.norms.application.port.output.DeletePublishedNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishChangelogPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

/**
 * Service responsible for uploading, deleting, and managing {@link Norm} XML documents in designated AWS S3 buckets.
 * This service provides methods to publish norms, delete norms (single or batch) and also for publishing changelogs.
 * <p>
 * The service uses AWS SDK to interact with the S3 service and to manage document storage, utilizing XML transformation
 * utilities for document conversion and changelog management.
 *
 * <p>Usage:</p>
 * This service is used by invoking the appropriate methods to publish or delete norm documents, or managing the changelogs
 * In case of a failure during any operation (upload, delete, etc.), a {@link BucketException} is thrown, encapsulating the bucket name
 * and norm details.
 */
@Slf4j
public class BucketService
  implements
    PublishNormPort,
    DeletePublishedNormPort,
    DeleteAllPublishedDokumentePort,
    PublishChangelogPort {

  private final String bucketName;
  private final S3Client s3Client;
  private Changelog changelog = null;

  public BucketService(S3Client s3Client, String bucketName) {
    this.s3Client = s3Client;
    this.bucketName = bucketName;
  }

  @Override
  public void publishNorm(PublishNormPort.Command command) throws BucketException {
    if (changelog == null) {
      changelog = loadChangelog(s3Client, bucketName);
    }

    uploadNormToBucket(changelog, s3Client, bucketName, command.norm());
  }

  @Override
  public void deletePublishedNorm(DeletePublishedNormPort.Command command) throws BucketException {
    deleteFromBucket(changelog, s3Client, bucketName, command.norm());
  }

  @Override
  public void deleteAllPublishedDokumente(DeleteAllPublishedDokumentePort.Command command) {
    deleteAllDokumenteLastModifiedBefore(
      s3Client,
      bucketName,
      changelog,
      command.lastChangeBefore()
    );
  }

  @Override
  public void publishChangelogs(PublishChangelogPort.Command command) {
    if (changelog != null) {
      try {
        uploadToBucket(
          s3Client,
          bucketName,
          changelog.getFileName(),
          changelog.getContent(command.allChanged())
        );
        changelog = null;
        log.info("Successfully uploaded changelog to bucket %s.".formatted(bucketName));
      } catch (final JsonProcessingException e) {
        log.error(
          "Failed to parse changelog with name %s for bucket %s with error %s".formatted(
              changelog.getFileName(),
              bucketName,
              e
            )
        );
      }
    }
  }

  private void uploadNormToBucket(
    final Changelog changelog,
    final S3Client s3Client,
    final String bucketName,
    final Norm norm
  ) {
    norm
      .getDokumente()
      .forEach(dokument -> {
        uploadToBucket(
          s3Client,
          bucketName,
          dokument.getManifestationEli().toString(),
          XmlMapper.toString(dokument.getDocument())
        );
        changelog.addContent(Changelog.CHANGED, dokument.getManifestationEli().toString());
      });
    norm
      .getBinaryFiles()
      .forEach(binaryFile -> {
        uploadToBucket(
          s3Client,
          bucketName,
          binaryFile.getDokumentManifestationEli().toString(),
          binaryFile.getContent()
        );
        changelog.addContent(
          Changelog.CHANGED,
          binaryFile.getDokumentManifestationEli().toString()
        );
      });
  }

  private void uploadToBucket(
    final S3Client s3Client,
    final String bucketName,
    final String key,
    final String content
  ) {
    uploadToBucket(s3Client, bucketName, key, RequestBody.fromString(content));
  }

  private void uploadToBucket(
    final S3Client s3Client,
    final String bucketName,
    final String key,
    final byte[] content
  ) {
    uploadToBucket(s3Client, bucketName, key, RequestBody.fromBytes(content));
  }

  private void uploadToBucket(
    final S3Client s3Client,
    final String bucketName,
    final String key,
    final RequestBody requestBody
  ) {
    try {
      final PutObjectRequest request = PutObjectRequest.builder()
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

  private void deleteFromBucket(
    final Changelog changelog,
    final S3Client s3Client,
    final String bucketName,
    final Norm norm
  ) {
    norm
      .getDokumente()
      .forEach(dokument ->
        deleteFromBucket(changelog, s3Client, bucketName, dokument.getManifestationEli())
      );
    norm
      .getBinaryFiles()
      .forEach(binaryFile ->
        deleteFromBucket(changelog, s3Client, bucketName, binaryFile.getDokumentManifestationEli())
      );
  }

  private void deleteFromBucket(
    final Changelog changelog,
    final S3Client s3Client,
    final String bucketName,
    final DokumentManifestationEli eli
  ) {
    try {
      final DeleteObjectRequest request = DeleteObjectRequest.builder()
        .bucket(bucketName)
        .key(eli.toString())
        .build();
      s3Client.deleteObject(request);
    } catch (final Exception e) {
      throw new BucketException(
        BucketException.Operation.DELETE,
        bucketName,
        "Key %s could not be deleted".formatted(eli.toString()),
        e
      );
    }

    changelog.addContent(Changelog.DELETED, eli.toString());
  }

  /**
   * Deletes all Dokumente in the specified S3 bucket, (not the changelog files) which have not been changed since the
   * given date.
   * The deletion process handles pagination automatically if there are more than 1,000 objects in the bucket.
   * <p>
   * AWS S3 allows a maximum of 1,000 keys to be processed per delete request. This method retrieves and deletes objects
   * in batches of up to 1,000 keys at a time, using pagination to handle more than 1,000 objects. If the number of objects
   * exceeds the 1,000-object limit, the method will continue deleting in subsequent requests until all objects have been deleted.
   *
   * @param s3Client the S3 client used to interact with the S3 service
   * @param bucketName the name of the S3 bucket where the objects are located
   * @param lastChangeBefore Dokumente that have been changed since this date are ignored
   */
  private void deleteAllDokumenteLastModifiedBefore(
    final S3Client s3Client,
    final String bucketName,
    Changelog changelog,
    Instant lastChangeBefore
  ) {
    try {
      ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
        .bucket(bucketName)
        .prefix("eli")
        .build();
      ListObjectsV2Response listResponse;
      int objectsDeleted = 0;
      do {
        listResponse = s3Client.listObjectsV2(listRequest);
        final List<ObjectIdentifier> objectsToDelete = new ArrayList<>();

        for (S3Object s3Object : listResponse.contents()) {
          final String key = s3Object.key();
          if (
            !key.startsWith(Changelog.FOLDER + "/") &&
            s3Object.lastModified().isBefore(lastChangeBefore)
          ) {
            objectsToDelete.add(ObjectIdentifier.builder().key(key).build());
          }
        }
        if (!objectsToDelete.isEmpty()) {
          final DeleteObjectsRequest deleteRequest = DeleteObjectsRequest.builder()
            .bucket(bucketName)
            .delete(d -> d.objects(objectsToDelete))
            .build();
          s3Client.deleteObjects(deleteRequest);
          objectsDeleted += objectsToDelete.size();
          objectsToDelete.forEach(objectIdentifier ->
            changelog.addContent(Changelog.DELETED, objectIdentifier.key())
          );
        }

        listRequest = listRequest
          .toBuilder()
          .continuationToken(listResponse.nextContinuationToken())
          .build();
      } while (listResponse.isTruncated() != null && listResponse.isTruncated());
      log.info("Successfully deleted {} objects in bucket {}", objectsDeleted, bucketName);
    } catch (Exception e) {
      throw new BucketException(
        BucketException.Operation.DELETE,
        bucketName,
        "All dokumente could not be deleted",
        e
      );
    }
  }

  private Changelog loadChangelog(final S3Client s3Client, final String bucketName) {
    final Changelog newChangelog = new Changelog();
    try {
      final GetObjectRequest getRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key(newChangelog.getFileName())
        .build();
      final InputStream changelogStream = s3Client.getObject(getRequest);
      newChangelog.setContent(new String(changelogStream.readAllBytes(), StandardCharsets.UTF_8));
    } catch (final Exception e) {
      log.warn(
        "Changelog not found or failed to load with name %s in bucket %s, creating an empty changelog. Error: %s".formatted(
            newChangelog.getFileName(),
            bucketName,
            e
          )
      );
    }
    return newChangelog;
  }
}
