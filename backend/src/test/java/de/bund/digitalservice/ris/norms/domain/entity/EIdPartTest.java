package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EIdPartTest {

  @Test
  void getType() {
    // given
    EIdPart eIdPart = new EIdPart("text-1");
    // when
    var type = eIdPart.getType();
    // then
    assertThat(type).isEqualTo("text");
  }

  @Test
  void getPosition() {
    // given
    EIdPart eIdPart = new EIdPart("text-1");
    // when
    var type = eIdPart.getPosition();
    // then
    assertThat(type).isEqualTo("1");
  }
}
