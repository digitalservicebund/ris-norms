package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TargetLawResponseSchema;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.application.service.XmlDocumentService;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import org.junit.jupiter.api.Test;

class TargetLawResponseMapperTest {

  final XmlDocumentService xmlDocumentService = new XmlDocumentService();
  final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);


  @Test
  void canMapSimpleResponseSchema() {
    // Given
    final TargetLaw targetLaw1 =
        TargetLaw.builder()
            .eli("target-law-eli-1")
            .title("target law article 1")
            .xml(timeMachineService.stringToXmlDocument("<target>1</target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    // When
    final TargetLawResponseSchema resultTargetLaw =
        TargetLawResponseMapper.fromUseCaseData(targetLaw1);

    // Then
    assertThat(resultTargetLaw.getEli()).isEqualTo("target-law-eli-1");
    assertThat(resultTargetLaw.getTitle()).isEqualTo("target law article 1");
    assertThat(resultTargetLaw.getFna()).isEqualTo("4711");
    assertThat(resultTargetLaw.getShortTitle()).isEqualTo("targetlaw");
  }
}
