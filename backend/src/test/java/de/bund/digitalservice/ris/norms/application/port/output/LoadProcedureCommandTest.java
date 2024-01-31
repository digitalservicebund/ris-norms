package de.bund.digitalservice.ris.norms.application.port.output;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class LoadProcedureCommandTest {

  @Test
  void canCreateCommandWithUUID() {
    // Given
    final UUID guid = UUID.randomUUID();

    // When
    final LoadProcedurePort.Command command = new LoadProcedurePort.Command(guid);

    // Then
    assertThat(command.uuid()).isEqualTo(guid);
  }
}
