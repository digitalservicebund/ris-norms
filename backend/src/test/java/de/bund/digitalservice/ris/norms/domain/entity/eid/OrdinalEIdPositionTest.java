package de.bund.digitalservice.ris.norms.domain.entity.eid;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

class OrdinalEIdPositionTest {

  @Nested
  class findEIdPosition {

    @Test
    void itShouldProvidePositionForNodeWithSiblingsWithSameEIdTypeWithoutNestedNum() {
      var node = XmlMapper.toElement(
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8/\" eId=\"art-z6_abs-z3_inhalt-n3\"><akn:p eId=\"art-n6_abs-n3_inhalt-z3_text-n1\">Some text 1</akn:p><akn:ref eId=\"art-z6_abs-z3_inhalt-n3_ref-n1\">Some other element</akn:ref><akn:p eId=\"art-z6_abs-z3_inhalt-n3_text-n1\">Some text 2</akn:p><akn:p eId=\"art-z6_abs-z3_inhalt-n3_text-n2\">Some text 3</akn:p></akn:mod>"
      );
      // when
      var eIdPosition = OrdinalEIdPosition.findEIdPosition(
        node.getChildNodes().item(2),
        EIdPartType.TEXT
      );
      // then
      assertThat(eIdPosition).hasToString("n2");
    }

    @Test
    void itShouldProvidePositionForNodeWithoutSiblings() {
      var node = XmlMapper.toElement(
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8/\" />"
      );
      // when
      var eIdPosition = OrdinalEIdPosition.findEIdPosition(node, EIdPartType.AENDBEFEHL);
      // then
      assertThat(eIdPosition).hasToString("n1");
    }

    @Test
    void itShouldProvidePositionForArtikel() {
      // given
      var node = XmlMapper.toElement(
        """
        <akn:book xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/" eId="buch-n1">
          <akn:section eId="buch-n1_abschnitt-n1">
            <akn:article eId="art-z1"><akn:num>1</akn:num>
              <akn:quotedStructure>
                <akn:article/>
              </akn:quotedStructure>
            </akn:article>
            <akn:article eId="art-n2"><akn:num refersTo="ordinale-zaehlung-eid"/></akn:article>
            <akn:article eId="art-z2"><akn:num>2</akn:num></akn:article>
            <akn:article eId="art-n4"><akn:num refersTo="ordinale-zaehlung-eid"/></akn:article>
          </akn:section>
          <akn:section eId="buch-n1_abschnitt-n2">
            <akn:article eId="art-z5"><akn:num>5</akn:num></akn:article>
            <akn:article eId="art-n6"><akn:num refersTo="ordinale-zaehlung-eid"/></akn:article>
          </akn:section>
        </akn:book>
        """.strip()
      );

      var section1 = (Element) node.getChildNodes().item(1);
      assertThat(section1.getAttribute("eId")).hasToString("buch-n1_abschnitt-n1");
      var section1Article2 = (Element) section1.getChildNodes().item(3);
      assertThat(section1Article2.getAttribute("eId")).hasToString("art-n2");
      var section1Article4 = (Element) section1.getChildNodes().item(7);
      assertThat(section1Article4.getAttribute("eId")).hasToString("art-n4");
      var section2 = (Element) node.getChildNodes().item(3);
      assertThat(section2.getAttribute("eId")).hasToString("buch-n1_abschnitt-n2");
      var section2Article2 = (Element) section2.getChildNodes().item(3);
      assertThat(section2Article2.getAttribute("eId")).hasToString("art-n6");

      // when // then
      assertThat(OrdinalEIdPosition.findEIdPosition(section1Article2, EIdPartType.ART)).hasToString(
        "n2"
      );
      assertThat(OrdinalEIdPosition.findEIdPosition(section1Article4, EIdPartType.ART)).hasToString(
        "n4"
      );
      assertThat(OrdinalEIdPosition.findEIdPosition(section2Article2, EIdPartType.ART)).hasToString(
        "n6"
      );
    }

    @Test
    void itShouldProvidePositionForArtikelInQuotedStructure() {
      // given
      var node = XmlMapper.toElement(
        """
        <akn:book xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/" eId="buch-n1">
          <akn:section eId="buch-n1_abschnitt-n1">
            <akn:article eId="art-z5"><akn:num>5</akn:num></akn:article>
            <akn:article eId="art-n2"><akn:num refersTo="ordinale-zaehlung-eid"/></akn:article>
          </akn:section>
          <akn:quotedStructure eId="buch-n1_quot-n1">
            <akn:article eId="buch-n1_quot-n1_art-z1"><akn:num>1</akn:num></akn:article>
            <akn:article eId="buch-n1_quot-n1_art-n2"><akn:num refersTo="ordinale-zaehlung-eid"/></akn:article>
            <akn:article eId="buch-n1_quot-n1_art-z2"><akn:num>2</akn:num></akn:article>
            <akn:article eId="buch-n1_quot-n1_art-n4"><akn:num refersTo="ordinale-zaehlung-eid"/></akn:article>
          </akn:quotedStructure>
        </akn:book>
        """.strip()
      );

      var quotedStructure = (Element) node.getChildNodes().item(3);
      assertThat(quotedStructure.getAttribute("eId")).hasToString("buch-n1_quot-n1");
      var article2 = (Element) quotedStructure.getChildNodes().item(3);
      assertThat(article2.getAttribute("eId")).hasToString("buch-n1_quot-n1_art-n2");
      var article4 = (Element) quotedStructure.getChildNodes().item(7);
      assertThat(article4.getAttribute("eId")).hasToString("buch-n1_quot-n1_art-n4");

      // when // then
      assertThat(OrdinalEIdPosition.findEIdPosition(article2, EIdPartType.ART)).hasToString("n2");
      assertThat(OrdinalEIdPosition.findEIdPosition(article4, EIdPartType.ART)).hasToString("n4");
    }
  }
}
