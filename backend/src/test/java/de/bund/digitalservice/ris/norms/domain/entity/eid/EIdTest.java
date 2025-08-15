package de.bund.digitalservice.ris.norms.domain.entity.eid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Element;

class EIdTest {

  @Test
  void getParts() {
    // given
    var eid = new EId("hauptteil-n1_abschnitt-n1_art-z1_abs-z3_inhalt-n3_text-n1");
    // when
    var parts = eid.getParts();
    // then
    assertThat(parts)
      .hasSize(6)
      .contains(new EIdPart("hauptteil-n1"))
      .contains(new EIdPart("art-z1"))
      .contains(new EIdPart("text-n1"));
  }

  @Test
  void itShouldHaveAStringRepresentation() {
    // given
    var eid = new EId("hauptteil-n1_abschnitt-n1_art-z6_abs-z3_inhalt-n3_text-n1");
    // when // then
    assertThat(eid).hasToString("hauptteil-n1_abschnitt-n1_art-z6_abs-z3_inhalt-n3_text-n1");
  }

  @Nested
  class getParent {

    @Test
    void itShouldBeEmptyForEIdOfOnePart() {
      // given
      var eid = new EId("hauptteil-n1");
      // when // then
      assertThat(eid.getParent()).isEmpty();
    }

    @Test
    void itShouldReturnTheParentEId() {
      // given
      var eid = new EId("hauptteil-n1_abschnitt-n1_art-z6_abs-z3_inhalt-n3_text-n1");
      // when // then
      assertThat(eid.getParent()).contains(
        new EId("hauptteil-n1_abschnitt-n1_art-z6_abs-z3_inhalt-n3")
      );
    }
  }

  @Test
  void addPart() {
    // given
    var eid = new EId("hauptteil-n1_abschnitt-n1_art-z6_abs-z3_inhalt-n3");
    // when
    var newEid = eid.addPart(new EIdPart("text-n1"));
    // then
    assertThat(eid).hasToString("hauptteil-n1_abschnitt-n1_art-z6_abs-z3_inhalt-n3");
    assertThat(newEid).hasToString("hauptteil-n1_abschnitt-n1_art-z6_abs-z3_inhalt-n3_text-n1");
  }

  @Test
  void fromNode() {
    // given
    var node = XmlMapper.toElement(
      "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.2/\" eId=\"art-z6_abs-z3_inhalt-n3\" />"
    );
    // when
    var eId = EId.fromNode(node);
    // then
    assertThat(eId).isPresent();
    assertThat(eId.get().value()).isEqualTo("art-z6_abs-z3_inhalt-n3");
  }

  @Test
  void fromMandatoryNode() {
    // given
    var node = XmlMapper.toElement(
      "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.2/\" eId=\"art-z6_abs-z3_inhalt-n3\" />"
    );
    // when
    var eId = EId.fromMandatoryNode(node);
    // then
    assertThat(eId.value()).isEqualTo("art-z6_abs-z3_inhalt-n3");
  }

  @Test
  void fromMandatoryNodeThrowsMandatoryNodeNotFoundException() {
    // given
    var node = XmlMapper.toElement(
      "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.2/\" eId=\"\" />"
    );

    // when/then
    assertThatThrownBy(() -> EId.fromMandatoryNode(node)).isInstanceOf(
      MandatoryNodeNotFoundException.class
    );
  }

  @Nested
  class forNode {

    @ParameterizedTest
    @CsvSource(
      {
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.2/\" />,ändbefehl-n1",
        "<akn:p xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.2/\"><akn:num><akn:marker>3a</akn:marker>§ 3a</akn:num></akn:p>,text-n1",
        "<akn:li value=\"3.\" xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.2/\">Some text</akn:li>,listenelem-n1",
      }
    )
    void itShouldProvideEIdForNode(String xml, String expectedEId) {
      var node = XmlMapper.toElement(xml);
      // when
      var optionalEId = EId.forNode(node);
      // then
      assertThat(optionalEId).hasValueSatisfying(eId -> {
          assertThat(eId.value()).isEqualTo(expectedEId);
        });
    }

    @Test
    void itShouldProvideEIdForNodeWithParent() {
      var node = XmlMapper.toElement(
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.2/\" eId=\"art-z6_abs-z3_inhalt-n3\"><akn:p>Some text</akn:p></akn:mod>"
      );
      // when
      var optionalEId = EId.forNode((Element) node.getFirstChild());
      // then
      assertThat(optionalEId).hasValueSatisfying(eId -> {
          assertThat(eId.value()).isEqualTo("art-z6_abs-z3_inhalt-n3_text-n1");
        });
    }

    @Test
    void itShouldProvideEIdForNodeWithSiblingsWithSameEIdTypeWithoutNestedNum() {
      var node = XmlMapper.toElement(
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.2/\" eId=\"art-z6_abs-z3_inhalt-n3\"><akn:p eId=\"art-n6_abs-n3_inhalt-z3_text-n1\">Some text 1</akn:p><akn:ref eId=\"art-z6_abs-z3_inhalt-n3_ref-n1\">Some other element</akn:ref><akn:p eId=\"art-z6_abs-z3_inhalt-n3_text-n1\">Some text 2</akn:p><akn:p eId=\"art-z6_abs-z3_inhalt-n3_text-n2\">Some text 3</akn:p></akn:mod>"
      );
      // when
      var optionalEId = EId.forNode((Element) node.getChildNodes().item(2));
      // then
      assertThat(optionalEId).hasValueSatisfying(eId -> {
          assertThat(eId.value()).isEqualTo("art-z6_abs-z3_inhalt-n3_text-n2");
        });
    }
  }
}
