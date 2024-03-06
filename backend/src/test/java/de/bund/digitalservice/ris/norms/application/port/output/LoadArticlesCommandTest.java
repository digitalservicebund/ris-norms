package de.bund.digitalservice.ris.norms.application.port.output;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoadArticlesCommandTest {

  @Test
  void canCreateCommandWithEli() {
    // Given
    final String eli = "someEli";

    // When
    final LoadArticlesPort.Command command = new LoadArticlesPort.Command(eli);

    // Then
    assertThat(command.eli()).isEqualTo(eli);
  }
}
