package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Namespace;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EIdPart;
import de.bund.digitalservice.ris.norms.domain.entity.eid.OrdinalEIdPosition;
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
      "//test/Q{http://MetadatenRIS.LegalDocML.de/1.7.2/}childTest",
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
      <akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="text-n1">test value</akn:p>
                                    </root>"""
    );
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//*/p", document);

    // when
    final Element newElement = NodeCreator.createElementWithEidAndGuid("akn:ref", testNode);

    // Then
    final Element childTestNode = NodeParser.getMandatoryElementFromExpression("//p/ref", document);
    assertThat(childTestNode).isEqualTo(newElement);
    assertThat(childTestNode.getAttribute("GUID")).isNotEmpty();
    assertThat(childTestNode.getAttribute("eId")).isEqualTo("text-n1_ref-n1");
  }

  @Test
  void createElementWithStaticEidAndGuidNoAppend() {
    // given
    final Document document = XmlMapper.toDocument(
      """
      <root>
          <test eId="test-n1">test value</test>
      </root>"""
    );
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//test", document);

    // when
    final Element newElement = NodeCreator.createElementWithStaticEidAndGuidNoAppend(
      "childTest",
      new EIdPart("child-test", new OrdinalEIdPosition(1)),
      testNode
    );

    // Then
    final Optional<Node> childTestNode = NodeParser.getNodeFromExpression(
      "//test/childTest",
      document
    );
    assertThat(childTestNode).isEmpty();
    assertThat(newElement.getAttribute("GUID")).isNotEmpty();
    assertThat(newElement.getAttribute("eId")).isEqualTo("test-n1_child-test-n1");
  }
}
