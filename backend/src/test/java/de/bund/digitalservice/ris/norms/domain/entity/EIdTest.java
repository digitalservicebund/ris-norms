package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EIdTest {

  @Test
  void getParts() {
    // given
    var eid = new EId("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1");
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
    var eid = new EId("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1");
    // when // then
    assertThat(eid).hasToString("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1");
  }

  @Nested
  class getParent {

    @Test
    void itShouldBeEmptyForEIdOfOnePart() {
      // given
      var eid = new EId("hauptteil-1");
      // when // then
      assertThat(eid.getParent()).isEmpty();
    }

    @Test
    void itShouldReturnTheParentEId() {
      // given
      var eid = new EId("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1");
      // when // then
      assertThat(eid.getParent()).contains(
        new EId("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3")
      );
    }
  }

  @Test
  void addPart() {
    // given
    var eid = new EId("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3");
    // when
    var newEid = eid.addPart(new EIdPart("text-1"));
    // then
    assertThat(eid).hasToString("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3");
    assertThat(newEid).hasToString("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1");
  }

  @Test
  void fromNode() {
    // given
    var node = XmlMapper.toElement(
      "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\" eId=\"hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3\" />"
    );
    // when
    var eId = EId.fromNode(node);
    // then
    assertThat(eId).isPresent();
    assertThat(eId.get().value()).isEqualTo("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3");
  }

  @Test
  void fromMandatoryNode() {
    // given
    var node = XmlMapper.toElement(
      "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\" eId=\"hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3\" />"
    );
    // when
    var eId = EId.fromMandatoryNode(node);
    // then
    assertThat(eId.value()).isEqualTo("hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3");
  }

  @Test
  void fromMandatoryNodeThrowsMandatoryNodeNotFoundException() {
    // given
    var node = XmlMapper.toElement(
      "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\" eId=\"\" />"
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
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\" />,ändbefehl-1",
        "<akn:p xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\"><akn:num><akn:marker>3a</akn:marker>§ 3a</akn:num></akn:p>,text-1",
        "<akn:li value=\"3.\" xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\">Some text</akn:li>,listenelem-1",
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
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\" eId=\"hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3\"><akn:p>Some text</akn:p></akn:mod>"
      );
      // when
      var optionalEId = EId.forNode(node.getFirstChild());
      // then
      assertThat(optionalEId).hasValueSatisfying(eId -> {
        assertThat(eId.value()).isEqualTo(
          "hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1"
        );
      });
    }

    @Test
    void itShouldProvideEIdForNodeWithSiblingsWithSameEIdTypeWithoutNestedNum() {
      var node = XmlMapper.toElement(
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\" eId=\"hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3\"><akn:p eId=\"hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1\">Some text 1</akn:p><akn:ref eId=\"hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_ref-1\">Some other element</akn:ref><akn:p eId=\"hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1\">Some text 2</akn:p><akn:p eId=\"hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-2\">Some text 3</akn:p></akn:mod>"
      );
      // when
      var optionalEId = EId.forNode(node.getChildNodes().item(2));
      // then
      assertThat(optionalEId).hasValueSatisfying(eId -> {
        assertThat(eId.value()).isEqualTo(
          "hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-2"
        );
      });
    }
  }
}
