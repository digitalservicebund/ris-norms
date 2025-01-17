package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import org.junit.jupiter.api.Test;

class RegelungstextTest {

  @Test
  void getWorkEli() {
    // given
    Regelungstext regelungstext = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");

    // when
    var actualEli = regelungstext.getWorkEli();

    // then
    assertThat(actualEli)
      .isEqualTo(DokumentWorkEli.fromString("eli/bund/bgbl-1/1964/s593/regelungstext-1"));
  }
}
