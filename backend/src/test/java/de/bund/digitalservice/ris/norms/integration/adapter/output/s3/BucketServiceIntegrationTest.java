package de.bund.digitalservice.ris.norms.integration.adapter.output.s3;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bund.digitalservice.ris.norms.adapter.output.s3.BucketService;
import de.bund.digitalservice.ris.norms.adapter.output.s3.Changelog;
import de.bund.digitalservice.ris.norms.application.port.output.DeletePrivateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.DeletePublicNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishPrivateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishPublicNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BucketServiceIntegrationTest extends BaseS3MockIntegrationTest {

  private static final String CHANGED = "changed";
  private static final String DELETED = "deleted";

  @Autowired
  private BucketService bucketService;

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  void itPublishesNormToPublicBucket() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

    // When
    final PublishPublicNormPort.Command command = new PublishPublicNormPort.Command(norm);
    bucketService.publishPublicNorm(command);
    bucketService.publishChangelogs();

    // Then
    final Path filePath = getPublicPath(norm);
    assertThat(Files.exists(filePath)).isTrue();
    assertChangelogContains(true, PUBLIC_BUCKET, CHANGED, norm);
  }

  @Test
  void itPublishesNormToPrivateBucket() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

    // When
    final PublishPrivateNormPort.Command command = new PublishPrivateNormPort.Command(norm);
    bucketService.publishPrivateNorm(command);
    bucketService.publishChangelogs();

    // Then
    final Path filePath = getPrivatePath(norm);
    assertThat(Files.exists(filePath)).isTrue();
    assertChangelogContains(true, PRIVATE_BUCKET, CHANGED, norm);
  }

  @Test
  void itPublishesNormToPublicAndPrivateBucket() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

    // When
    final PublishPublicNormPort.Command commandPublic = new PublishPublicNormPort.Command(norm);
    bucketService.publishPublicNorm(commandPublic);
    final PublishPrivateNormPort.Command commandPrivate = new PublishPrivateNormPort.Command(norm);
    bucketService.publishPrivateNorm(commandPrivate);
    bucketService.publishChangelogs();

    // Then
    final Path publicFilePath = getPublicPath(norm);
    assertThat(Files.exists(publicFilePath)).isTrue();
    assertChangelogContains(true, PUBLIC_BUCKET, CHANGED, norm);
    final Path privateFilePath = getPrivatePath(norm);
    assertThat(Files.exists(privateFilePath)).isTrue();
    assertChangelogContains(true, PRIVATE_BUCKET, CHANGED, norm);
  }

  @Test
  void itDeletesNormFromPublicBucket() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    final PublishPublicNormPort.Command commandPublish = new PublishPublicNormPort.Command(norm);
    bucketService.publishPublicNorm(commandPublish);

    // When
    final DeletePublicNormPort.Command commandDelete = new DeletePublicNormPort.Command(norm);
    bucketService.deletePublicNorm(commandDelete);
    bucketService.publishChangelogs();

    // Then
    final Path filePath = getPublicPath(norm);
    assertThat(Files.exists(filePath)).isFalse();
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm);
    assertChangelogContains(false, PUBLIC_BUCKET, CHANGED, norm);
  }

  @Test
  void itDeletesNormFromPrivateBucket() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    final PublishPrivateNormPort.Command commandPublish = new PublishPrivateNormPort.Command(norm);
    bucketService.publishPrivateNorm(commandPublish);

    // When
    final DeletePrivateNormPort.Command commandDelete = new DeletePrivateNormPort.Command(norm);
    bucketService.deletePrivateNorm(commandDelete);
    bucketService.publishChangelogs();

    // Then
    final Path filePath = getPrivatePath(norm);
    assertThat(Files.exists(filePath)).isFalse();
    assertChangelogContains(false, PRIVATE_BUCKET, DELETED, norm);
    assertChangelogContains(false, PRIVATE_BUCKET, CHANGED, norm);
  }

  @Test
  void itDeletesAllNormsFromPublicBucket() {
    // Given
    final Norm norm1 = NormFixtures.loadFromDisk("SimpleNorm.xml");
    final Norm norm2 = NormFixtures.loadFromDisk("NormToBeReleased.xml");
    final PublishPublicNormPort.Command commandPublish1 = new PublishPublicNormPort.Command(norm1);
    final PublishPublicNormPort.Command commandPublish2 = new PublishPublicNormPort.Command(norm2);
    bucketService.publishPublicNorm(commandPublish1);
    bucketService.publishPublicNorm(commandPublish2);

    // When
    bucketService.deleteAllPublicNorms();
    bucketService.publishChangelogs();

    // Then
    final Path filePath1 = getPublicPath(norm1);
    final Path filePath2 = getPublicPath(norm2);
    assertThat(Files.exists(filePath1)).isFalse();
    assertThat(Files.exists(filePath2)).isFalse();
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm1);
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm2);
  }

  @Test
  void itDeletesAllNormsFromPrivateBucket() {
    // Given
    final Norm norm1 = NormFixtures.loadFromDisk("SimpleNorm.xml");
    final Norm norm2 = NormFixtures.loadFromDisk("NormToBeReleased.xml");
    final PublishPrivateNormPort.Command commandPublish1 = new PublishPrivateNormPort.Command(
      norm1
    );
    final PublishPrivateNormPort.Command commandPublish2 = new PublishPrivateNormPort.Command(
      norm2
    );
    bucketService.publishPrivateNorm(commandPublish1);
    bucketService.publishPrivateNorm(commandPublish2);

    // When
    bucketService.deleteAllPrivateNorms();
    bucketService.publishChangelogs();

    // Then
    final Path filePath1 = getPrivatePath(norm1);
    final Path filePath2 = getPrivatePath(norm2);
    assertThat(Files.exists(filePath1)).isFalse();
    assertThat(Files.exists(filePath2)).isFalse();
    assertChangelogContains(false, PRIVATE_BUCKET, DELETED, norm1);
    assertChangelogContains(false, PRIVATE_BUCKET, DELETED, norm2);
  }

  @Test
  void itAddsToExistingChangelog() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    final PublishPublicNormPort.Command command = new PublishPublicNormPort.Command(norm);
    final Norm anotherNorm = NormFixtures.loadFromDisk("NormToBeReleased.xml");
    final PublishPublicNormPort.Command commandAnotherNorm = new PublishPublicNormPort.Command(
      anotherNorm
    );
    // When
    bucketService.publishPublicNorm(command);
    bucketService.publishChangelogs();
    bucketService.publishPublicNorm(commandAnotherNorm);
    bucketService.publishChangelogs();

    // Then
    assertChangelogContains(true, PUBLIC_BUCKET, CHANGED, norm);
    assertChangelogContains(true, PUBLIC_BUCKET, CHANGED, anotherNorm);
  }

  private void assertChangelogContains(
    final boolean contains,
    final String location,
    final String operation,
    final Norm norm
  ) {
    Path changeLogPath;
    if (Objects.equals(location, PUBLIC_BUCKET)) {
      changeLogPath =
      getPublicPath()
        .resolve(Changelog.FOLDER)
        .resolve("changelog-%s.json".formatted(LocalDate.now().toString()));
    } else {
      changeLogPath =
      getPrivatePath()
        .resolve(Changelog.FOLDER)
        .resolve("changelog-%s.json".formatted(LocalDate.now().toString()));
    }

    final Map<String, Set<String>> changelogEntries;
    try {
      final String json = Files.readString(changeLogPath);
      changelogEntries = OBJECT_MAPPER.readValue(json, new TypeReference<>() {});
    } catch (Exception e) {
      throw new RuntimeException("Failed to read changelog file", e);
    }

    // Get the set for the specified operation
    final Set<String> set = changelogEntries.get(operation);

    if (contains) {
      assertThat(set).isNotNull();
      assertThat(set).contains(norm.getManifestationEli().toString());
    } else {
      if (set == null) {
        return;
      }
      assertThat(set).contains(norm.getManifestationEli().toString());
    }
  }
}
