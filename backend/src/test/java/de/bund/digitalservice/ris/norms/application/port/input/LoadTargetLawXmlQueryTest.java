package de.bund.digitalservice.ris.norms.application.port.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoadTargetLawXmlQueryTest {

  @Test
  void canCreateQueryWithEli() {
    // Given
    final String eli = "someEli";

    // When
    final LoadTargetLawXmlUseCase.Query query = new LoadTargetLawXmlUseCase.Query(eli);

    // Then
    assertThat(query.eli()).isEqualTo(eli);
  }
}
