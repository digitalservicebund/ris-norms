package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DokumentWorkEliTest {

  @Test
  void asNormEli() {
    var eli = new DokumentWorkEli("bgbl-1", "2021", "s4", "regelungstext-verkuendung-1");

    assertThat(eli.asNormEli()).hasToString("eli/bund/bgbl-1/2021/s4");
  }
}
