package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OffeneStrukturTest {

  @Test
  void getWorkEliShouldReturnExpectedValue() {
    final var offeneStruktur = Fixtures.loadOffeneStrukturFromDisk("OffeneStruktur.xml");
    assertThat(offeneStruktur.getWorkEli().toString())
      .isEqualTo("eli/bund/bgbl-1/2020/s3092/offenestruktur-1");
  }

  @Test
  void getExpressionEliShouldReturnExpectedValue() {
    final var offeneStruktur = Fixtures.loadOffeneStrukturFromDisk("OffeneStruktur.xml");
    assertThat(offeneStruktur.getExpressionEli().toString())
      .isEqualTo("eli/bund/bgbl-1/2020/s3092/2020-12-23/1/deu/offenestruktur-1");
  }

  @Test
  void getManifestationEliShouldReturnExpectedValue() {
    final var offeneStruktur = Fixtures.loadOffeneStrukturFromDisk("OffeneStruktur.xml");
    assertThat(offeneStruktur.getManifestationEli().toString())
      .isEqualTo("eli/bund/bgbl-1/2020/s3092/2020-12-23/1/deu/2022-08-23/offenestruktur-1.xml");
  }

  @Test
  void copyShouldReturnEqualButDistinctInstance() {
    final var original = Fixtures.loadOffeneStrukturFromDisk("OffeneStruktur.xml");
    final var copy = (OffeneStruktur) original.copy();

    // They should be equal in content but not the same instance.
    assertThat(copy).isEqualTo(original);
    assertThat(copy).isNotSameAs(original);
    // The underlying documents should be separate instances.
    assertThat(copy.getDocument()).isNotSameAs(original.getDocument());
  }

  @Test
  void equalsShouldReturnTrueForInstancesWithSameXml() {
    final var offeneStruktur1 = Fixtures.loadOffeneStrukturFromDisk("OffeneStruktur.xml");
    final var offeneStruktur2 = Fixtures.loadOffeneStrukturFromDisk("OffeneStruktur.xml");
    assertThat(offeneStruktur1).isEqualTo(offeneStruktur2);
  }

  @Test
  void hashCodeShouldBeConsistentForSameXml() {
    final var offeneStruktur1 = Fixtures.loadOffeneStrukturFromDisk("OffeneStruktur.xml");
    final var offeneStruktur2 = Fixtures.loadOffeneStrukturFromDisk("OffeneStruktur.xml");
    assertThat(offeneStruktur1.hashCode()).isEqualTo(offeneStruktur2.hashCode());
  }
}
