package de.bund.digitalservice.ris.norms.domain.entity.eid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EIdPartTest {

  @Test
  void getType() {
    // given
    EIdPart eIdPart = new EIdPart("text-n1");
    // when
    var type = eIdPart.getType();
    // then
    assertThat(type).isEqualTo("text");
  }

  @Test
  void getPositionOrdinal() {
    // given
    EIdPart eIdPart = new EIdPart("text-n1");
    // when
    var type = eIdPart.getPosition();
    // then
    assertThat(type).isEqualTo(new OrdinalEIdPosition(1));
  }

  @Test
  void getPositionInvalid() {
    // given
    EIdPart eIdPart = new EIdPart("text-1");
    // when // then
    assertThatThrownBy(eIdPart::getPosition).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void getPositionZaehlbezeichnungsbasiert() {
    // given
    EIdPart eIdPart = new EIdPart("text-z1");
    // when
    var type = eIdPart.getPosition();
    // then
    assertThat(type).isEqualTo(new ZaehlbezeichnungsbasierteEIdPosition("1"));
  }

  @Test
  void itShouldHaveAStringRepresentation() {
    // given
    EIdPart eIdPart = new EIdPart("text-n1");
    // when // then
    assertThat(eIdPart).hasToString("text-n1");
  }

  @Test
  void itShouldCreateNewEidPart() {
    // given // when
    EIdPart eIdPart = new EIdPart("text", new ZaehlbezeichnungsbasierteEIdPosition("1"));
    // then
    assertThat(eIdPart.getPosition()).isEqualTo(new ZaehlbezeichnungsbasierteEIdPosition("1"));
    assertThat(eIdPart.getType()).isEqualTo("text");
    assertThat(eIdPart).hasToString("text-z1");
  }

  @Test
  void itShouldCreateNewEidPartOrdinalPosition() {
    // given // when
    EIdPart eIdPart = new EIdPart("text", new OrdinalEIdPosition(2));
    // then
    assertThat(eIdPart.getPosition()).isEqualTo(new OrdinalEIdPosition(2));
    assertThat(eIdPart.getType()).isEqualTo("text");
    assertThat(eIdPart).hasToString("text-n2");
  }
}
