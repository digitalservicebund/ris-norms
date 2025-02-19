package de.bund.digitalservice.ris.norms.integration.adapter.output.s3;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bund.digitalservice.ris.norms.adapter.output.s3.BucketService;
import de.bund.digitalservice.ris.norms.adapter.output.s3.Changelog;
import de.bund.digitalservice.ris.norms.application.port.output.*;
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
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
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
    final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
    final Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "SimpleRegelungstext2.xml"
    );
    final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "SimpleOffenestruktur.xml"
    );
    final Norm norm = new Norm(
      NormPublishState.QUEUED_FOR_PUBLISH,
      Set.of(regelungstext1, offenestruktur1, regelungstext2)
    );

    // When
    final PublishPublicNormPort.Command command = new PublishPublicNormPort.Command(norm);
    final PublishChangelogsPort.Command commandPublishChangelogs =
      new PublishChangelogsPort.Command(false);
    bucketService.publishPublicNorm(command);
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    assertThat(Files.exists(getPublicPath(regelungstext1))).isTrue();
    assertThat(Files.exists(getPublicPath(regelungstext2))).isTrue();
    assertThat(Files.exists(getPublicPath(offenestruktur1))).isTrue();
    assertChangelogContains(true, PUBLIC_BUCKET, CHANGED, norm);
  }

  @Test
  void itPublishesNormToPrivateBucket() {
    // Given
    final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
    final Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "SimpleRegelungstext2.xml"
    );
    final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "SimpleOffenestruktur.xml"
    );
    final Norm norm = new Norm(
      NormPublishState.QUEUED_FOR_PUBLISH,
      Set.of(regelungstext1, offenestruktur1, regelungstext2)
    );

    // When
    final PublishPrivateNormPort.Command command = new PublishPrivateNormPort.Command(norm);
    final PublishChangelogsPort.Command commandPublishChangelogs =
      new PublishChangelogsPort.Command(false);
    bucketService.publishPrivateNorm(command);
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    assertThat(Files.exists(getPrivatePath(regelungstext1))).isTrue();
    assertThat(Files.exists(getPrivatePath(regelungstext2))).isTrue();
    assertThat(Files.exists(getPrivatePath(offenestruktur1))).isTrue();
    assertChangelogContains(true, PRIVATE_BUCKET, CHANGED, norm);
  }

  @Test
  void itPublishesNormToPublicAndPrivateBucket() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");

    // When
    final PublishPublicNormPort.Command commandPublic = new PublishPublicNormPort.Command(norm);
    bucketService.publishPublicNorm(commandPublic);
    final PublishPrivateNormPort.Command commandPrivate = new PublishPrivateNormPort.Command(norm);
    bucketService.publishPrivateNorm(commandPrivate);
    final PublishChangelogsPort.Command commandPublishChangelogs =
      new PublishChangelogsPort.Command(false);
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    final Path publicFilePath = getPublicPath(norm.getRegelungstext1());
    assertThat(Files.exists(publicFilePath)).isTrue();
    assertChangelogContains(true, PUBLIC_BUCKET, CHANGED, norm);
    final Path privateFilePath = getPrivatePath(norm.getRegelungstext1());
    assertThat(Files.exists(privateFilePath)).isTrue();
    assertChangelogContains(true, PRIVATE_BUCKET, CHANGED, norm);
  }

  @Test
  void itDeletesNormFromPublicBucket() {
    // Given
    final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
    final Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "SimpleRegelungstext2.xml"
    );
    final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "SimpleOffenestruktur.xml"
    );
    final Norm norm = new Norm(
      NormPublishState.QUEUED_FOR_PUBLISH,
      Set.of(regelungstext1, offenestruktur1, regelungstext2)
    );
    final PublishPublicNormPort.Command commandPublish = new PublishPublicNormPort.Command(norm);
    bucketService.publishPublicNorm(commandPublish);

    // When
    final DeletePublicNormPort.Command commandDelete = new DeletePublicNormPort.Command(norm);
    bucketService.deletePublicNorm(commandDelete);
    final PublishChangelogsPort.Command commandPublishChangelogs =
      new PublishChangelogsPort.Command(false);
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    assertThat(Files.exists(getPublicPath(regelungstext1))).isFalse();
    assertThat(Files.exists(getPublicPath(regelungstext2))).isFalse();
    assertThat(Files.exists(getPublicPath(offenestruktur1))).isFalse();
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm);
    assertChangelogContains(false, PUBLIC_BUCKET, CHANGED, norm);
  }

  @Test
  void itDeletesNormFromPrivateBucket() {
    // Given
    final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
    final Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "SimpleRegelungstext2.xml"
    );
    final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "SimpleOffenestruktur.xml"
    );
    final Norm norm = new Norm(
      NormPublishState.QUEUED_FOR_PUBLISH,
      Set.of(regelungstext1, offenestruktur1, regelungstext2)
    );
    final PublishPrivateNormPort.Command commandPublish = new PublishPrivateNormPort.Command(norm);
    bucketService.publishPrivateNorm(commandPublish);

    // When
    final DeletePrivateNormPort.Command commandDelete = new DeletePrivateNormPort.Command(norm);
    bucketService.deletePrivateNorm(commandDelete);
    final PublishChangelogsPort.Command commandPublishChangelogs =
      new PublishChangelogsPort.Command(false);
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    assertThat(Files.exists(getPublicPath(regelungstext1))).isFalse();
    assertThat(Files.exists(getPublicPath(regelungstext2))).isFalse();
    assertThat(Files.exists(getPublicPath(offenestruktur1))).isFalse();
    assertChangelogContains(false, PRIVATE_BUCKET, DELETED, norm);
    assertChangelogContains(false, PRIVATE_BUCKET, CHANGED, norm);
  }

  @Test
  void itDeletesAllNormsFromPublicBucket() {
    // Given
    final Norm norm1 = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    final Norm norm2 = Fixtures.loadNormFromDisk("NormToBeReleased.xml");
    final PublishPublicNormPort.Command commandPublish1 = new PublishPublicNormPort.Command(norm1);
    final PublishPublicNormPort.Command commandPublish2 = new PublishPublicNormPort.Command(norm2);
    bucketService.publishPublicNorm(commandPublish1);
    bucketService.publishPublicNorm(commandPublish2);

    // When
    bucketService.deleteAllPublicDokumente(new DeleteAllPublicDokumentePort.Command(Instant.now()));
    final PublishChangelogsPort.Command commandPublishChangelogs =
      new PublishChangelogsPort.Command(false);
    bucketService.publishChangelogs(commandPublishChangelogs);

    // Then
    final Path filePath1 = getPublicPath(norm1.getRegelungstext1());
    final Path filePath2 = getPublicPath(norm2.getRegelungstext1());
    assertThat(Files.exists(filePath1)).isFalse();
    assertThat(Files.exists(filePath2)).isFalse();
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm1);
    assertChangelogContains(false, PUBLIC_BUCKET, DELETED, norm2);
  }

  @Test
  void itDeletesAllNormsFromPrivateBucket() throws InterruptedException {
    // Given
    final Norm norm1 = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    final Norm norm2 = Fixtures.loadNormFromDisk("NormToBeReleased.xml");
    final Norm norm3 = Fixtures.loadNormFromDisk("NormWithMods.xml");

    bucketService.publishPrivateNorm(new PublishPrivateNormPort.Command(norm1));
    bucketService.publishPrivateNorm(new PublishPrivateNormPort.Command(norm2));
    Instant afterTwoPublishes = Instant.now();
    // ensure the time of afterTwoPublishes is always before the time publishPrivateNorm is called with norm3. If the
    // difference is too small norm3 is also deleted later on which leads to a flaky test.
    Thread.sleep(Duration.ofMillis(100));
    bucketService.publishChangelogs(new PublishChangelogsPort.Command(false));
    bucketService.publishPrivateNorm(new PublishPrivateNormPort.Command(norm3));

    // When
    bucketService.deleteAllPrivateDokumente(
      new DeleteAllPrivateDokumentePort.Command(afterTwoPublishes)
    );
    bucketService.publishChangelogs(new PublishChangelogsPort.Command(false));

    // Then
    final Path filePath1 = getPrivatePath(norm1.getRegelungstext1());
    final Path filePath2 = getPrivatePath(norm2.getRegelungstext1());
    final Path filePath3 = getPrivatePath(norm3.getRegelungstext1());
    assertThat(Files.exists(filePath1)).isFalse();
    assertThat(Files.exists(filePath2)).isFalse();
    assertThat(Files.exists(filePath3)).isTrue();
    assertChangelogContains(false, PRIVATE_BUCKET, DELETED, norm1);
    assertChangelogContains(false, PRIVATE_BUCKET, DELETED, norm2);
    assertChangelogContains(true, PRIVATE_BUCKET, CHANGED, norm3);
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
