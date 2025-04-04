package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.AnnouncementDBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AnnouncementDBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private AnnouncementDBService announcementDBService;

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    announcementRepository.deleteAll();
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
    final Optional<Announcement> announcementOptional =
      announcementDBService.loadAnnouncementByNormEli(
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
    final List<Announcement> announcements = announcementDBService.loadAllAnnouncements();

    // Then
    assertThat(announcements)
      .usingRecursiveFieldByFieldElementComparatorIgnoringFields("importTimestamp")
      .containsExactly(announcement2, announcement1);
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
    var announcementFromDatabase = announcementDBService.updateOrSaveAnnouncement(
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
}
