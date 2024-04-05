package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.TargetLawDto;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.application.service.XmlDocumentService;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import org.junit.jupiter.api.Test;

class TargetLawMapperTest {

  @Test
  void testMapToDomain() {
    // Given
    TimeMachineService timeMachineService = new TimeMachineService(new XmlDocumentService());
    final TargetLawDto targetLawDto =
        TargetLawDto.builder()
            .eli("123")
            .title("Test Law")
            .xml(
                timeMachineService.stringToXmlDocument(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Test XML</test>"))
            .fna("4711")
            .shortTitle("TL")
            .build();

    // When
    final TargetLaw targetLaw = TargetLawMapper.mapToDomain(targetLawDto);

    // Then
    assertNotNull(targetLaw);
    assertEquals(targetLawDto.getEli(), targetLaw.getEli());
    assertEquals(targetLawDto.getTitle(), targetLaw.getTitle());
    assertEquals(
        targetLawDto.getXml(), timeMachineService.convertDocumentToString(targetLaw.getXml()));
    assertEquals(targetLawDto.getFna(), targetLaw.getFna());
    assertEquals(targetLawDto.getShortTitle(), targetLaw.getShortTitle());
  }

  @Test
  void testMapToDto() {
    // Given
    TimeMachineService timeMachineService = new TimeMachineService(new XmlDocumentService());
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("456")
            .title("Another Test Law")
            .xml(
                timeMachineService.stringToXmlDocument(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Another Test XML</test>"))
            .build();

    // When
    final TargetLawDto targetLawDto = TargetLawMapper.mapToDto(targetLaw);

    // Then
    assertNotNull(targetLawDto);
    assertEquals(targetLaw.getEli(), targetLawDto.getEli());
    assertEquals(targetLaw.getTitle(), targetLawDto.getTitle());
    assertEquals(
        timeMachineService.convertDocumentToString(targetLaw.getXml()), targetLawDto.getXml());
  }
}
