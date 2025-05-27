package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class DokumentMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var regelungstextXml = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    var dokumentDto = DokumentDto.builder().xml(regelungstextXml).subtype("regelungstext").build();

    // When
    final Dokument dokument = DokumentMapper.mapToDomain(dokumentDto);

    // Then
    assertThat(dokument).isNotNull().isInstanceOf(Regelungstext.class);
    assertThat(dokument.getDocument().isEqualNode(XmlMapper.toDocument(regelungstextXml))).isTrue();
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var xml = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    // When
    final DokumentDto dokumentDto = DokumentMapper.mapToDto(regelungstext);

    // Then
    assertThat(dokumentDto).isNotNull();
    assertThat(
      XmlMapper.toDocument(dokumentDto.getXml()).isEqualNode(regelungstext.getDocument())
    ).isTrue();
  }
}
