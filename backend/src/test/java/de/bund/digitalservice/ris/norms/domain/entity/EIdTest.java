package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
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

  @Test
  void itShouldHaveAStringRepresentation() {
    // given
    var eid = new EId("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1");
    // when // then
    assertThat(eid).hasToString("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1");
  }

  @Test
  void addPart() {
    // given
    var eid = new EId("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3");
    // when
    var newEid = eid.addPart(new EIdPart("text-1"));
    // then
    assertThat(eid).hasToString("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3");
    assertThat(newEid).hasToString("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1");
  }

  @Test
  void fromNode() {
    // given
    var node =
        XmlMapper.toNode("<akn:mod eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3\" />");
    // when
    var eId = EId.fromNode(node);
    // then
    assertThat(eId).isPresent();
    assertThat(eId.get().value()).isEqualTo("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3");
  }
}
