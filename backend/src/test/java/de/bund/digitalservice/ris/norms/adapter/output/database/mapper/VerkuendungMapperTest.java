package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungDto;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class VerkuendungMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var verkuendungDto = VerkuendungDto
      .builder()
      .eliNormExpression("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      .importTimestamp(Instant.parse("2025-03-12T12:00:00.0Z"))
      .build();

    // When
    final Verkuendung verkuendung = VerkuendungMapper.mapToDomain(verkuendungDto);

    // Then
    assertThat(verkuendung).isNotNull();
    assertThat(verkuendung.getEli()).hasToString(verkuendungDto.getEliNormExpression());
    assertThat(verkuendung.getImportTimestamp()).isEqualTo(verkuendungDto.getImportTimestamp());
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var verkuendung = Verkuendung
      .builder()
      .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
      .importTimestamp(Instant.parse("2025-03-12T12:00:00.0Z"))
      .build();

    // When
    final VerkuendungDto verkuendungDto = VerkuendungMapper.mapToDto(verkuendung);

    // Then
    assertThat(verkuendungDto).isNotNull();
    assertThat(verkuendungDto.getEliNormExpression()).isEqualTo(verkuendung.getEli().toString());
    assertThat(verkuendungDto.getImportTimestamp()).isNull();
  }
}
