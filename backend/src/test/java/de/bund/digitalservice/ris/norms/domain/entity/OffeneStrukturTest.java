package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OffeneStrukturTest {

  @Test
  void getWorkEliShouldReturnExpectedValue() {
    final var offeneStruktur = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    assertThat(offeneStruktur.getWorkEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/anlage-regelungstext-1"
    );
  }

  @Test
  void getExpressionEliShouldReturnExpectedValue() {
    final var offeneStruktur = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    assertThat(offeneStruktur.getExpressionEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/anlage-regelungstext-1"
    );
  }

  @Test
  void getManifestationEliShouldReturnExpectedValue() {
    final var offeneStruktur = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    assertThat(offeneStruktur.getManifestationEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
  }

  @Test
  void copyShouldReturnEqualButDistinctInstance() {
    final var original = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    final var copy = (OffeneStruktur) original.copy();

    // They should be equal in content but not the same instance.
    assertThat(copy).isEqualTo(original).isNotSameAs(original);
    // The underlying documents should be separate instances.
    assertThat(copy.getDocument()).isNotSameAs(original.getDocument());
  }

  @Test
  void equalsShouldReturnTrueForInstancesWithSameXml() {
    final var offeneStruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    final var offeneStruktur2 = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    assertThat(offeneStruktur1).isEqualTo(offeneStruktur2);
  }

  @Test
  void hashCodeShouldBeConsistentForSameXml() {
    final var offeneStruktur1 = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    final var offeneStruktur2 = Fixtures.loadOffeneStrukturFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
    );
    assertThat(offeneStruktur1).hasSameHashCodeAs(offeneStruktur2);
  }
}
