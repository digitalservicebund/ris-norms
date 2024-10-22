package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class AnnouncementMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var announcementDto = AnnouncementDto
      .builder()
      .releasedByDocumentalistAt(Instant.now())
      .eli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
      .build();

    // When
    final Announcement announcement = AnnouncementMapper.mapToDomain(announcementDto);

    // Then
    assertThat(announcement).isNotNull();
    assertThat(announcement.getEli()).hasToString(announcementDto.getEli());
    assertThat(announcement.getReleasedByDocumentalistAt())
      .isEqualTo(announcementDto.getReleasedByDocumentalistAt());
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var announcement = Announcement
      .builder()
      .eli(ExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"))
      .releasedByDocumentalistAt(Instant.now())
      .build();

    // When
    final AnnouncementDto announcementDto = AnnouncementMapper.mapToDto(announcement);

    // Then
    assertThat(announcementDto).isNotNull();
    assertThat(announcementDto.getEli()).isEqualTo(announcement.getEli().toString());
    assertThat(announcementDto.getReleasedByDocumentalistAt())
      .isEqualTo(announcement.getReleasedByDocumentalistAt());
  }
}
