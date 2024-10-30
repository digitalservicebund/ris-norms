package de.bund.digitalservice.ris.norms.integration.adapter.output.s3;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BucketServiceIntegrationTest extends BaseS3MockIntegrationTest {

  @Autowired
  private BucketService bucketService;

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
  }
}
