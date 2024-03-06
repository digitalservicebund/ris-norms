package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.TargetLawDto;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import org.junit.jupiter.api.Test;

class TargetLawMapperTest {

  @Test
  void testMapToDomain() {
    // Given
    final TargetLawDto targetLawDto =
        TargetLawDto.builder().eli("123").title("Test Law").xml("<xml>Test XML</xml>").build();

    // When
    final TargetLaw targetLaw = TargetLawMapper.mapToDomain(targetLawDto);

    // Then
    assertNotNull(targetLaw);
    assertEquals(targetLawDto.getEli(), targetLaw.getEli());
    assertEquals(targetLawDto.getTitle(), targetLaw.getTitle());
    assertEquals(targetLawDto.getXml(), targetLaw.getXml());
  }

  @Test
  void testMapToDto() {
    // Given
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("456")
            .title("Another Test Law")
            .xml("<xml>Another Test XML</xml>")
            .build();

    // When
    final TargetLawDto targetLawDto = TargetLawMapper.mapToDto(targetLaw);

    // Then
    assertNotNull(targetLawDto);
    assertEquals(targetLaw.getEli(), targetLawDto.getEli());
    assertEquals(targetLaw.getTitle(), targetLawDto.getTitle());
    assertEquals(targetLaw.getXml(), targetLawDto.getXml());
  }
}
