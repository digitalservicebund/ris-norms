package de.bund.digitalservice.ris.norms.application.port.output;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoadAmendingLawXmlCommandTest {

  @Test
  void canCreateCommandWithEli() {
    // Given
    final String eli = "someEli";

    // When
    final LoadAmendingLawXmlPort.Command command = new LoadAmendingLawXmlPort.Command(eli);

    // Then
    assertThat(command.eli()).isEqualTo(eli);
  }
}
