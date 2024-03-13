package de.bund.digitalservice.ris.norms.application.port.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UpdateAmendingLawXmlQueryTest {

  @Test
  void canCreateQueryWithEliAndXml() {
    // Given
    final String eli = "someEli";
    final String xml = "<amendingLaw>...</amendingLaw>";

    // When
    final UpdateAmendingLawXmlUseCase.Query query = new UpdateAmendingLawXmlUseCase.Query(eli, xml);

    // Then
    assertThat(query.eli()).isEqualTo(eli);
    assertThat(query.xml()).isEqualTo(xml);
  }
}
