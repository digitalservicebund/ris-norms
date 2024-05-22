package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

class NodeParserTest {

  @Test
  void testParseNode() {
    Node node = XmlMapper.toDocument("<test>testValue</test>");
    String expression = "/test/text()";
    Optional<String> text = NodeParser.getValueFromExpression(expression, node);
    assertThat(text).isPresent();
    assertThat(text.get()).contains("testValue");
  }

  @Test
  void returnEmptyOptionalWhenNoTextIsFound() {
    Node node = XmlMapper.toDocument("<test/>");
    String expression = "/test/text()";
    Optional<String> text = NodeParser.getValueFromExpression(expression, node);
    assertThat(text).isEmpty();
  }

  @Test
  void returnEmptyListWhenNoNodesFound() {
    Node node = XmlMapper.toDocument("<test>testValue</test>");
    String expression = "/nonExistingNode";
    var nodes = NodeParser.getNodesFromExpression(expression, node);
    assertThat(nodes).isEmpty();
  }

  @Test
  void getNodeList() {
    Node node = XmlMapper.toDocument("<test>testValue</test>");
    String expression = "/test";
    var nodes = NodeParser.getNodesFromExpression(expression, node);
    assertThat(nodes).hasSize(1);
    assertThat(nodes.getFirst().getNodeName()).isEqualTo("test");
  }

  @Test
  void nodeListToList() {
    Node node = XmlMapper.toDocument("<foo><test>testValue</test></foo>").getFirstChild();
    var nodes = NodeParser.nodeListToList(node.getChildNodes());
    assertThat(nodes).hasSize(1);
  }

  @Test
  void nodeListToListMultipleElements() {
    Node node =
        XmlMapper.toDocument("<foo><test>testValue</test><test>testValue2</test></foo>")
            .getFirstChild();
    var nodes = NodeParser.nodeListToList(node.getChildNodes());
    assertThat(nodes).hasSize(2);
  }

  @Test
  void nodeListToListZeroElements() {
    Node node = XmlMapper.toDocument("<foo></foo>").getFirstChild();
    var nodes = NodeParser.nodeListToList(node.getChildNodes());
    assertThat(nodes).isEmpty();
  }
}
