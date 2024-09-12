package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
  void returnNodeWhenFound() {
    Node node = XmlMapper.toDocument("<test>testValue</test>");
    String expression = "/test";
    Optional<Node> result = NodeParser.getNodeFromExpression(expression, node);
    assertThat(result).isPresent();
    assertThat(result.get().getTextContent()).isEqualTo("testValue");
  }

  @Test
  void returnEmptyOptionalWhenNoNodeIsFound() {
    Node node = XmlMapper.toDocument("<test>testValue</test>");
    String expression = "/bla";
    Optional<Node> result = NodeParser.getNodeFromExpression(expression, node);
    assertThat(result).isEmpty();
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

  @ParameterizedTest
  @CsvSource(
      """
      //act/@name
      //*:act/@name
      //Q{http://Inhaltsdaten.LegalDocML.de/1.6/}act/@name
      """)
  void xPathsWorkWithoutNamespaceAwareness(String xPath) {
    var norm = NormFixtures.loadFromDisk("NormWithMods.xml", false);
    Node node = norm.getDocument();
    Optional<String> result = NodeParser.getValueFromExpression(xPath, node);
    assertThat(result).contains("regelungstext");
  }

  @ParameterizedTest
  @CsvSource(
      """
      //act/@name
      //*:act/@name
      //Q{http://Inhaltsdaten.LegalDocML.de/1.6/}act/@name
      """)
  void xPathsWorkWithNamespaceAwareness(String xPath) {
    var norm = NormFixtures.loadFromDisk("NormWithMods.xml", true);
    Node node = norm.getDocument();
    Optional<String> result = NodeParser.getValueFromExpression(xPath, node);
    assertThat(result).contains("regelungstext");
  }

  @Test
  void xPathsWorkWithNamespaceDoesNotMatchInDocumentWithDifferentNamespace() {
    var norm = NormFixtures.loadFromDisk("NormWithMods.xml", true);
    Node node = norm.getDocument();
    Optional<String> result =
        NodeParser.getValueFromExpression(
            "//Q{http://Inhaltsdaten.LegalDocML.de/1.5/}act/@name", node);
    assertThat(result).isEmpty();
  }
}
