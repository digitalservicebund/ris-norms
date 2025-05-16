package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.BinaryFileDto;
import de.bund.digitalservice.ris.norms.domain.entity.BinaryFile;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import org.junit.jupiter.api.Test;

class BinaryFileMapperTest {

  @Test
  void itShouldMapToDomain() {
    var binaryFileFixture = Fixtures.loadBinaryFileFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png",
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
      )
    );
    var binaryFileDto = new BinaryFileDto(
      binaryFileFixture.getDokumentManifestationEli().toString(),
      binaryFileFixture.getDokumentManifestationEli().asNormEli().toString(),
      binaryFileFixture.getContent()
    );

    // When
    final BinaryFile binaryFile = BinaryFileMapper.mapToDomain(binaryFileDto);

    // Then
    assertThat(binaryFile.getContent()).isEqualTo(binaryFileFixture.getContent());
    assertThat(binaryFile.getDokumentManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
    );
  }

  @Test
  void itShouldMapToDtos() {
    var binaryFile = Fixtures.loadBinaryFileFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png",
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
      )
    );

    // When
    final BinaryFileDto binaryFileDto = BinaryFileMapper.mapToDto(binaryFile);

    // Then
    assertThat(binaryFileDto.getContent()).isEqualTo(binaryFile.getContent());
    assertThat(binaryFileDto.getEliDokumentManifestation()).isEqualTo(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/image-1.png"
    );
  }
}
