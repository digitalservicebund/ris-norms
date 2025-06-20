package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.BinaryFileDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.domain.entity.BinaryFile;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NormMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var regelungstext1Xml = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    var dokumentDto1 = DokumentDto.builder()
      .xml(regelungstext1Xml)
      .subtype("/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung")
      .build();

    var regelungstext2Xml = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
    );
    var dokumentDto2 = DokumentDto.builder()
      .xml(regelungstext2Xml)
      .subtype("/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung")
      .build();

    var offenestrukturXml = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    var offenestrukturDto = DokumentDto.builder()
      .xml(offenestrukturXml)
      .subtype("/akn/ontology/de/concept/documenttype/bund/anlage-regelungstext")
      .build();

    var binaryFileFixture = Fixtures.loadBinaryFileFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png",
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
      )
    );
    var binaryFileDto = new BinaryFileDto(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png",
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
      binaryFileFixture.getContent()
    );

    // When
    final Norm norm = NormManifestationMapper.mapToDomain(
      NormManifestationDto.builder()
        .dokumente(List.of(dokumentDto1, dokumentDto2, offenestrukturDto))
        .binaryFiles(List.of(binaryFileDto))
        .publishState(NormPublishState.QUEUED_FOR_PUBLISH)
        .build()
    );

    // Then
    assertThat(norm.getPublishState()).isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    assertThat(norm.getDokumente()).containsExactlyInAnyOrder(
      new Regelungstext(XmlMapper.toDocument(regelungstext1Xml)),
      new Regelungstext(XmlMapper.toDocument(regelungstext2Xml)),
      new OffeneStruktur(XmlMapper.toDocument(offenestrukturXml))
    );
    assertThat(norm.getBinaryFiles()).containsExactlyInAnyOrder(
      new BinaryFile(
        DokumentManifestationEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
        ),
        binaryFileFixture.getContent()
      )
    );
  }

  @Test
  void itShouldMapToDtos() {
    // Given
    var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
    );
    var offeneStruktur = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    var binaryFile = Fixtures.loadBinaryFileFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png",
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
      )
    );
    var norm = Norm.builder()
      .dokumente(Set.of(regelungstext1, regelungstext2, offeneStruktur))
      .binaryFiles(Set.of(binaryFile))
      .publishState(NormPublishState.PUBLISHED)
      .build();

    // When
    final NormManifestationDto normManifestationDto = NormManifestationMapper.mapToDto(norm);

    // Then
    assertThat(normManifestationDto.getPublishState()).isEqualTo(NormPublishState.PUBLISHED);
    assertThat(normManifestationDto.getDokumente()).hasSize(3);
    assertThat(normManifestationDto.getDokumente())
      .map(DokumentDto::getXml)
      .containsExactlyInAnyOrder(
        XmlMapper.toString(regelungstext1.getDocument()),
        XmlMapper.toString(regelungstext2.getDocument()),
        XmlMapper.toString(offeneStruktur.getDocument())
      );
    assertThat(normManifestationDto.getBinaryFiles()).hasSize(1);
    assertThat(normManifestationDto.getBinaryFiles())
      .map(BinaryFileDto::getContent)
      .containsExactlyInAnyOrder(binaryFile.getContent());
    assertThat(normManifestationDto.getBinaryFiles())
      .map(BinaryFileDto::getEliDokumentManifestation)
      .containsExactlyInAnyOrder(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
      );
  }
}
