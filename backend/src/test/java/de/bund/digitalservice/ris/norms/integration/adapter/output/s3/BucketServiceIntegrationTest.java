package de.bund.digitalservice.ris.norms.integration.adapter.output.s3;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bund.digitalservice.ris.norms.adapter.output.s3.BucketService;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BucketServiceIntegrationTest extends BaseS3MockIntegrationTest {

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

    // Then
    final Path filePath = getPublicPath(norm);
    assertThat(Files.exists(filePath)).isTrue();
    assertChangelogContains(PUBLIC_BUCKET, "PUT", norm);
  }

  @Test
  void itPublishesNormToPrivateBucket() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

    // When
    final PublishPrivateNormPort.Command command = new PublishPrivateNormPort.Command(norm);
    bucketService.publishPrivateNorm(command);

    // Then
    final Path filePath = getPrivatePath(norm);
    assertThat(Files.exists(filePath)).isTrue();
    assertChangelogContains(PRIVATE_BUCKET, "PUT", norm);
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

    // Then
    final Path filePath = getPublicPath(norm);
    assertThat(Files.exists(filePath)).isFalse();
    assertChangelogContains(PUBLIC_BUCKET, "DELETE", norm);
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

    // Then
    final Path filePath = getPrivatePath(norm);
    assertThat(Files.exists(filePath)).isFalse();
    assertChangelogContains(PRIVATE_BUCKET, "DELETE", norm);
  }

  @Test
  void itAddsToExistingChangelog() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    final PublishPublicNormPort.Command command = new PublishPublicNormPort.Command(norm);
    bucketService.publishPublicNorm(command);
    final Norm anotherNorm = NormFixtures.loadFromDisk("NormToBeReleased.xml");
    // When
    final PublishPublicNormPort.Command commandAnotherNorm = new PublishPublicNormPort.Command(
      anotherNorm
    );
    bucketService.publishPublicNorm(commandAnotherNorm);
    final DeletePublicNormPort.Command deleteCommand = new DeletePublicNormPort.Command(norm);
    bucketService.deletePublicNorm(deleteCommand);

    // Then
    assertChangelogContains(PUBLIC_BUCKET, "PUT", norm);
    assertChangelogContains(PUBLIC_BUCKET, "PUT", anotherNorm);
    assertChangelogContains(PUBLIC_BUCKET, "DELETE", norm);
  }

  private void assertChangelogContains(final String location, String operation, Norm norm) {
    Path changeLogPath;
    if (Objects.equals(location, PUBLIC_BUCKET)) {
      changeLogPath = getPublicPath().resolve("changelog.json");
    } else {
      changeLogPath = getPrivatePath().resolve("changelog.json");
    }

    final List<Map<String, Object>> changelogEntries;
    try {
      final String json = Files.readString(changeLogPath);
      changelogEntries = OBJECT_MAPPER.readValue(json, new TypeReference<>() {});
    } catch (Exception e) {
      throw new RuntimeException("Failed to read changelog file", e);
    }

    assertThat(
      changelogEntries
        .stream()
        .anyMatch(entry ->
          operation.equals(entry.get("operation")) &&
          norm.getManifestationEli().toString().equals(entry.get("norm")) &&
          entry
            .get("timestamp")
            .toString()
            .startsWith(LocalDate.now().format(DateTimeFormatter.ISO_DATE))
        )
    )
      .isTrue();
  }
}
