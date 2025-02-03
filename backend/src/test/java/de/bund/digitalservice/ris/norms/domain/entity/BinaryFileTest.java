package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class BinaryFileTest {

  @Test
  void equalsShouldReturnTrueForIdenticalBinaryFiles() {
    final DokumentManifestationEli eli = DokumentManifestationEli.fromString(
      "eli/bund/bgbl-1/2020/s3092/2020-12-23/1/deu/2022-08-23/img-1.png"
    );
    byte[] content = "Sample content".getBytes(StandardCharsets.UTF_8);

    BinaryFile file1 = new BinaryFile(eli, content);
    // Create a separate copy of the content array.
    BinaryFile file2 = new BinaryFile(eli, Arrays.copyOf(content, content.length));

    // They should be equal (value equality) and have the same hash code.
    assertThat(file1).isEqualTo(file2).hasSameHashCodeAs(file2);
  }

  @Test
  void equalsShouldReturnFalseForDifferentEli() {
    final DokumentManifestationEli eli1 = DokumentManifestationEli.fromString(
      "eli/bund/bgbl-1/2020/s3092/2020-12-23/1/deu/2022-08-23/img-1.png"
    );
    final DokumentManifestationEli eli2 = DokumentManifestationEli.fromString(
      "eli/bund/bgbl-1/2020/s3092/2020-12-23/1/deu/2022-08-23/img-2.png"
    );
    byte[] content = "Sample content".getBytes(StandardCharsets.UTF_8);

    BinaryFile file1 = new BinaryFile(eli1, content);
    BinaryFile file2 = new BinaryFile(eli2, content);

    // With different ELIs, the files should not be equal.
    assertThat(file1).isNotEqualTo(file2);
  }

  @Test
  void equalsShouldReturnFalseForDifferentEliAndContent() {
    final DokumentManifestationEli eli1 = DokumentManifestationEli.fromString(
      "eli/bund/bgbl-1/2020/s3092/2020-12-23/1/deu/2022-08-23/img-1.png"
    );
    final DokumentManifestationEli eli2 = DokumentManifestationEli.fromString(
      "eli/bund/bgbl-1/2020/s3092/2020-12-23/1/deu/2022-08-23/img-2.png"
    );
    byte[] content1 = "Sample content".getBytes(StandardCharsets.UTF_8);
    byte[] content2 = "Different content".getBytes(StandardCharsets.UTF_8);

    BinaryFile file1 = new BinaryFile(eli1, content1);
    BinaryFile file2 = new BinaryFile(eli2, content2);

    assertThat(file1).isNotEqualTo(file2);
  }
}
