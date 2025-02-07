package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import org.junit.jupiter.api.Test;

class AnnouncementMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var announcementDto = AnnouncementDto
      .builder()
      .eliNormExpression("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      .build();

    // When
    final Announcement announcement = AnnouncementMapper.mapToDomain(announcementDto);

    // Then
    assertThat(announcement).isNotNull();
    assertThat(announcement.getEli()).hasToString(announcementDto.getEliNormExpression());
    // TODO: (Malte Laukötter, 2024-10-28) test release mapping
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var announcement = Announcement
      .builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
      .build();

    // When
    final AnnouncementDto announcementDto = AnnouncementMapper.mapToDto(announcement);

    // Then
    assertThat(announcementDto).isNotNull();
    assertThat(announcementDto.getEliNormExpression()).isEqualTo(announcement.getEli().toString());
    // TODO: (Malte Laukötter, 2024-10-28) test release mapping
  }
}
