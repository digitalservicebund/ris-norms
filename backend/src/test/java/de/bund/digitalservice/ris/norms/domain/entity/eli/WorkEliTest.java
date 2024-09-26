package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class WorkEliTest {

  @Test
  void fromString() {
    var eli = WorkEli.fromString("eli/bund/bgbl-1/2021/s4/regelungstext-1");
    assertThat(eli.getAgent()).isEqualTo("bgbl-1");
    assertThat(eli.getYear()).isEqualTo("2021");
    assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
    assertThat(eli.getSubtype()).isEqualTo("regelungstext-1");
  }

  @Test
  void testToString() {
    var eli = WorkEli.fromString("eli/bund/bgbl-1/2021/s4/regelungstext-1");
    assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4/regelungstext-1");
  }

  @Test
  void toUri() {
    var eli = WorkEli.fromString("eli/bund/bgbl-1/2021/s4/regelungstext-1");
    assertThat(eli.toUri()).hasToString("eli/bund/bgbl-1/2021/s4");
  }
}
