package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class VerkuendungResponseMapperTest {

  @Test
  void canMapPrintAnnouncedNorm() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    var verkuendung = Verkuendung.builder()
      .eli(norm.getExpressionEli())
      .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
      .build();

    // When
    final VerkuendungResponseSchema result = VerkuendungResponseMapper.fromAnnouncedNorm(
      verkuendung,
      norm
    );

    // Then
    assertThat(result.getEli()).isEqualTo(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    );
    assertThat(result.getTitle()).isEqualTo("Gesetz zur Regelung des öffentlichen Vereinsrechts");
    assertThat(result.getShortTitle()).isEqualTo("Vereinsgesetz");
    assertThat(result.getFrbrName()).isEqualTo("BGBl. I");
    assertThat(result.getFrbrNumber()).isEqualTo("s593");
    assertThat(result.getFrbrDateVerkuendung()).isEqualTo("1964-08-05");
    assertThat(result.getDateAusfertigung()).isEqualTo("1964-08-05");
    assertThat(result.getFna()).isEqualTo("754-28-1");
    assertThat(result.getImportedAt()).isEqualTo(verkuendung.getImportTimestamp());
  }

  @Test
  void canMapDigitallyAnnouncedNorm() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/2024-01-18");
    var verkuendung = Verkuendung.builder()
      .eli(norm.getExpressionEli())
      .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
      .build();

    // When
    final VerkuendungResponseSchema result = VerkuendungResponseMapper.fromAnnouncedNorm(
      verkuendung,
      norm
    );

    // Then
    assertThat(result.getEli()).isEqualTo(
      "eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/regelungstext-1"
    );
    assertThat(result.getTitle()).isEqualTo("Gesetz zur Änderung des Lobbyregistergesetzes");
    assertThat(result.getFrbrName()).isEqualTo("BGBl. I");
    assertThat(result.getFrbrNumber()).isEqualTo("10");
    assertThat(result.getFrbrDateVerkuendung()).isEqualTo("2024-01-18");
    assertThat(result.getDateAusfertigung()).isEqualTo("2024-01-15");
    assertThat(result.getFna()).isEqualTo("1101-13");
    assertThat(result.getImportedAt()).isEqualTo(verkuendung.getImportTimestamp());
  }
}
