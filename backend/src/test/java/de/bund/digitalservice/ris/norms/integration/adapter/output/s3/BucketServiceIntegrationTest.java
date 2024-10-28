package de.bund.digitalservice.ris.norms.integration.adapter.output.s3;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.s3.BucketService;
import de.bund.digitalservice.ris.norms.application.port.output.PublishPrivateNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishPublicNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BucketServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private BucketService bucketService;

  private static final String PUBLIC_BUCKET = "public";
  private static final String PRIVATE_BUCKET = "private";

  @AfterEach
  void emptyDir() throws Exception {
    FileUtils.cleanDirectory(new File(LOCAL_STORAGE_PATH));
  }

  @AfterAll
  static void removeDir() throws Exception {
    FileUtils.deleteDirectory(new File(LOCAL_STORAGE_PATH));
  }

  @Test
  void itPublishesNormToPublicBucket() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    final PublishPublicNormPort.Command command = new PublishPublicNormPort.Command(norm);

    // When
    bucketService.publishPublicNorm(command);

    // Then
    final Path filePath = Paths.get(
      LOCAL_STORAGE_PATH,
      PUBLIC_BUCKET,
      norm.getManifestationEli().toString()
    );
    assertThat(Files.exists(filePath)).isTrue();
  }

  @Test
  void itPublishesNormToPrivateBucket() {
    // Given
    final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    final PublishPrivateNormPort.Command command = new PublishPrivateNormPort.Command(norm);

    // When
    bucketService.publishPrivateNorm(command);

    // Then
    final Path filePath = Paths.get(
      LOCAL_STORAGE_PATH,
      PRIVATE_BUCKET,
      norm.getManifestationEli().toString()
    );
    assertThat(Files.exists(filePath)).isTrue();
  }
}
