package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class XmlMapperTest {

  @Test
  void itCanConvertTextToXmlDocument() {
    // Given
    var text = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // When
    final Document document = XmlMapper.toDocument(text);

    // Then
    assertThat(
      document
        .getElementsByTagName("akn:act")
        .item(0)
        .getAttributes()
        .getNamedItem("name")
        .getNodeValue()
    ).isEqualTo("regelungstext");
  }

  @Test
  void itCanConvertTextToXmlDocumentAndBack() {
    // Given
    var text = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // When
    final Document document = XmlMapper.toDocument(text);
    final String text2 = XmlMapper.toString(document);
    final Document document2 = XmlMapper.toDocument(text2);

    // Then
    assertThat(document.isEqualNode(document2)).isTrue();
  }

  @Test
  void itCanConvertTextToXmlNode() {
    // given
    var text = "<node><inner-node>Hello</inner-node></node>";

    // when
    var node = XmlMapper.toElement(text);

    // then
    assertThat(node.getNodeName()).isEqualTo("node");
    assertThat(node.getChildNodes().item(0).getTextContent()).isEqualTo("Hello");
  }

  @Test
  void itCanConvertTextToXmlNodeAndBack() {
    // given
    var text = "<node><inner-node>Hello</inner-node></node>";

    // when
    var node1 = XmlMapper.toElement(text);
    var asString = XmlMapper.toString(node1);
    var node2 = XmlMapper.toElement(asString);

    // then
    assertThat(node1.isEqualNode(node2)).isTrue();
  }
}
