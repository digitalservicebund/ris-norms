package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NormWorkEliTest {

  @Test
  void fromString() {
    var eli = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4");
    assertThat(eli.getAgent()).isEqualTo("bgbl-1");
    assertThat(eli.getYear()).isEqualTo("2021");
    assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
  }

  @Test
  void testToString() {
    var eli = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4");
    assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4");
  }

  @Test
  void toUri() {
    var eli = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4");
    assertThat(eli.toUri()).hasToString("eli/bund/bgbl-1/2021/s4");
  }
}
