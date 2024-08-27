package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EIdPartTypeTest {

  @ParameterizedTest
  @CsvSource(
      """
          <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" />,ändbefehl
          <akn:list xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" />,untergl
          <akn:li xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" />,listenelem
          <akn:point xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" />,listenelem
          <akn:passiveModifications xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" />,pasmod
          """)
  void itShouldGetTheCorrectEIdPartTypeForAknElements(String xml, String eIdPartName) {
    var node = XmlMapper.toNode(xml);
    // when
    var partType = EIdPartType.forAknElement(node);
    // then
    assertThat(partType)
        .hasValueSatisfying(part -> assertThat(part.getName()).isEqualTo(eIdPartName));
  }

  @ParameterizedTest
  @CsvSource(
      """
          stammform,para
          mantelform,para
          eingebundene-stammform,para
          geltungszeitregel,para
          vertragsgesetz,art
          vertragsverordnung,art
          hauptaenderung,para
          folgeaenderung,para
          """)
  void itShouldGetTheCorrectEIdPartTypeForArticleInQuotedStructure(
      String refersTo, String eIdPartName) {
    var node =
        XmlMapper.toNode(
            """
        <akn:quotedStructure xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"><akn:article refersTo="%s" /></akn:quotedStructure>
        """
                .formatted(refersTo));
    // when
    var partType = EIdPartType.forAknElement(node.getFirstChild());
    // then
    assertThat(partType)
        .hasValueSatisfying(part -> assertThat(part.getName()).isEqualTo(eIdPartName));
  }

  @ParameterizedTest
  @CsvSource(
      """
          stammform,stammform,para
          stammform,mantelform,para
          stammform,eingebundene-stammform,para
          stammform,geltungszeitregel,para
          stammform,vertragsgesetz,art
          stammform,vertragsverordnung,art
          stammform,hauptaenderung,para
          stammform,folgeaenderung,para
          eingebundene-stammform,stammform,para
          eingebundene-stammform,mantelform,para
          eingebundene-stammform,eingebundene-stammform,para
          eingebundene-stammform,geltungszeitregel,para
          eingebundene-stammform,vertragsgesetz,para
          eingebundene-stammform,vertragsverordnung,para
          eingebundene-stammform,hauptaenderung,para
          eingebundene-stammform,folgeaenderung,para
          mantelform,stammform,art
          mantelform,mantelform,art
          mantelform,eingebundene-stammform,art
          mantelform,geltungszeitregel,art
          mantelform,vertragsgesetz,art
          mantelform,vertragsverordnung,art
          mantelform,hauptaenderung,art
          mantelform,folgeaenderung,art
          """)
  void itShouldGetTheCorrectEIdPartTypeForArticleOutsideQuotedStructure(
      String form, String refersTo, String eIdPartName) {
    var node =
        XmlMapper.toNode(
            """
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/">
            <akn:act name="regelungstext">
              <akn:meta eId="meta-1" GUID="7e5837c8-b967-45be-924b-c95956c4aa94">
                <akn:proprietary eId="meta-1_proprietary-1" GUID="fe419055-3201-41b1-b096-402eabcbe6a1" source="attributsemantik-noch-undefiniert">
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                    <meta:form>%s</meta:form>
                  </meta:legalDocML.de_metadaten>
                </akn:proprietary>
              </akn:meta>
              <akn:article refersTo="%s" />
            </akn:act>
          </akn:akomaNtoso>
        """
                .formatted(form, refersTo));
    // when
    var partType =
        EIdPartType.forAknElement(NodeParser.getMandatoryNodeFromExpression("//*/article", node));
    // then
    assertThat(partType)
        .hasValueSatisfying(part -> assertThat(part.getName()).isEqualTo(eIdPartName));
  }
}
