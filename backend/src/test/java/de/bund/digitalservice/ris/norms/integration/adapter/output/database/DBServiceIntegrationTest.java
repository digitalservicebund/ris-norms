package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.MigrationLogDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDetailDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.BinaryFileMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.MigrationLogMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ReleaseMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.MigrationLogRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private DBService dbService;

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @Autowired
  private ReleaseRepository releaseRepository;

  @Autowired
  private MigrationLogRepository migrationLogRepository;

  @Autowired
  private VerkuendungImportProcessesRepository verkuendungImportProcessesRepository;

  @AfterEach
  void cleanUp() {
    announcementRepository.deleteAll();
    releaseRepository.deleteAll();
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
    migrationLogRepository.deleteAll();
    verkuendungImportProcessesRepository.deleteAll();
  }

  @Nested
  class loadNorm {

    @Test
    void itFindsNormOnDB() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When
      final Optional<Norm> normOptional = dbService.loadNorm(
        new LoadNormPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional)
        .isPresent()
        .satisfies(normDb ->
          assertThat(normDb)
            .contains(
              Fixtures.loadNormFromDisk(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
              )
            )
        );
    }

    @Test
    void itFindsAllDokumenteOfNormOnDB() {
      // Given
      var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var regelungstext2 = Fixtures.loadRegelungstextFromDisk("SimpleRegelungstext2.xml");
      var offenestruktur = Fixtures.loadOffeneStrukturFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/offenestruktur-1.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext1));
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext2));
      dokumentRepository.save(DokumentMapper.mapToDto(offenestruktur));

      var binaryFile = Fixtures.loadBinaryFileFromDisk(
        "image-1.png",
        DokumentManifestationEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
        )
      );
      binaryFileRepository.save(BinaryFileMapper.mapToDto(binaryFile));

      var norm = new Norm(
        NormPublishState.UNPUBLISHED,
        Set.of(regelungstext1, regelungstext2, offenestruktur),
        Set.of(binaryFile)
      );

      // When
      final Optional<Norm> normOptional = dbService.loadNorm(
        new LoadNormPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional).isPresent();
      assertThat(normOptional.get().getRegelungstexte()).hasSize(2);
      assertThat(normOptional.get().getDokumente()).hasSize(3);
      assertThat(normOptional.get().getBinaryFiles()).hasSize(1);
      assertThat(normOptional).contains(norm);
    }

    @Test
    void itFindsNormByManifestationEliWithoutPointInTimeManifestationOnDB() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When
      final Optional<Norm> normOptional = dbService.loadNorm(
        new LoadNormPort.Command(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional)
        .contains(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        );
    }

    @Test
    void itFindsNormByManifestationEli() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When
      final Optional<Norm> normOptional = dbService.loadNorm(
        new LoadNormPort.Command(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        )
      );

      // Then
      assertThat(normOptional)
        .contains(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        );
    }

    @Test
    void itFindsNewestManifestationOfNorm() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      // When
      final Optional<Norm> normOptional = dbService.loadNorm(
        new LoadNormPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional)
        .contains(Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml"));
    }
  }

  @Nested
  class loadRegelungstext {

    @Test
    void itFindsRegelungstextByExpressionEli() {
      // Given
      var regelungstextOld = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var regelungstextCurrent = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstextOld));
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstextCurrent));

      // When
      var loadedRegelungstext = dbService.loadRegelungstext(
        new LoadRegelungstextPort.Command(
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
          )
        )
      );

      // Then
      assertThat(loadedRegelungstext).contains(regelungstextCurrent);
    }

    @Test
    void itFindsRegelungstextByManifestationEli() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext));

      // When
      var loadedRegelungstext = dbService.loadRegelungstext(
        new LoadRegelungstextPort.Command(
          DokumentManifestationEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // Then
      assertThat(loadedRegelungstext).contains(regelungstext);
    }
  }

  @Test
  void itFindsNormByGuidOnDB() {
    // When
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
    );

    // When
    final Optional<Norm> normOptional = dbService.loadNormByGuid(
      new LoadNormByGuidPort.Command(UUID.fromString("d04791fc-dcdc-47e6-aefb-bc2f7aaee151"))
    );

    // Then
    assertThat(normOptional)
      .isPresent()
      .contains(
        Fixtures.loadNormFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      );
  }

  @Test
  void itFindsAnnouncementOnDB() {
    // Given
    var norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    dokumentRepository.save(DokumentMapper.mapToDto(norm.getRegelungstext1()));
    var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

    // When
    final Optional<Announcement> announcementOptional = dbService.loadAnnouncementByNormEli(
      new LoadAnnouncementByNormEliPort.Command(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      )
    );

    // Then
    assertThat(announcementOptional)
      .get()
      .usingRecursiveComparison()
      .ignoringFields("importTimestamp")
      .isEqualTo(announcement);

    assertThat(announcementOptional.get().getImportTimestamp())
      .isCloseTo(Instant.now(), new TemporalUnitWithinOffset(5, ChronoUnit.MINUTES));
  }

  @Test
  void itLoadsAllAnnouncementsFromDB() {
    // Given
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
    );
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
    );

    var announcement1 = Announcement
      .builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
      .build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement1));
    var announcement2 = Announcement
      .builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
      .build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement2));

    // When
    final List<Announcement> announcements = dbService.loadAllAnnouncements();

    // Then
    assertThat(announcements)
      .usingRecursiveFieldByFieldElementComparatorIgnoringFields("importTimestamp")
      .containsExactly(announcement2, announcement1);
  }

  @Nested
  class updateNorm {

    @Test
    void itUpdatesNorm() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
          )
        )
      );

      var newNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
      );
      newNorm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      // When
      var normFromDatabase = dbService.updateNorm(new UpdateNormPort.Command(newNorm));

      // Then
      assertThat(dokumentRepository.findAll()).hasSize(1);
      assertThat(normFromDatabase).contains(newNorm);
    }

    @Test
    void itUpdatesNormWithMultipleRegelungstexte() {
      // Given
      var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var regelungstext2 = Fixtures.loadRegelungstextFromDisk("SimpleRegelungstext2.xml");
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext1));
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext2));

      var newNorm = new Norm(
        NormPublishState.QUEUED_FOR_PUBLISH,
        Set.of(regelungstext1, regelungstext2)
      );
      newNorm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      // When
      var normFromDatabase = dbService.updateNorm(new UpdateNormPort.Command(newNorm));

      // Then
      assertThat(dokumentRepository.findAll()).hasSize(2);
      assertThat(normFromDatabase).contains(newNorm);
    }
  }

  @Test
  void itCreatesNewAnnouncement() {
    // Given
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
    );
    var announcement = Announcement
      .builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
      .build();

    // When
    var announcementFromDatabase = dbService.updateOrSaveAnnouncement(
      new UpdateOrSaveAnnouncementPort.Command(announcement)
    );

    // Then
    assertThat(announcementFromDatabase)
      .usingRecursiveComparison()
      .ignoringFields("importTimestamp")
      .isEqualTo(announcement);
    assertThat(announcementFromDatabase.getImportTimestamp())
      .isNotNull()
      .isCloseTo(Instant.now(), within(1, ChronoUnit.MINUTES));
  }

  @Nested
  class saveRelease {

    @Test
    void itSavesRelease() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var release = Release.builder().publishedNorms(List.of(norm)).build();

      // When
      dbService.saveRelease(new SaveReleasePort.Command(release));

      var releases = releaseRepository.findAll();

      assertThat(releases).hasSize(1);
      assertThat(releases.getFirst().getNorms()).hasSize(1);
      assertThat(releases.getFirst().getNorms().getFirst().getManifestationEli())
        .isEqualTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    }
  }

  @Nested
  class deleteQueuedReleases {

    @Test
    void itDeletesQueuedReleases() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normDto = normManifestationRepository.save(normDto);

      var release = Release
        .builder()
        .publishedNorms(
          List.of(
            Fixtures.loadNormFromDisk(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
            )
          )
        )
        .build();

      var releaseDto = ReleaseMapper.mapToDto(release);
      releaseDto.setNorms(List.of(normDto));
      releaseRepository.save(releaseDto);

      // When
      dbService.deleteQueuedReleases(
        new DeleteQueuedReleasesPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      var savedReleases = releaseRepository.findAllByNormExpressionEli(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );
      assertThat(savedReleases).isEmpty();

      var savedNorm = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      assertThat(savedNorm).isPresent();
    }
  }

  @Nested
  class loadNormsByPublishState {

    @Test
    void itLoadsNormIdByPublishState() {
      // Given

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normManifestationRepository.save(normDto);

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16/regelungstext-1.xml"
          )
        )
      );
      var normDto2 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16")
        .orElseThrow();
      normDto2.setPublishState(NormPublishState.PUBLISHED);
      normManifestationRepository.save(normDto);

      // When
      final List<NormManifestationEli> publishedNorms =
        dbService.loadNormManifestationElisByPublishState(
          new LoadNormManifestationElisByPublishStatePort.Command(
            NormPublishState.QUEUED_FOR_PUBLISH
          )
        );

      // Then
      assertThat(publishedNorms).isNotEmpty().hasSize(1);
    }
  }

  @Nested
  class loadsMigrationLog {

    @Test
    void itLoadLastMigrationLogWithTwoDates() {
      // Given
      var date1 = LocalDate.parse("2024-11-06");
      var migrationLog1 = MigrationLog
        .builder()
        .createdAt(date1.atStartOfDay().toInstant(ZoneOffset.UTC))
        .size(5)
        .completed(false)
        .build();

      var date2 = LocalDate.parse("2024-11-05");
      var migrationLog2 = MigrationLog
        .builder()
        .createdAt(date2.atStartOfDay().toInstant(ZoneOffset.UTC))
        .size(5)
        .completed(false)
        .build();

      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog1));
      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog2));

      // When
      final Optional<MigrationLog> migrationLogOptional = dbService.loadLastMigrationLog();

      // Then
      assertThat(migrationLogOptional)
        .get()
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(migrationLog1);
    }

    @Test
    void itLoadLastMigrationLogWithSameDate() {
      // Given
      var date1 = LocalDate.parse("2024-11-06");
      var migrationLog1 = MigrationLog
        .builder()
        .createdAt(date1.atTime(9, 30).toInstant(ZoneOffset.UTC))
        .size(5)
        .completed(false)
        .build();

      var migrationLog2 = MigrationLog
        .builder()
        .createdAt(date1.atTime(11, 45).toInstant(ZoneOffset.UTC))
        .size(12)
        .completed(false)
        .build();

      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog1));
      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog2));

      // When
      final Optional<MigrationLog> migrationLogOptional = dbService.loadLastMigrationLog();

      // Then
      assertThat(migrationLogOptional)
        .get()
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(migrationLog2);
    }
  }

  @Nested
  class updateMigrationLogCompleted {

    @Test
    void itSetsAnExistingMigrationLogToCompleted() {
      // Given
      var savedMigrationLog = migrationLogRepository.save(
        MigrationLogDto
          .builder()
          .size(5)
          .createdAt(Instant.parse("2025-03-03T15:00:00.0Z"))
          .completed(false)
          .build()
      );

      // When
      dbService.completeMigrationLog(
        new CompleteMigrationLogPort.Command(savedMigrationLog.getId())
      );
      var updatedMigrationLog = migrationLogRepository.findAll().getFirst();

      // Then
      assertThat(migrationLogRepository.findAll()).hasSize(1);
      assertThat(updatedMigrationLog.isCompleted()).isTrue();
    }
  }

  @Nested
  class loadReleasesByNormExpressionEli {

    @Test
    void itLoadsRelease() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normDto = normManifestationRepository.save(normDto);

      var release = Release
        .builder()
        .publishedNorms(
          List.of(
            Fixtures.loadNormFromDisk(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
            )
          )
        )
        .build();

      var releaseDto = ReleaseMapper.mapToDto(release);
      releaseDto.setNorms(List.of(normDto));
      releaseRepository.save(releaseDto);
      releaseRepository.save(
        ReleaseMapper.mapToDto(Release.builder().releasedAt(Instant.now()).build())
      );

      // When
      var releases = dbService.loadReleasesByNormExpressionEli(
        new LoadReleasesByNormExpressionEliPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      assertThat(releases).hasSize(1);
      assertThat(releases.getFirst().getPublishedNorms()).hasSize(1);
      assertThat(releases.getFirst().getPublishedNorms().getFirst().getManifestationEli())
        .isEqualTo(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        );
    }

    @Test
    void itLoadsReleases() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normDto = normManifestationRepository.save(normDto);
      var release = Release
        .builder()
        .publishedNorms(
          List.of(
            Fixtures.loadNormFromDisk(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
            )
          )
        )
        .build();
      var releaseDto = ReleaseMapper.mapToDto(release);
      releaseDto.setNorms(List.of(normDto));
      releaseRepository.save(releaseDto);

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );
      var normDto2 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23")
        .orElseThrow();
      var release2 = Release
        .builder()
        .publishedNorms(
          List.of(
            Fixtures.loadNormFromDisk(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
            )
          )
        )
        .build();
      var releaseDto2 = ReleaseMapper.mapToDto(release2);
      releaseDto2.setNorms(List.of(normDto2));
      releaseRepository.save(releaseDto2);

      releaseRepository.save(
        ReleaseMapper.mapToDto(Release.builder().releasedAt(Instant.now()).build())
      );

      // When
      var releases = dbService.loadReleasesByNormExpressionEli(
        new LoadReleasesByNormExpressionEliPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      assertThat(releases).hasSize(2);
      assertThat(releases.getFirst().getPublishedNorms()).hasSize(1);
      assertThat(releases.getFirst().getPublishedNorms().getFirst().getManifestationEli())
        .isEqualTo(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        );
      assertThat(releases.get(1).getPublishedNorms()).hasSize(1);
      assertThat(releases.get(1).getPublishedNorms().getFirst().getManifestationEli())
        .isEqualTo(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23")
        );
    }
  }

  @Nested
  class loadVerkuendungImportProcess {

    @Test
    void itLoadsAProcessWithoutDetails() {
      // Given
      var dto = VerkuendungImportProcessDto
        .builder()
        .id(null)
        .status(VerkuendungImportProcessDto.Status.ERROR)
        .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
        .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
        .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
        .detail(List.of())
        .build();

      var saved = verkuendungImportProcessesRepository.save(dto);

      // When
      var resultOptional = dbService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(saved.getId())
      );

      // Then
      assertThat(resultOptional).isPresent();
      assertThat(resultOptional.get().getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
      assertThat(resultOptional.get().getCreatedAt()).isEqualTo(dto.getCreatedAt());
      assertThat(resultOptional.get().getStartedAt()).isEqualTo(dto.getStartedAt());
      assertThat(resultOptional.get().getFinishedAt()).isEqualTo(dto.getFinishedAt());
      assertThat(resultOptional.get().getDetail()).isEmpty();
    }

    @Test
    void itLoadsAProcessWithDetails() {
      // Given
      var dtoDetail = new VerkuendungImportProcessDetailDto(
        null,
        "/example/type",
        "example title",
        "example detail"
      );
      var dto = VerkuendungImportProcessDto
        .builder()
        .id(null)
        .status(VerkuendungImportProcessDto.Status.ERROR)
        .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
        .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
        .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
        .detail(List.of(dtoDetail))
        .build();

      var saved = verkuendungImportProcessesRepository.save(dto);

      // When
      var resultOptional = dbService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(saved.getId())
      );
      var detail = resultOptional.get().getDetail().getFirst();

      // Then
      assertThat(resultOptional).isPresent();
      assertThat(resultOptional.get().getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
      assertThat(resultOptional.get().getCreatedAt()).isEqualTo(dto.getCreatedAt());
      assertThat(resultOptional.get().getStartedAt()).isEqualTo(dto.getStartedAt());
      assertThat(resultOptional.get().getFinishedAt()).isEqualTo(dto.getFinishedAt());
      assertThat(resultOptional.get().getDetail()).hasSize(1);

      assertThat(detail.getType()).isEqualTo(dtoDetail.getType());
      assertThat(detail.getTitle()).isEqualTo(dtoDetail.getTitle());
      assertThat(detail.getDetail()).isEqualTo(dtoDetail.getDetail());
    }

    @Test
    void itReturnsEmptyIfTheProcessDoesntExist() {
      // Given
      // Nothing

      // When
      var resultOptional = dbService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(UUID.randomUUID())
      );

      // Then
      assertThat(resultOptional).isEmpty();
    }
  }
}
