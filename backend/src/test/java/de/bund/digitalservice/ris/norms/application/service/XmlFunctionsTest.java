package de.bund.digitalservice.ris.norms.application.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XmlFunctionsTest {

  /** loadXmlFromString */
  @Test
  public void documentGeneratedFromValidXmlStringMustNotBeEmpty() {
    // given
    final String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";
    
    // when
    final Optional<Document> result = XmlFunctions.loadXMLFromString(input);
    
    // then
    assertTrue(result.isPresent());
  }
  
  @Test
  public void documentGeneratedFromInValidXmlStringMustBeEmpty() {
    // given
    final String input = "invalid XML; does not even have an XML declaration";
    
    // when
    final Optional<Document> result = XmlFunctions.loadXMLFromString(input);
    
    // then
    assertTrue(result.isEmpty());
  }
  
  /** getNode() */
  @Test
  public void returnEmptyIfNoNodeMatches(){
    // given
    final String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";
    final Optional<Document> document = XmlFunctions.loadXMLFromString(xmlString);
    
    // when
    final Optional<Node> node = XmlFunctions.getNode("", document.get());
    
    // then
    assertTrue(node.isEmpty());
  }

  @Test
  public void returnMatchingNode(){
    // given
    final String xmlString = """
      <?xml version=\"1.0\" encoding=\"UTF-8\"?>
      <root>
        <notMyNode>
          not my node
        </notMyNode>
        <myNode>
          my node
          <childNode>
            child node
          </childNode>
          my node still
        </myNode>
      </root>
      """;
    final Optional<Document> document = XmlFunctions.loadXMLFromString(xmlString);

    // when
    final Optional<Node> node = XmlFunctions.getNode("", document.get());

    // then
    assertTrue(node.isPresent());
  }
}
