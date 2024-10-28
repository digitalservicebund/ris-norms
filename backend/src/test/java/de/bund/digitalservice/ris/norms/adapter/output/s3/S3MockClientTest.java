package de.bund.digitalservice.ris.norms.adapter.output.s3;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.*;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.model.*;

class S3MockClientTest {

  private static S3MockClient s3MockClient;
  private static Path tempDirectory;

  @BeforeAll
  static void setUp() throws IOException {
    s3MockClient = new S3MockClient();
    tempDirectory = Files.createTempDirectory("s3-mock-test");
    s3MockClient.setRelativeLocalStorageDirectory(tempDirectory);
    s3MockClient.init();
  }

  @AfterEach
  void clean() throws IOException {
    try (var paths = Files.list(tempDirectory)) {
      paths.forEach(path -> {
        try {
          Files.delete(path);
        } catch (IOException e) {
          // Ignore
        }
      });
    }
  }

  @Test
  void testListObjects() {
    // Given
    final String key1 = "test-file1.txt";
    final String key2 = "test-file2.txt";

    s3MockClient.putObject(
      PutObjectRequest.builder().key(key1).build(),
      RequestBody.fromString("Content 1")
    );
    s3MockClient.putObject(
      PutObjectRequest.builder().key(key2).build(),
      RequestBody.fromString("Content 2")
    );

    // When
    final ListObjectsV2Response response = s3MockClient.listObjectsV2(
      ListObjectsV2Request.builder().build()
    );

    // Then
    assertThat(response.contents()).hasSize(2);
    assertThat(response.contents().stream().anyMatch(s3Object -> s3Object.key().equals(key1)))
      .isTrue();
    assertThat(response.contents().stream().anyMatch(s3Object -> s3Object.key().equals(key2)))
      .isTrue();
  }

  @Test
  void testGetObject() throws IOException {
    // Given
    final String key = "test-file.txt";
    final String content = "Hello, S3!";
    final RequestBody requestBody = RequestBody.fromInputStream(
      new ByteArrayInputStream(content.getBytes()),
      content.length()
    );
    final PutObjectRequest putRequest = PutObjectRequest.builder().key(key).build();
    s3MockClient.putObject(putRequest, requestBody);

    // When
    final String retrievedContent;
    try (
      InputStream inputStream = s3MockClient.getObject(
        GetObjectRequest.builder().key(key).build(),
        ResponseTransformer.toInputStream()
      )
    ) {
      retrievedContent = new String(inputStream.readAllBytes());
    }

    // Then
    assertThat(retrievedContent).isEqualTo(content);
  }

  @Test
  void testPutObject() {
    // Given
    final String key = "test-file.txt";
    final String content = "Hello, S3!";
    final RequestBody requestBody = RequestBody.fromInputStream(
      new ByteArrayInputStream(content.getBytes()),
      content.length()
    );

    // When
    final PutObjectRequest request = PutObjectRequest.builder().key(key).build();
    s3MockClient.putObject(request, requestBody);

    // Then
    assertThat(Files.exists(tempDirectory.resolve(key))).isTrue();
  }

  @Test
  void testDeleteObject() {
    // Given
    final String key = "test-file-to-delete.txt";
    final String content = "This file will be deleted.";
    final RequestBody requestBody = RequestBody.fromInputStream(
      new ByteArrayInputStream(content.getBytes()),
      content.length()
    );
    s3MockClient.putObject(PutObjectRequest.builder().key(key).build(), requestBody);

    // When
    s3MockClient.deleteObject(DeleteObjectRequest.builder().key(key).build());

    // Then
    assertThat(Files.exists(tempDirectory.resolve(key))).isFalse();
  }
}
