package de.bund.digitalservice.ris.norms.utils;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

/**
 * A mock implementation of the S3Client for testing purposes.
 * This class simulates interactions with an S3-like storage
 * by storing files locally in a specified directory.
 */
public class S3MockClient implements S3Client {

  private static final Logger LOGGER = LoggerFactory.getLogger(S3MockClient.class);

  @Value("${local.file-storage}")
  private Path relativeLocalStorageDirectory;

  private Path localStorageDirectory;

  @Override
  public String serviceName() {
    return null;
  }

  @Override
  public void close() {
    /* this method is empty because of mock */
  }

  /**
   * Init method
   */
  @PostConstruct
  public void init() {
    this.localStorageDirectory = relativeLocalStorageDirectory.toAbsolutePath();
    this.localStorageDirectory.toFile().mkdirs();
  }

  /**
   * Puts an object in the mock storage by writing it to a local file.
   *
   * @param putObjectRequest the request containing the object metadata
   * @param requestBody the content of the object to be stored
   * @return the response of the put operation
   */
  @Override
  public PutObjectResponse putObject(PutObjectRequest putObjectRequest, RequestBody requestBody) {
    String fileName = putObjectRequest.key();

    try (
      FileOutputStream fos = new FileOutputStream(
        localStorageDirectory.resolve(fileName).toFile(),
        false
      );
      InputStream inputStream = requestBody.contentStreamProvider().newStream()
    ) {
      byte[] content = new byte[1024];
      int len = -1;
      while ((len = inputStream.read(content)) != -1) {
        fos.write(content, 0, len);
      }
    } catch (IOException ex) {
      LOGGER.info("Couldn't write file: {}", fileName, ex);
    }

    return PutObjectResponse.builder().build();
  }

  /**
   * Lists the objects stored in the mock storage.
   *
   * @param listObjectsV2Request the request to list objects
   * @return a response containing the list of objects
   */
  @Override
  public ListObjectsV2Response listObjectsV2(ListObjectsV2Request listObjectsV2Request) {
    String[] nameList = null;
    File localFileStorage = localStorageDirectory.toFile();
    if (localFileStorage.isDirectory()) {
      nameList = localFileStorage.list();
    }

    List<S3Object> objectList = Collections.emptyList();
    if (nameList != null) {
      objectList =
      Arrays.stream(nameList).map(name -> S3Object.builder().key(name).build()).toList();
    }

    return ListObjectsV2Response.builder().contents(objectList).build();
  }

  /**
   * Retrieves an object from the mock storage.
   *
   * @param getObjectRequest the request for the object to retrieve
   * @param responseTransformer the transformer for the response
   * @param <T> the type of the response
   * @return the object retrieved from the storage
   */
  @Override
  public <T> T getObject(
    GetObjectRequest getObjectRequest,
    ResponseTransformer<GetObjectResponse, T> responseTransformer
  ) {
    byte[] bytes = new byte[] {};

    String fileName = getObjectRequest.key();
    File file = localStorageDirectory.resolve(fileName).toFile();
    try (FileInputStream fl = new FileInputStream(file)) {
      bytes = new byte[(int) file.length()];
      int readBytes = fl.read(bytes);
      if (readBytes != file.length()) {
        LOGGER.warn("different size between file length and read bytes");
      }
    } catch (IOException ex) {
      LOGGER.error("Couldn't get object from local storage.");
    }

    return (T) ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), bytes);
  }

  /**
   * Deletes an object from the mock storage.
   *
   * @param deleteObjectRequest the request containing the object to delete
   * @return the response of the delete operation
   */
  @Override
  public DeleteObjectResponse deleteObject(DeleteObjectRequest deleteObjectRequest) {
    String fileName = deleteObjectRequest.key();
    File file = localStorageDirectory.resolve(fileName).toFile();
    if (file.exists()) {
      try {
        Files.delete(file.toPath());
      } catch (IOException ex) {
        LOGGER.error("Couldn't delete file", ex);
      }
    }

    return DeleteObjectResponse.builder().build();
  }
}
