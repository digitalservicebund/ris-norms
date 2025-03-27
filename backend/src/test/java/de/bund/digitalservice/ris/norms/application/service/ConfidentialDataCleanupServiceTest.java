package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import org.junit.jupiter.api.Test;

class ConfidentialDataCleanupServiceTest {

  ConfidentialDataCleanupService confidentialDataCleanupService =
    new ConfidentialDataCleanupService();

  @Test
  void clean() {
    var norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    var proprietary = norm.getRegelungstext1().getMeta().getOrCreateProprietary();

    assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT))
      .contains("Aktuelle Organisationseinheit");

    // When
    confidentialDataCleanupService.clean(norm);

    // Then
    assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
  }
}
