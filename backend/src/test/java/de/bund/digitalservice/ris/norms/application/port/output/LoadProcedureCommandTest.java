package de.bund.digitalservice.ris.norms.application.port.output;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoadProcedureCommandTest {

  @Test
  void canCreateCommandWithUUID() {
    // Given
    final String eli = "someEli";
    // When
    final LoadProcedurePort.Command command = new LoadProcedurePort.Command(eli);

    // Then
    assertThat(command.eli()).isEqualTo(eli);
  }
}
