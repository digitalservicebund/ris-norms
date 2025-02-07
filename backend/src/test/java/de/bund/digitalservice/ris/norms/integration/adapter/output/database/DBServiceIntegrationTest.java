package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

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
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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

  @AfterEach
  void cleanUp() {
    announcementRepository.deleteAll();
    releaseRepository.deleteAll();
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
    migrationLogRepository.deleteAll();
  }

  @Nested
  class loadNorm {

    @Test
    void itFindsNormOnDB() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
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
          assertThat(normDb).contains(Fixtures.loadNormFromDisk("SimpleNorm.xml"))
        );
    }

    @Test
    void itFindsAllDokumenteOfNormOnDB() {
      // Given
      var regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
      var regelungstext2 = Fixtures.loadRegelungstextFromDisk("SimpleRegelungstext2.xml");
      var offenestruktur = Fixtures.loadOffeneStrukturFromDisk("SimpleOffenestruktur.xml");
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
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

      // When
      final Optional<Norm> normOptional = dbService.loadNorm(
        new LoadNormPort.Command(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(normOptional).contains(Fixtures.loadNormFromDisk("SimpleNorm.xml"));
    }

    @Test
    void itFindsNormByManifestationEli() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

      // When
      final Optional<Norm> normOptional = dbService.loadNorm(
        new LoadNormPort.Command(
          NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        )
      );

      // Then
      assertThat(normOptional).contains(Fixtures.loadNormFromDisk("SimpleNorm.xml"));
    }

    @Test
    void itFindsNewestManifestationOfNorm() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
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
        "NormWithoutPassiveModifications.xml"
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
      var regelungstext = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
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
      DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
    );

    // When
    final Optional<Norm> normOptional = dbService.loadNormByGuid(
      new LoadNormByGuidPort.Command(UUID.fromString("77167d15-511d-4927-adf3-3c8b0464423c"))
    );

    // Then
    assertThat(normOptional).isPresent().contains(Fixtures.loadNormFromDisk("SimpleNorm.xml"));
  }

  @Test
  void itFindsAnnouncementOnDB() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
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
    assertThat(announcementOptional).isPresent().contains(announcement);
  }

  @Test
  void itLoadsAllAnnouncementsFromDB() {
    // Given
    dokumentRepository.save(
      DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
    );
    dokumentRepository.save(
      DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
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
    assertThat(announcements).containsExactly(announcement2, announcement1);
  }

  @Nested
  class updateNorm {

    @Test
    void itUpdatesNorm() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithAppliedQuotedStructure.xml")
        )
      );

      var newNorm = Fixtures.loadNormFromDisk("NormWithAppliedQuotedStructure.xml");
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
      var regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
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
      DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
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
    assertThat(announcementFromDatabase).isEqualTo(announcement);
  }

  @Nested
  class saveReleaseToAnnouncement {

    @Test
    void itUpdatesAnnouncementAndSavesRelease() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      var release = Release.builder().publishedNorms(List.of(norm)).build();

      // When
      dbService.saveReleaseToAnnouncement(
        new SaveReleaseToAnnouncementPort.Command(release, announcement)
      );

      var savedAnnouncement = announcementRepository.findByEliNormExpression(
        norm.getExpressionEli().toString()
      );

      assertThat(savedAnnouncement).isPresent();
      assertThat(savedAnnouncement.get().getReleases()).hasSize(1);
      assertThat(savedAnnouncement.get().getReleases().getFirst().getNorms()).hasSize(1);
      assertThat(
        savedAnnouncement.get().getReleases().getFirst().getNorms().getFirst().getManifestationEli()
      )
        .isEqualTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    }
  }

  @Nested
  class deleteQueuedReleases {

    @Test
    void itDeletesQueuedReleases() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normDto = normManifestationRepository.save(normDto);

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .build();
      var release = Release
        .builder()
        .publishedNorms(List.of(Fixtures.loadNormFromDisk("SimpleNorm.xml")))
        .build();

      var releaseDto = ReleaseMapper.mapToDto(release);
      releaseDto.setNorms(List.of(normDto));
      releaseRepository.save(releaseDto);

      var announcementDto = AnnouncementMapper.mapToDto(announcement);
      announcementDto.setReleases(List.of(releaseDto));
      announcementRepository.save(announcementDto);

      // When
      dbService.deleteQueuedReleases(new DeleteQueuedReleasesPort.Command(announcement.getEli()));

      // Then
      var savedAnnouncement = announcementRepository.findByEliNormExpression(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );
      assertThat(savedAnnouncement).isPresent();
      assertThat(savedAnnouncement.get().getReleases()).isEmpty();

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
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normManifestationRepository.save(normDto);

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormToBeReleased.xml"))
      );
      var normDto2 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/2022-08-23")
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
      var migrationLog1 = new MigrationLog(5, date1.atStartOfDay().toInstant(ZoneOffset.UTC));

      var date2 = LocalDate.parse("2024-11-05");
      var migrationLog2 = new MigrationLog(12, date2.atStartOfDay().toInstant(ZoneOffset.UTC));

      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog1));
      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog2));

      // When
      final Optional<MigrationLog> migrationLogOptional = dbService.loadLastMigrationLog();

      // Then
      assertThat(migrationLogOptional)
        .isPresent()
        .satisfies(log -> assertThat(log).contains(migrationLog1));
    }

    @Test
    void itLoadLastMigrationLogWithSameDate() {
      // Given
      var date1 = LocalDate.parse("2024-11-06");
      var migrationLog1 = new MigrationLog(5, date1.atTime(9, 30).toInstant(ZoneOffset.UTC));

      var migrationLog2 = new MigrationLog(12, date1.atTime(11, 45).toInstant(ZoneOffset.UTC));

      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog1));
      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog2));

      // When
      final Optional<MigrationLog> migrationLogOptional = dbService.loadLastMigrationLog();

      // Then
      assertThat(migrationLogOptional)
        .isPresent()
        .satisfies(log -> assertThat(log).contains(migrationLog2));
    }
  }
}
