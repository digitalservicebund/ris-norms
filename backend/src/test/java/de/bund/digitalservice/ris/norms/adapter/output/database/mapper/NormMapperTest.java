package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NormMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var regelungstext1Xml = Fixtures.loadTextFromDisk("SimpleNorm.xml");
    var dokumentDto1 = DokumentDto
      .builder()
      .xml(regelungstext1Xml)
      .publishState(NormPublishState.QUEUED_FOR_PUBLISH)
      .build();

    var regelungstext2Xml = Fixtures.loadTextFromDisk("NormWithMods.xml");
    var dokumentDto2 = DokumentDto
      .builder()
      .xml(regelungstext2Xml)
      .publishState(NormPublishState.QUEUED_FOR_PUBLISH)
      .build();

    // When
    final Optional<Norm> norm = NormMapper.mapToDomain(List.of(dokumentDto1, dokumentDto2));

    // Then
    assertThat(norm).isPresent();
    assertThat(norm.get().getPublishState()).isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    assertThat(norm.get().getRegelungstexte())
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
      .regelungstexte(Set.of(regelungstext1, regelungstext2))
      .publishState(NormPublishState.PUBLISHED)
      .build();

    // When
    final Set<DokumentDto> dokumentDtos = NormMapper.mapToDtos(norm);

    // Then
    assertThat(dokumentDtos).hasSize(2);
    assertThat(dokumentDtos.stream().findFirst().get().getPublishState())
      .isEqualTo(NormPublishState.PUBLISHED);
    assertThat(dokumentDtos)
      .map(DokumentDto::getXml)
      .containsExactlyInAnyOrder(
        XmlMapper.toString(regelungstext1.getDocument()),
        XmlMapper.toString(regelungstext2.getDocument())
      );
  }
}
