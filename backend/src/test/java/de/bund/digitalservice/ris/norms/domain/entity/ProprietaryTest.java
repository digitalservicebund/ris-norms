package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProprietaryTest {
  @Test
  void returnsTheFna() {
    // Given
    var norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
    var proprietary = norm.getProprietary().orElseThrow();

    // When
    var fna = proprietary.getFna();

    // Then
    assertThat(fna).contains("754-28-1");
  }
}
