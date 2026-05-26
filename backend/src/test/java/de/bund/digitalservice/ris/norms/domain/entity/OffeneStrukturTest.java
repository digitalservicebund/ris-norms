package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OffeneStrukturTest {

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
