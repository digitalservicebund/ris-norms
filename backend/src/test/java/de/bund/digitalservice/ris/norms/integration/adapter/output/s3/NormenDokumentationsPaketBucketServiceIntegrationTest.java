package de.bund.digitalservice.ris.norms.integration.adapter.output.s3;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.s3.NormenDokumentationsPaketBucketService;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

class NormenDokumentationsPaketBucketServiceIntegrationTest extends BaseS3MockIntegrationTest {

  @Autowired
  private NormenDokumentationsPaketBucketService bucketService;

  @Test
  void itPublishesNormToBucket() throws IOException {
    // Given
    Resource file = new UrlResource(
      Objects.requireNonNull(
        NormenDokumentationsPaketBucketServiceIntegrationTest.class.getResource(
            "SimpleNorm.xml.zip"
          )
      )
    );
    Resource signature = new UrlResource(
      Objects.requireNonNull(
        NormenDokumentationsPaketBucketServiceIntegrationTest.class.getResource("signature.sig")
      )
    );

    // When
    final SaveNormendokumentationspaketPort.Command command =
      new SaveNormendokumentationspaketPort.Command(
        UUID.fromString("b6a825ce-e065-4e24-918a-6f9e6c7fafdf"),
        file,
        signature
      );

    bucketService.saveNormendokumentationspaket(command);

    // Then
    assertThat(
      Files.exists(
        getEverkuendungPath()
          .resolve("b6a825ce-e065-4e24-918a-6f9e6c7fafdf")
          .resolve("SimpleNorm.xml.zip")
      )
    )
      .isTrue();
    assertThat(
      Files.exists(
        getEverkuendungPath()
          .resolve("b6a825ce-e065-4e24-918a-6f9e6c7fafdf")
          .resolve("signature.sig")
      )
    )
      .isTrue();
  }
}
