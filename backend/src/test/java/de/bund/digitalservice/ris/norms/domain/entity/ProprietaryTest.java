package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProprietaryTest {
  @Test
  void returnsTheFna() {
    // Given
    var norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
    var proprietary = norm.getProprietary();

    // When
    var fna = proprietary.getFna();

    // Then
    assertThat(fna).contains("754-28-1");
  }

  @Test
  void returnsEmptyOptionalIfFnaIsMissing() {
    // given
    var norm = NormFixtures.loadFromDisk("NormWithInvalidProprietary.xml");
    var proprietary = norm.getProprietary();
    // when
    var fna = proprietary.getFna();
    // then
    assertThat(fna).isEmpty();
  }
}
