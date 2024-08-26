package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import org.junit.jupiter.api.Nested;
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
        XmlMapper.toNode(
            "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3\" />");
    // when
    var eId = EId.fromNode(node);
    // then
    assertThat(eId).isPresent();
    assertThat(eId.get().value()).isEqualTo("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3");
  }

  @Test
  void fromMandatoryNode() {
    // given
    var node =
        XmlMapper.toNode(
            "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3\" />");
    // when
    var eId = EId.fromMandatoryNode(node);
    // then
    assertThat(eId.value()).isEqualTo("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3");
  }

  @Test
  void fromMandatoryNodeThrowsMandatoryNodeNotFoundException() {
    // given
    var node =
        XmlMapper.toNode(
            "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId=\"\" />");

    // when/then
    assertThatThrownBy(() -> EId.fromMandatoryNode(node))
        .isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Nested
  class forNode {
    @Test
    void itShouldProvideEIdForNodeWithoutParent() {
      var node =
          XmlMapper.toNode("<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" />");
      // when
      var optionalEId = EId.forNode(node);
      // then
      assertThat(optionalEId)
          .hasValueSatisfying(
              eId -> {
                assertThat(eId.value()).isEqualTo("ändbefehl-1");
              });
    }

    @Test
    void itShouldProvideEIdForNodeWithParent() {
      var node =
          XmlMapper.toNode(
              "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3\"><akn:p>Some text</akn:p></akn:mod>");
      // when
      var optionalEId = EId.forNode(node.getFirstChild());
      // then
      assertThat(optionalEId)
          .hasValueSatisfying(
              eId -> {
                assertThat(eId.value())
                    .isEqualTo("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1");
              });
    }

    @Test
    void itShouldProvideEIdForNodeWithSiblingsWithSameEIdTypeWithoutNestedNum() {
      var node =
          XmlMapper.toNode(
              "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3\"><akn:p eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1\">Some text 1</akn:p><akn:ref eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_ref-1\">Some other element</akn:ref><akn:p eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1\">Some text 2</akn:p><akn:p eId=\"hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-2\">Some text 3</akn:p></akn:mod>");
      // when
      var optionalEId = EId.forNode(node.getChildNodes().item(2));
      // then
      assertThat(optionalEId)
          .hasValueSatisfying(
              eId -> {
                assertThat(eId.value())
                    .isEqualTo("hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-2");
              });
    }

    @Test
    void itShouldProvideEIdForNodeWithNestedNum() {
      var node =
          XmlMapper.toNode(
              "<akn:article xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" refersTo=\"stammform\"><akn:num><akn:marker>3a</akn:marker>§ 3a</akn:num></akn:article>");
      // when
      var optionalEId = EId.forNode(node);
      // then
      assertThat(optionalEId)
          .hasValueSatisfying(
              eId -> {
                assertThat(eId.value()).isEqualTo("para-3a");
              });
    }

    @Test
    void itShouldProvideEIdForLiElementNestedInOl() {
      var node =
          XmlMapper.toNode(
              "<akn:ol xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\"><akn:li value=\"3.\">Some text</akn:li></akn:ol>");
      // when
      var optionalEId = EId.forNode(node.getFirstChild());
      // then
      assertThat(optionalEId)
          .hasValueSatisfying(
              eId -> {
                assertThat(eId.value()).isEqualTo("listenelem-3");
              });
    }
  }
}
