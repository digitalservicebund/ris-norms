package de.bund.digitalservice.ris.norms.application.port.input;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoadTargetLawQueryTest {

  @Test
  void canCreateQueryWithEli() {
    // Given
    final String eli = "someEli";

    // When
    final LoadTargetLawUseCase.Query query = new LoadTargetLawUseCase.Query(eli);

    // Then
    assertThat(query.eli()).isEqualTo(eli);
  }
}
