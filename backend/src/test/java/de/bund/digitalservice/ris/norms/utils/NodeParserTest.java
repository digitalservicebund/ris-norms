package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
  void returnEmptyNodeListWhenNoNodesFound() {
    Node node = XmlMapper.toDocument("<test>testValue</test>");
    String expression = "/nonExistingNode";
    NodeList nodes = NodeParser.getNodesFromExpression(expression, node);
    assertThat(nodes).isNotNull().isInstanceOf(NodeList.class);
    assertThat(nodes.getLength()).isZero();
  }

  @Test
  void getNodeList() {
    Node node = XmlMapper.toDocument("<test>testValue</test>");
    String expression = "/test";
    NodeList nodes = NodeParser.getNodesFromExpression(expression, node);
    assertThat(nodes).isNotNull().isInstanceOf(NodeList.class);
    assertThat(nodes.getLength()).isEqualTo(1);
    assertThat(nodes.item(0).getNodeName()).isEqualTo("test");
  }
}
