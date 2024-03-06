package de.bund.digitalservice.ris.norms.application.port.input;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoadArticlesQueryTest {

  @Test
  void canCreateQueryWithEli() {
    // Given
    final String eli = "someEli";

    // When
    final LoadArticlesUseCase.Query query = new LoadArticlesUseCase.Query(eli);

    // Then
    assertThat(query.eli()).isEqualTo(eli);
  }
}
