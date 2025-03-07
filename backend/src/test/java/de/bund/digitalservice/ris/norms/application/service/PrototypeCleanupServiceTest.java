package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import org.junit.jupiter.api.Test;

class PrototypeCleanupServiceTest {

  private final PrototypeCleanupService underTest = new PrototypeCleanupService();

  @Test
  void cleanRisMetadata() {
    final Norm norm = Fixtures.loadNormFromDisk("NormWithProprietaryToBeCleaned.xml");

    underTest.clean(norm);
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.SUBTYP)
    )
      .isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)
    )
      .isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.ART_DER_NORM)
    )
      .isEmpty();
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.NORMGEBER)
    )
      .isEmpty();
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.SUBTYP)
    )
      .isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)
    )
      .isEmpty();
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.NORMGEBER)
    )
      .isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)
    )
      .isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)
    )
      .isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.ENTRY_INTO_FORCE_DATE)
    )
      .contains("2008-07-01");
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.EXPIRY_DATE)
    )
      .contains("2011-12-31");
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.STANDANGABE)
    )
      .contains(
        "V aufgeh. durch Art. 22 G v. 22.12.2011 I 1111 mWv\n                       1.1.2012"
      );
  }
}
