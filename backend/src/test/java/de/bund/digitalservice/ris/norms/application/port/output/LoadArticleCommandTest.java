package de.bund.digitalservice.ris.norms.application.port.output;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoadArticleCommandTest {

  @Test
  void canCreateCommandWithEliAndEid() {
    // Given
    final String eli = "someEli";
    final String eId = "someEId";

    // When
    final LoadArticlePort.Command command = new LoadArticlePort.Command(eli, eId);

    // Then
    assertThat(command.eli()).isEqualTo(eli);
    assertThat(command.eId()).isEqualTo(eId);
  }
}
