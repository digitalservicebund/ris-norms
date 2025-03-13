package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class AnnouncementMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var announcementDto = AnnouncementDto
      .builder()
      .eliNormExpression("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      .importTimestamp(Instant.parse("2025-03-12T12:00:00.0Z"))
      .build();

    // When
    final Announcement announcement = AnnouncementMapper.mapToDomain(announcementDto);

    // Then
    assertThat(announcement).isNotNull();
    assertThat(announcement.getEli()).hasToString(announcementDto.getEliNormExpression());
    assertThat(announcement.getImportTimestamp()).isEqualTo(announcementDto.getImportTimestamp());
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var announcement = Announcement
      .builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
      .importTimestamp(Instant.parse("2025-03-12T12:00:00.0Z"))
      .build();

    // When
    final AnnouncementDto announcementDto = AnnouncementMapper.mapToDto(announcement);

    // Then
    assertThat(announcementDto).isNotNull();
    assertThat(announcementDto.getEliNormExpression()).isEqualTo(announcement.getEli().toString());
    assertThat(announcementDto.getImportTimestamp()).isEqualTo(announcement.getImportTimestamp());
  }
}
