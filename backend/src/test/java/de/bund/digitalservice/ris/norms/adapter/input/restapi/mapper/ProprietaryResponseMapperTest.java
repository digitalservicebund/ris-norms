package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import org.junit.jupiter.api.Test;

class ProprietaryResponseMapperTest {
  @Test
  void convertsProprietaryToResponseSchema() {
    // Given
    var norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
    var proprietary = norm.getMeta().getProprietary();

    // When
    var converted = ProprietaryResponseMapper.fromProprietary(proprietary);

    // Then
    assertThat(converted.getFna().getValue()).isEqualTo("754-28-1");
  }
}
