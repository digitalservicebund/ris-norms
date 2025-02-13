package de.bund.digitalservice.ris.norms.adapter.output.s3;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.http.AbortableInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

/**
 * A mock S3Client implementation for testing purposes, storing files locally.
 */
@Slf4j
public class S3MockClient implements S3Client {

  @Setter
  @Value("${local.file-storage}")
  private Path relativeLocalStorageDirectory;

  private Path localStorageDirectory;

  @Override
  public String serviceName() {
    return "s3-mock";
  }

  @Override
  public void close() {
    // No-op for mock client
  }

  /**
   * Initializes the local storage directory for the mock S3 client.
   * This method is invoked after the bean's properties have been set,
   * ensuring that the specified local file storage directory exists.
   * If the directory does not exist, it will be created. Any IO exceptions
   * encountered during the creation of the directory will be logged.
   */
  @PostConstruct
  public void init() {
    this.localStorageDirectory = relativeLocalStorageDirectory.toAbsolutePath();
    try {
      Files.createDirectories(localStorageDirectory);
    } catch (IOException e) {
      log.error("Could not create local storage directory", e);
    }
  }

  @Override
  public ListObjectsV2Response listObjectsV2(ListObjectsV2Request listObjectsV2Request) {
    final Path basePath = listObjectsV2Request.bucket() != null
      ? localStorageDirectory.resolve(listObjectsV2Request.bucket())
      : localStorageDirectory;

    try (Stream<Path> files = Files.walk(basePath)) {
      final List<S3Object> s3Objects = files
        .filter(Files::isRegularFile)
        .map(path -> {
          String key;
          // If the file is under the 'eli' folder, return full path starting with 'eli'
          if (path.toString().contains("/eli/")) {
            key = basePath.relativize(path).toString();
          } else {
            // If the file is not under 'eli', return only the file name
            key = path.getFileName().toString();
          }
          return S3Object.builder().key(key).build();
        })
        .toList();

      return ListObjectsV2Response.builder().contents(s3Objects).build();
    } catch (IOException e) {
      log.error("Failed to list objects", e);
      return ListObjectsV2Response.builder().contents(List.of()).build();
    }
  }

  @Override
  public <T> T getObject(
    GetObjectRequest getObjectRequest,
    ResponseTransformer<GetObjectResponse, T> responseTransformer
  ) {
    final Path filePath = getObjectRequest.bucket() != null
      ? localStorageDirectory.resolve(getObjectRequest.bucket()).resolve(getObjectRequest.key())
      : localStorageDirectory.resolve(getObjectRequest.key());
    try {
      byte[] fileBytes = Files.readAllBytes(filePath);
      final GetObjectResponse getObjectResponse = GetObjectResponse
        .builder()
        .contentLength((long) fileBytes.length)
        .build();
      return responseTransformer.transform(
        getObjectResponse,
        AbortableInputStream.create(new ByteArrayInputStream(fileBytes))
      );
    } catch (final Exception e) {
      log.error("Failed to retrieve object: {}", filePath, e);
    }
    return null;
  }

  @Override
  public PutObjectResponse putObject(PutObjectRequest putObjectRequest, RequestBody requestBody) {
    final Path filePath = putObjectRequest.bucket() != null
      ? localStorageDirectory.resolve(putObjectRequest.bucket()).resolve(putObjectRequest.key())
      : localStorageDirectory.resolve(putObjectRequest.key());
    try {
      Files.createDirectories(filePath.getParent());
      try (InputStream inputStream = requestBody.contentStreamProvider().newStream()) {
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        log.info("File stored at: {}", filePath);
      }
    } catch (IOException e) {
      log.error("Failed to store file: {}", filePath, e);
    }
    return PutObjectResponse.builder().build();
  }

  @Override
  public DeleteObjectResponse deleteObject(DeleteObjectRequest deleteObjectRequest) {
    // Resolve the full path to the file, including bucket if present
    final Path filePath = deleteObjectRequest.bucket() != null
      ? localStorageDirectory
        .resolve(deleteObjectRequest.bucket())
        .resolve(deleteObjectRequest.key())
      : localStorageDirectory.resolve(deleteObjectRequest.key());

    try {
      // Delete the specified file
      Files.deleteIfExists(filePath);
      log.info("File deleted: {}", filePath);

      // Traverse up from the file, deleting empty parent directories
      final Path stopAtDirectory = deleteObjectRequest.bucket() != null
        ? localStorageDirectory.resolve(deleteObjectRequest.bucket())
        : localStorageDirectory;
      Path parent = filePath.getParent();
      while (parent != null && !parent.equals(stopAtDirectory)) {
        if (FileUtils.isEmptyDirectory(parent.toFile())) {
          Files.delete(parent);
          parent = parent.getParent();
        } else {
          break; // Directory is not empty, so stop the loop
        }
      }
    } catch (IOException e) {
      log.error("Failed to delete file or directory structure starting from: {}", filePath, e);
    }

    return DeleteObjectResponse.builder().build();
  }

  @Override
  public DeleteObjectsResponse deleteObjects(DeleteObjectsRequest deleteObjectsRequest) {
    final List<DeletedObject> deletedObjects = deleteObjectsRequest
      .delete()
      .objects()
      .stream()
      .map(objIdentifier -> {
        Path filePath = deleteObjectsRequest.bucket() != null
          ? localStorageDirectory
            .resolve(deleteObjectsRequest.bucket())
            .resolve(objIdentifier.key())
          : localStorageDirectory.resolve(objIdentifier.key());

        try {
          // Delete the specified file
          Files.deleteIfExists(filePath);
          log.info("File deleted: {}", filePath);

          // Traverse up from the file, deleting empty parent directories
          final Path stopAtDirectory = deleteObjectsRequest.bucket() != null
            ? localStorageDirectory.resolve(deleteObjectsRequest.bucket())
            : localStorageDirectory;
          Path parent = filePath.getParent();
          while (parent != null && !parent.equals(stopAtDirectory)) {
            if (FileUtils.isEmptyDirectory(parent.toFile())) {
              Files.delete(parent);
              log.info("Deleted empty directory: {}", parent);
              parent = parent.getParent();
            } else {
              break; // Directory is not empty, so stop the loop
            }
          }

          return DeletedObject.builder().key(objIdentifier.key()).build();
        } catch (IOException e) {
          log.error("Failed to delete file: {}", filePath, e);
          return null; // Return null on error, will be filtered out in the next step
        }
      })
      .filter(Objects::nonNull) // Filter out null entries (failed deletions)
      .toList();

    // Return a DeleteObjectsResponse including the successfully deleted objects
    return DeleteObjectsResponse.builder().deleted(deletedObjects).build();
  }
}
