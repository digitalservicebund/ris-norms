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
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    final Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-2.xml"
    );
    final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    final BinaryFile binaryFile1 = Fixtures.loadBinaryFileFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png",
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
    final PublishNormPort.Options options = new PublishNormPort.Options(norm);
    final PublishChangelogPort.Options optionsPublishChangelogs = new PublishChangelogPort.Options(
      false
    );
    bucketService.publishNorm(options);
    bucketService.publishChangelogs(optionsPublishChangelogs);

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
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    final Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-2.xml"
    );
    final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    final BinaryFile binaryFile1 = Fixtures.loadBinaryFileFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png",
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.xml"
      )
    );
    final Norm norm = new Norm(
      NormPublishState.QUEUED_FOR_PUBLISH,
      Set.of(regelungstext1, offenestruktur1, regelungstext2),
      Set.of(binaryFile1)
    );
    final PublishNormPort.Options optionsPublish = new PublishNormPort.Options(norm);
    bucketService.publishNorm(optionsPublish);

    // When
    final DeletePublishedNormPort.Options optionsDelete = new DeletePublishedNormPort.Options(norm);
    bucketService.deletePublishedNorm(optionsDelete);
    final PublishChangelogPort.Options optionsPublishChangelogs = new PublishChangelogPort.Options(
      false
    );
    bucketService.publishChangelogs(optionsPublishChangelogs);

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
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    final Norm norm2 = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16"
    );
    final PublishNormPort.Options optionsPublish1 = new PublishNormPort.Options(norm1);
    final PublishNormPort.Options optionsPublish2 = new PublishNormPort.Options(norm2);
    bucketService.publishNorm(optionsPublish1);
    bucketService.publishNorm(optionsPublish2);

    // When
    bucketService.deleteAllPublishedDokumente(
      new DeleteAllPublishedDokumentePort.Options(Instant.now())
    );
    final PublishChangelogPort.Options optionsPublishChangelogs = new PublishChangelogPort.Options(
      false
    );
    bucketService.publishChangelogs(optionsPublishChangelogs);

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
      assertThat(set).containsAll(
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
      assertThat(set).containsAll(
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
