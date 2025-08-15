package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import org.junit.jupiter.api.Test;

class PretextCleanupServiceTest {

  PretextCleanupService pretextCleanupService = new PretextCleanupService();

  @Test
  void cleanRegelungstext() {
    var norm = Fixtures.loadNormFromDisk(
      PretextCleanupServiceTest.class,
      "norm-for-cleaning-metadata"
    );
    var proprietary = norm.getRegelungstext1().getMeta().getOrCreateProprietary();

    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RECHTSETZUNGSDOKUMENT)).isEmpty();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_REGELUNGSTEXT)).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_BUNDESREGIERUNG)).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isPresent();

    // When
    pretextCleanupService.clean(norm);

    // Then
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RECHTSETZUNGSDOKUMENT)).isEmpty();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_REGELUNGSTEXT)).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_BUNDESREGIERUNG)).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isEmpty();
  }

  @Test
  void cleanRechtsetzungsdokument() {
    var norm = Fixtures.loadNormFromDisk(
      PretextCleanupServiceTest.class,
      "norm-for-cleaning-metadata"
    );
    var proprietary = norm.getRechtsetzungsdokument().getMeta().getOrCreateProprietary();

    assertThat(
      proprietary.getMetadataParent(Namespace.METADATEN_RECHTSETZUNGSDOKUMENT)
    ).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_REGELUNGSTEXT)).isEmpty();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_BUNDESREGIERUNG)).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isPresent();

    // When
    pretextCleanupService.clean(norm);

    // Then
    assertThat(
      proprietary.getMetadataParent(Namespace.METADATEN_RECHTSETZUNGSDOKUMENT)
    ).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_REGELUNGSTEXT)).isEmpty();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_BUNDESREGIERUNG)).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isEmpty();
  }

  // An anlage-text should not have any metadata as stated in LDMl.de 1.8.2 "2.2.2 Anlage zu einem Regelungstext", but just in case testing also
  @Test
  void cleanAnlageText() {
    var norm = Fixtures.loadNormFromDisk(
      PretextCleanupServiceTest.class,
      "norm-for-cleaning-metadata"
    );
    var proprietary = norm
      .getOffenestrukturen()
      .stream()
      .findFirst()
      .get()
      .getMeta()
      .getOrCreateProprietary();

    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RECHTSETZUNGSDOKUMENT)).isEmpty();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_REGELUNGSTEXT)).isEmpty();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_BUNDESREGIERUNG)).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isPresent();

    // When
    pretextCleanupService.clean(norm);

    // Then
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RECHTSETZUNGSDOKUMENT)).isEmpty();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_REGELUNGSTEXT)).isEmpty();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_BUNDESREGIERUNG)).isPresent();
    assertThat(proprietary.getMetadataParent(Namespace.METADATEN_RIS)).isEmpty();
  }
}
