package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NormMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var regelungstext1Xml = Fixtures.loadTextFromDisk("SimpleNorm.xml");
    var dokumentDto1 = DokumentDto.builder().xml(regelungstext1Xml).build();

    var regelungstext2Xml = Fixtures.loadTextFromDisk("NormWithMods.xml");
    var dokumentDto2 = DokumentDto.builder().xml(regelungstext2Xml).build();

    // When
    final Norm norm = NormManifestationMapper.mapToDomain(
      NormManifestationDto
        .builder()
        .dokumente(List.of(dokumentDto1, dokumentDto2))
        .publishState(NormPublishState.QUEUED_FOR_PUBLISH)
        .build()
    );

    // Then
    assertThat(norm.getPublishState()).isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    assertThat(norm.getRegelungstexte())
      .containsExactlyInAnyOrder(
        new Regelungstext(XmlMapper.toDocument(regelungstext1Xml)),
        new Regelungstext(XmlMapper.toDocument(regelungstext2Xml))
      );
  }

  @Test
  void itShouldMapToDtos() {
    // Given
    var regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
    var regelungstext2 = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
    var norm = Norm
      .builder()
      .dokumente(Set.of(regelungstext1, regelungstext2))
      .publishState(NormPublishState.PUBLISHED)
      .build();

    // When
    final NormManifestationDto normManifestationDto = NormManifestationMapper.mapToDto(norm);

    // Then
    assertThat(normManifestationDto.getPublishState()).isEqualTo(NormPublishState.PUBLISHED);
    assertThat(normManifestationDto.getDokumente())
      .map(DokumentDto::getXml)
      .containsExactlyInAnyOrder(
        XmlMapper.toString(regelungstext1.getDocument()),
        XmlMapper.toString(regelungstext2.getDocument())
      );
  }
}
