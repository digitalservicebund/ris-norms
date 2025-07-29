package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DokumentTest {

  @Test
  void setManifestationDateTo_ShouldSetCorrectMetadata() {
    var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    // given
    LocalDate testDate = LocalDate.of(2222, 2, 22);

    // when
    regelungstext.setManifestationDateTo(testDate);

    // than
    assertThat(regelungstext.getManifestationEli()).isEqualTo(
      DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2222-02-22/regelungstext-verkuendung-1.xml"
      )
    );
  }

  @Test
  void setUebergreifendeGuid_ShouldSetGuidInFrbrWork() {
    // given
    var dokument = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    UUID guid = UUID.randomUUID();

    // when
    dokument.setUebergreifendeGuid(guid);

    // then
    assertThat(
      NodeParser.getValueFromMandatoryNodeFromExpression(
        "./FRBRalias[@name='übergreifende-id']/@value",
        dokument.getMeta().getFRBRWork().getElement()
      )
    ).isEqualTo(guid.toString());
  }
}
