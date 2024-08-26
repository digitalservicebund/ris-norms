package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

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
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" refersTo="stammform" />,para
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" refersTo="mantelform" />,art
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" refersTo="eingebundene-stammform" />,para
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" refersTo="geltungszeitregel" />,para
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" refersTo="vertragsgesetz" />,art
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" refersTo="vertragsverordnung" />,art
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" refersTo="hauptaenderung" />,para
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" refersTo="folgeaenderung" />,para
          """)
  void itShouldGetTheCorrectEIdPartTypeForAknElements(String xml, String eIdPartName) {
    var node = XmlMapper.toNode(xml);
    // when
    var partType = EIdPartType.forAknElement(node);
    // then
    assertThat(partType)
        .hasValueSatisfying(part -> assertThat(part.getName()).isEqualTo(eIdPartName));
  }
}
