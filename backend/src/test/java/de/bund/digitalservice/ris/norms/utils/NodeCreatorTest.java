package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.EIdPart;
import de.bund.digitalservice.ris.norms.domain.entity.Namespace;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class NodeCreatorTest {

  @Test
  void createElement() {
    // given
    final Document document = XmlMapper.toDocument(
      """
      <root>
          <test>test value</test>
      </root>"""
    );
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//test", document);

    // when
    final Element newElement = NodeCreator.createElement("childTest", testNode);

    // Then
    final Node childTestNode = NodeParser.getMandatoryNodeFromExpression(
      "//test/childTest",
      document
    );
    assertThat(childTestNode).isEqualTo(newElement);
  }

  @Test
  void createElement_withNamespace() {
    // given
    final Document document = XmlMapper.toDocument(
      """
      <root>
          <test>test value</test>
      </root>"""
    );
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//test", document);

    // when
    final Element newElement = NodeCreator.createElement(
      Namespace.METADATEN_RIS,
      "childTest",
      testNode
    );

    // Then
    final Node childTestNode = NodeParser.getMandatoryNodeFromExpression(
      "//test/Q{http://MetadatenRIS.LegalDocML.de/1.7.1/}childTest",
      document
    );
    assertThat(childTestNode).isEqualTo(newElement);
    assertThat(childTestNode.getNodeName()).isEqualTo("ris:childTest");
  }

  @Test
  void createElementWithEidAndGuid() {
    // given
    final Document document = XmlMapper.toDocument(
      """
                                    <root>
      <akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="text-1">test value</akn:p>
                                    </root>"""
    );
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//*/p", document);

    // when
    final Element newElement = NodeCreator.createElementWithEidAndGuid("akn:ref", testNode);

    // Then
    final Node childTestNode = NodeParser.getMandatoryNodeFromExpression("//p/ref", document);
    assertThat(childTestNode).isEqualTo(newElement);
    assertThat(childTestNode.getAttributes().getNamedItem("GUID")).isNotNull();
    assertThat(childTestNode.getAttributes().getNamedItem("eId").getNodeValue())
      .isEqualTo("text-1_ref-1");
  }

  @Test
  void createElementWithStaticEidAndGuidNoAppend() {
    // given
    final Document document = XmlMapper.toDocument(
      """
      <root>
          <test eId="test-1">test value</test>
      </root>"""
    );
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//test", document);

    // when
    final Element newElement = NodeCreator.createElementWithStaticEidAndGuidNoAppend(
      "childTest",
      new EIdPart("child-test", "1"),
      testNode
    );

    // Then
    final Optional<Node> childTestNode = NodeParser.getNodeFromExpression(
      "//test/childTest",
      document
    );
    assertThat(childTestNode).isEmpty();
    assertThat(newElement.getAttributes().getNamedItem("GUID")).isNotNull();
    assertThat(newElement.getAttributes().getNamedItem("eId").getNodeValue())
      .isEqualTo("test-1_child-test-1");
  }
}
