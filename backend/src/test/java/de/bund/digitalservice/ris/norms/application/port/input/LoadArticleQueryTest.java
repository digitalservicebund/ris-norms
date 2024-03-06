package de.bund.digitalservice.ris.norms.application.port.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoadArticleQueryTest {

  @Test
  void canCreateQueryWithEliAndEId() {
    // Given
    final String eli = "someEli";
    final String eId = "someEId";

    // When
    final LoadArticleUseCase.Query query = new LoadArticleUseCase.Query(eli, eId);

    // Then
    assertThat(query.eli()).isEqualTo(eli);
    assertThat(query.eId()).isEqualTo(eId);
  }
}
