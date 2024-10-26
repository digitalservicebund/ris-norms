package de.bund.digitalservice.ris.norms.adapter.output.s3;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
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
  public PutObjectResponse putObject(PutObjectRequest putObjectRequest, RequestBody requestBody) {
    final Path filePath = localStorageDirectory.resolve(putObjectRequest.key());
    try (InputStream inputStream = requestBody.contentStreamProvider().newStream()) {
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
      log.info("File stored at: {}", filePath);
    } catch (IOException e) {
      log.error("Failed to store file: {}", filePath, e);
    }
    return PutObjectResponse.builder().build();
  }

  @Override
  public ListObjectsV2Response listObjectsV2(ListObjectsV2Request listObjectsV2Request) {
    try (Stream<Path> files = Files.list(localStorageDirectory)) {
      final List<S3Object> s3Objects = files
        .map(Path::getFileName)
        .map(Path::toString)
        .map(name -> S3Object.builder().key(name).build())
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
    final Path filePath = localStorageDirectory.resolve(getObjectRequest.key());
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
  public DeleteObjectResponse deleteObject(DeleteObjectRequest deleteObjectRequest) {
    final Path filePath = localStorageDirectory.resolve(deleteObjectRequest.key());
    try {
      Files.deleteIfExists(filePath);
      log.info("File deleted: {}", filePath);
    } catch (IOException e) {
      log.error("Failed to delete file: {}", filePath, e);
    }
    return DeleteObjectResponse.builder().build();
  }
}
