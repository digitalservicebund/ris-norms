package de.bund.digitalservice.ris.norms.integration.adapter.output.s3;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bund.digitalservice.ris.norms.adapter.output.s3.BucketService;
import de.bund.digitalservice.ris.norms.adapter.output.s3.Changelog;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.BinaryFile;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

class BucketServiceIntegrationTest extends BaseS3MockIntegrationTest {

  private static final String CHANGED = "changed";
  private static final String DELETED = "deleted";

  @Autowired
  @Qualifier("public")
  private BucketService bucketService;

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  void itPublishesNormToBucket() {
    // Given
    final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "SimpleRegelungstext2.xml"
    );
    final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/offenestruktur-1.xml"
    );
    final BinaryFile binaryFile1 = Fixtures.loadBinaryFileFromDisk(
      "image-1.png",
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.xml"
      )
    );
    final Norm norm = new Norm(
      NormPublishState.QUEUED_FOR_PUBLISH,
      Set.of(regelungstext1, offenestruktur1, regelungstext2),
      Set.of(binaryFile1)
    );

    // When
    final PublishNormPort.Command command = new PublishNormPort.Command(norm);
    final PublishChangelogPort.Command commandPublishChangelogs = new PublishChangelogPort.Command(
      false
    );
    bucketService.publishNorm(command);
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    assertThat(Files.exists(getPublicPath(regelungstext1.getManifestationEli()))).isTrue();
    assertThat(Files.exists(getPublicPath(regelungstext2.getManifestationEli()))).isTrue();
    assertThat(Files.exists(getPublicPath(offenestruktur1.getManifestationEli()))).isTrue();
    assertThat(Files.exists(getPublicPath(binaryFile1.getDokumentManifestationEli()))).isTrue();
    assertChangelogContains(true, PUBLIC_BUCKET, CHANGED, norm);
  }

  @Test
  void itDeletesNormFromPublicBucket() {
    // Given
    final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "SimpleRegelungstext2.xml"
    );
    final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/offenestruktur-1.xml"
    );
    final BinaryFile binaryFile1 = Fixtures.loadBinaryFileFromDisk(
      "image-1.png",
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.xml"
      )
    );
    final Norm norm = new Norm(
      NormPublishState.QUEUED_FOR_PUBLISH,
      Set.of(regelungstext1, offenestruktur1, regelungstext2),
      Set.of(binaryFile1)
    );
    final PublishNormPort.Command commandPublish = new PublishNormPort.Command(norm);
    bucketService.publishNorm(commandPublish);

    // When
    final DeletePublishedNormPort.Command commandDelete = new DeletePublishedNormPort.Command(norm);
    bucketService.deletePublishedNorm(commandDelete);
    final PublishChangelogPort.Command commandPublishChangelogs = new PublishChangelogPort.Command(
      false
    );
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    assertThat(Files.exists(getPublicPath(regelungstext1.getManifestationEli()))).isFalse();
    assertThat(Files.exists(getPublicPath(regelungstext2.getManifestationEli()))).isFalse();
    assertThat(Files.exists(getPublicPath(offenestruktur1.getManifestationEli()))).isFalse();
    assertThat(Files.exists(getPublicPath(binaryFile1.getDokumentManifestationEli()))).isFalse();
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm);
    assertChangelogContains(false, PUBLIC_BUCKET, CHANGED, norm);
  }

  @Test
  void itDeletesAllNormsFromPublicBucket() {
    // Given
    final Norm norm1 = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final Norm norm2 = Fixtures.loadNormFromDisk("NormToBeReleased.xml");
    final PublishNormPort.Command commandPublish1 = new PublishNormPort.Command(norm1);
    final PublishNormPort.Command commandPublish2 = new PublishNormPort.Command(norm2);
    bucketService.publishNorm(commandPublish1);
    bucketService.publishNorm(commandPublish2);

    // When
    bucketService.deleteAllPublishedDokumente(
      new DeleteAllPublishedDokumentePort.Command(Instant.now())
    );
    final PublishChangelogPort.Command commandPublishChangelogs = new PublishChangelogPort.Command(
      false
    );
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    final Path filePath1 = getPublicPath(norm1.getRegelungstext1().getManifestationEli());
    final Path filePath2 = getPublicPath(norm2.getRegelungstext1().getManifestationEli());
    assertThat(Files.exists(filePath1)).isFalse();
    assertThat(Files.exists(filePath2)).isFalse();
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm1);
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm2);
  }

  private void assertChangelogContains(
    final boolean contains,
    final String location,
    final String operation,
    final Norm norm
  ) {
    final Map<String, Set<String>> changelogEntries;

    try {
      Path changeLogPath;
      if (Objects.equals(location, PUBLIC_BUCKET)) {
        try (Stream<Path> files = Files.list(getPublicPath().resolve(Changelog.FOLDER))) {
          changeLogPath = files.max(Comparator.naturalOrder()).orElseThrow();
        }
      } else {
        try (Stream<Path> files = Files.list(getPrivatePath().resolve(Changelog.FOLDER))) {
          changeLogPath = files.max(Comparator.naturalOrder()).orElseThrow();
        }
      }

      final String json = Files.readString(changeLogPath);
      changelogEntries = OBJECT_MAPPER.readValue(json, new TypeReference<>() {});
    } catch (Exception e) {
      throw new RuntimeException("Failed to read changelog file", e);
    }

    // Get the set for the specified operation
    final Set<String> set = changelogEntries.get(operation);

    if (contains) {
      assertThat(set).isNotNull();
      assertThat(set)
        .containsAll(
          norm
            .getDokumente()
            .stream()
            .map(Dokument::getManifestationEli)
            .map(DokumentManifestationEli::toString)
            .toList()
        );
    } else {
      if (set == null) {
        return;
      }
      assertThat(set)
        .containsAll(
          norm
            .getDokumente()
            .stream()
            .map(Dokument::getManifestationEli)
            .map(DokumentManifestationEli::toString)
            .toList()
        );
    }
  }
}
