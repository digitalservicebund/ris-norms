package de.bund.digitalservice.ris.norms.application.port.output;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UpdateAmendingLawXmlCommandTest {

  @Test
  void canCreateCommandWithEliAndXml() {
    // Given
    final String eli = "someEli";
    final String xml = "<amendingLaw>...</amendingLaw>";

    // When
    final UpdateAmendingLawXmlPort.Command command = new UpdateAmendingLawXmlPort.Command(eli, xml);

    // Then
    assertThat(command.eli()).isEqualTo(eli);
    assertThat(command.xml()).isEqualTo(xml);
  }
}
