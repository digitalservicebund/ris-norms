package de.bund.digitalservice.ris.norms.adapter.output.s3;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
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
        } catch (IOException e) {}
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
    assertThat(
      response.contents().stream().anyMatch(s3Object -> s3Object.key().equals(key1))
    ).isTrue();
    assertThat(
      response.contents().stream().anyMatch(s3Object -> s3Object.key().equals(key2))
    ).isTrue();
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

  @Test
  void testDeleteObjects() {
    // Given

    // Mock some object keys (some matching the changelog pattern and some not)
    final String changelogKey = "changelog-2024.json";
    final String normalKey1 = "norm1.txt";
    final String normalKey2 = "norm2.txt";

    // Upload mock objects to the bucket
    s3MockClient.putObject(
      PutObjectRequest.builder().key(changelogKey).build(),
      RequestBody.fromString("Changelog content")
    );
    s3MockClient.putObject(
      PutObjectRequest.builder().key(normalKey1).build(),
      RequestBody.fromString("Norm 1 content")
    );
    s3MockClient.putObject(
      PutObjectRequest.builder().key(normalKey2).build(),
      RequestBody.fromString("Norm 2 content")
    );

    // When
    // Prepare delete request for the normal files (excluding changelog)
    final List<ObjectIdentifier> objectsToDelete = Arrays.asList(
      ObjectIdentifier.builder().key(normalKey1).build(),
      ObjectIdentifier.builder().key(normalKey2).build()
    );

    final DeleteObjectsRequest deleteRequest = DeleteObjectsRequest.builder()
      .delete(d -> d.objects(objectsToDelete))
      .build();

    // Call the deleteObjects method
    final DeleteObjectsResponse deleteResponse = s3MockClient.deleteObjects(deleteRequest);

    // Then
    // Check that the deleted objects response contains the expected deleted objects
    assertThat(deleteResponse.deleted()).hasSize(2);
    assertThat(
      deleteResponse
        .deleted()
        .stream()
        .anyMatch(deletedObject -> deletedObject.key().equals(normalKey1))
    ).isTrue();
    assertThat(
      deleteResponse
        .deleted()
        .stream()
        .anyMatch(deletedObject -> deletedObject.key().equals(normalKey2))
    ).isTrue();

    // Check that the deleted files are actually removed from the local storage (mocked S3)
    assertThat(Files.exists(tempDirectory.resolve(normalKey1))).isFalse();
    assertThat(Files.exists(tempDirectory.resolve(normalKey2))).isFalse();

    // Check that changelog file still exists in the mock storage
    assertThat(Files.exists(tempDirectory.resolve(changelogKey))).isTrue();
  }
}
