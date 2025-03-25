package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class NodeParserTest {

  @Nested
  class getValueFromExpression {

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
  }

  @Nested
  class getNodeFromExpression {

    @Test
    void returnNodeWhenFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/test";
      Optional<Node> result = NodeParser.getNodeFromExpression(expression, node);
      assertThat(result).isPresent();
      assertThat(result.get().getTextContent()).isEqualTo("testValue");
    }

    @Test
    void returnTextNodeWhenFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/test/text()";
      Optional<Node> result = NodeParser.getNodeFromExpression(expression, node);
      assertThat(result).isPresent();
      assertThat(result.get().getNodeValue()).isEqualTo("testValue");
    }

    @Test
    void returnEmptyOptionalWhenNoNodeIsFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/bla";
      Optional<Node> result = NodeParser.getNodeFromExpression(expression, node);
      assertThat(result).isEmpty();
    }
  }

  @Nested
  class getElementFromExpression {

    @Test
    void returnElementWhenFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/test";
      Optional<Element> result = NodeParser.getElementFromExpression(expression, node);
      assertThat(result).isPresent();
      assertThat(result.get().getTextContent()).isEqualTo("testValue");
    }

    @Test
    void returnEmptyWhenNonElementNodeFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/test/text()";
      Optional<Element> result = NodeParser.getElementFromExpression(expression, node);
      assertThat(result).isEmpty();
    }
  }

  @Nested
  class getMandatoryNodeFromExpression {

    @Test
    void returnNodeWhenFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/test/text()";
      Node result = NodeParser.getMandatoryNodeFromExpression(expression, node);
      assertThat(result.getNodeValue()).isEqualTo("testValue");
    }

    @Test
    void throwWhenNoNodeIsFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/bla";
      assertThatThrownBy(() -> NodeParser.getMandatoryNodeFromExpression(expression, node))
        .isInstanceOf(MandatoryNodeNotFoundException.class);
    }
  }

  @Nested
  class getMandatoryElementFromExpression {

    @Test
    void returnElementWhenFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/test";
      Element result = NodeParser.getMandatoryElementFromExpression(expression, node);
      assertThat(result.getTextContent()).isEqualTo("testValue");
    }

    @Test
    void throwWhenNoElementIsFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/test/text()";
      assertThatThrownBy(() -> NodeParser.getMandatoryElementFromExpression(expression, node))
        .isInstanceOf(MandatoryNodeNotFoundException.class);
    }
  }

  @Nested
  class getNodeList {

    @Test
    void returnEmptyListWhenNoNodesFound() {
      Node node = XmlMapper.toDocument("<test>testValue</test>");
      String expression = "/nonExistingNode";
      var nodes = NodeParser.getNodesFromExpression(expression, node);
      assertThat(nodes).isEmpty();
    }

    @Test
    void findElementNodes() {
      Node node = XmlMapper.toDocument(
        "<foo><test>testValue</test><test2>testValue2</test2><test>testValue3</test></foo>"
      );
      String expression = "//test";
      var nodes = NodeParser.getNodesFromExpression(expression, node);
      assertThat(nodes).hasSize(2);
      assertThat(nodes.getFirst().getNodeName()).isEqualTo("test");
      assertThat(nodes.getFirst().getTextContent()).isEqualTo("testValue");
      assertThat(nodes.get(1).getNodeName()).isEqualTo("test");
      assertThat(nodes.get(1).getTextContent()).isEqualTo("testValue3");
    }

    @Test
    void findTextNodes() {
      Node node = XmlMapper.toDocument(
        "<foo><test>testValue</test><test2>testValue2</test2><test>testValue3</test></foo>"
      );
      String expression = "//test/text()";
      var nodes = NodeParser.getNodesFromExpression(expression, node);
      assertThat(nodes).hasSize(2);
      assertThat(nodes.getFirst().getNodeValue()).isEqualTo("testValue");
      assertThat(nodes.get(1).getNodeValue()).isEqualTo("testValue3");
    }
  }

  @Nested
  class getElementList {

    @Test
    void findsElements() {
      Node node = XmlMapper.toDocument(
        "<foo><test>testValue</test><test2>testValue2</test2><test>testValue3</test></foo>"
      );
      String expression = "//test";
      var nodes = NodeParser.getElementsFromExpression(expression, node);
      assertThat(nodes).hasSize(2);
      assertThat(nodes.getFirst().getNodeName()).isEqualTo("test");
      assertThat(nodes.getFirst().getTextContent()).isEqualTo("testValue");
      assertThat(nodes.get(1).getNodeName()).isEqualTo("test");
      assertThat(nodes.get(1).getTextContent()).isEqualTo("testValue3");
    }

    @Test
    void ignoresNonElementNodes() {
      Node node = XmlMapper.toDocument(
        "<foo><test>testValue</test><test2>testValue2</test2><test>testValue3</test></foo>"
      );
      String expression = "//test/text()";
      var nodes = NodeParser.getElementsFromExpression(expression, node);
      assertThat(nodes).isEmpty();
    }
  }

  @Nested
  class nodeListToList {

    @Test
    void createsListOfNodesForOneElement() {
      Node node = XmlMapper.toDocument("<foo><test>testValue</test></foo>").getFirstChild();
      var nodes = NodeParser.nodeListToList(node.getChildNodes());
      assertThat(nodes).hasSize(1);
    }

    @Test
    void createsListOfNodesForMultipleElements() {
      Node node = XmlMapper
        .toDocument("<foo><test>testValue</test><test>testValue2</test></foo>")
        .getFirstChild();
      var nodes = NodeParser.nodeListToList(node.getChildNodes());
      assertThat(nodes).hasSize(2);
    }

    @Test
    void createsEmptyListForZeroElements() {
      Node node = XmlMapper.toDocument("<foo></foo>").getFirstChild();
      var nodes = NodeParser.nodeListToList(node.getChildNodes());
      assertThat(nodes).isEmpty();
    }
  }

  @Nested
  class isEmptyIgnoringWhitespace {

    @Test
    void returnTrueIfNodeIsEmpty() {
      Node node = XmlMapper.toElement("<foo></foo>");
      assertThat(NodeParser.isEmptyIgnoringWhitespace(node)).isTrue();
    }

    @Test
    void returnTrueIfNodeContentIsOnlyWhitespace() {
      Node node = XmlMapper.toElement("<foo>\n\t  </foo>");
      assertThat(NodeParser.isEmptyIgnoringWhitespace(node)).isTrue();
    }

    @Test
    void returnFalseIfNodeHasTextContent() {
      Node node = XmlMapper.toElement("<foo>Bar</foo>");
      assertThat(NodeParser.isEmptyIgnoringWhitespace(node)).isFalse();
    }

    @Test
    void returnFalseIfNodeHasChildNode() {
      Node node = XmlMapper.toElement("<foo><bar></bar></foo>");
      assertThat(NodeParser.isEmptyIgnoringWhitespace(node)).isFalse();
    }
  }

  @ParameterizedTest
  @CsvSource(
    { "//act/@name", "//*:act/@name", "//Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}act/@name" }
  )
  void xPathsWorkWithoutNamespaceAwareness(String xPath) {
    var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml",
      false
    );
    Node node = regelungstext.getDocument();
    Optional<String> result = NodeParser.getValueFromExpression(xPath, node);
    assertThat(result).contains("regelungstext");
  }

  @ParameterizedTest
  @CsvSource(
    { "//act/@name", "//*:act/@name", "//Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}act/@name" }
  )
  void xPathsWorkWithNamespaceAwareness(String xPath) {
    var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml",
      true
    );
    Node node = regelungstext.getDocument();
    Optional<String> result = NodeParser.getValueFromExpression(xPath, node);
    assertThat(result).contains("regelungstext");
  }

  @Test
  void xPathsWorkWithNamespaceDoesNotMatchInDocumentWithDifferentNamespace() {
    var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml",
      true
    );
    Node node = regelungstext.getDocument();
    Optional<String> result = NodeParser.getValueFromExpression(
      "//Q{http://Inhaltsdaten.LegalDocML.de/1.5/}act/@name",
      node
    );
    assertThat(result).isEmpty();
  }
}
