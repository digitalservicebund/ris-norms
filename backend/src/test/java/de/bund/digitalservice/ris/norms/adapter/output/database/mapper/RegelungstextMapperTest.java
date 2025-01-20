package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class RegelungstextMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var regelungstextXml = Fixtures.loadTextFromDisk("SimpleNorm.xml");
    var dokumentDto = DokumentDto.builder().xml(regelungstextXml).build();

    // When
    final Regelungstext regelungstext = RegelungstextMapper.mapToDomain(dokumentDto);

    // Then
    assertThat(regelungstext).isNotNull();
    assertThat(regelungstext.getDocument().isEqualNode(XmlMapper.toDocument(regelungstextXml)))
      .isTrue();
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var xml = Fixtures.loadTextFromDisk("SimpleNorm.xml");
    var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    // When
    final DokumentDto dokumentDto = RegelungstextMapper.mapToDto(
      regelungstext,
      NormPublishState.QUEUED_FOR_PUBLISH
    );

    // Then
    assertThat(dokumentDto).isNotNull();
    assertThat(XmlMapper.toDocument(dokumentDto.getXml()).isEqualNode(regelungstext.getDocument()))
      .isTrue();
    assertThat(dokumentDto.getPublishState()).isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
  }
}
