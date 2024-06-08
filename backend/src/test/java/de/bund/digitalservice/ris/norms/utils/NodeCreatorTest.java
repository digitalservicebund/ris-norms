package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class NodeCreatorTest {

  @Test
  void createElement() {
    // given
    final Document document =
        XmlMapper.toDocument(
            """
                                                          <root>
                                                              <test>test value</test>
                                                          </root>""");
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//test", document);

    // when
    final Element newElement = NodeCreator.createElement("childTest", testNode);

    // Then
    final Node childTestNode =
        NodeParser.getMandatoryNodeFromExpression("//test/childTest", document);
    assertThat(childTestNode).isEqualTo(newElement);
  }

  @Test
  void createElementWithEidAndGuid() {
    // given
    final Document document =
        XmlMapper.toDocument(
            """
                                                          <root>
                                                              <test eId="test-1">test value</test>
                                                          </root>""");
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//test", document);

    // when
    final Element newElement =
        NodeCreator.createElementWithEidAndGuid("childTest", "child-test", testNode);

    // Then
    final Node childTestNode =
        NodeParser.getMandatoryNodeFromExpression("//test/childTest", document);
    assertThat(childTestNode).isEqualTo(newElement);
    assertThat(childTestNode.getAttributes().getNamedItem("GUID")).isNotNull();
    assertThat(childTestNode.getAttributes().getNamedItem("eId").getNodeValue())
        .isEqualTo("test-1_child-test-1");
  }

  @Test
  void calculateNextPossibleEid() {
    // given
    final Document document =
        XmlMapper.toDocument(
            """
                                                          <root>
                                                              <test eId="test-1">test value</test>
                                                          </root>""");
    final Node testNode = NodeParser.getMandatoryNodeFromExpression("//test", document);

    // when
    final String nextPossibleEid = NodeCreator.calculateNextPossibleEid(testNode, "child-test");

    // Then
    assertThat(nextPossibleEid).isEqualTo("test-1_child-test-1");
  }
}
