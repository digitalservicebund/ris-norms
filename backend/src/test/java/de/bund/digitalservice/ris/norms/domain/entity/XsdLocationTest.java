package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

class XsdLocationTest {
  final Node normNode =
      XmlMapper.toNode(
          """
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/"
                            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
            </akn:akomaNtoso>
            """);

  final XsdLocation underTest = new XsdLocation(normNode);

  @Test
  void getAknNameSpace() {
    String result = underTest.getAknNameSpace();
    assertThat(result).isEqualTo("http://Inhaltsdaten.LegalDocML.de/1.7/");
  }
}
