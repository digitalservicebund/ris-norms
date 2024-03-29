package de.bund.digitalservice.ris.norms.application.port.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoadAmendingLawQueryTest {

  @Test
  void canCreateQueryWithEli() {
    // Given
    final String eli = "someEli";

    // When
    final LoadAmendingLawUseCase.Query query = new LoadAmendingLawUseCase.Query(eli);

    // Then
    assertThat(query.eli()).isEqualTo(eli);
  }
}
