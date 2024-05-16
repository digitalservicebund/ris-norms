package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EIdTest {

  @Test
  void getParts() {
    // given
    var eid = new EId("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1");
    // when
    var parts = eid.getParts();
    // then
    assertThat(parts)
        .hasSize(6)
        .contains(new EIdPart("hauptteil-1"))
        .contains(new EIdPart("text-1"));
  }
}
