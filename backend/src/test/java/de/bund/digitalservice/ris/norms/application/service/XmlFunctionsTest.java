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
  public void returnMatchingNodeByName(){
    // given
    final String xmlString = """
      <?xml version=\"1.0\" encoding=\"UTF-8\"?>
      <root>
        <not-my-node>
          not my node
        </not-my-node>
        <my-node>
          my node
        </my-node>
      </root>
      """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlString);
    
    // when
    String xPathExpression = "//*[local-name()='my-node']";
    final Optional<Node> optionalMyNode = XmlFunctions.getNode(xPathExpression, optionalDocument.get());

    // then
    assertTrue(optionalMyNode.isPresent());
    assertTrue(optionalMyNode.get().getTextContent().contains("my node"));
  }

  @Test
  public void returnMatchingNodeByAttributeValue() {
    // given
    final String xmlText = """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
        <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                    eId="theEId"
                                    refersTo="aenderungsbefehl-ersetzen">

            my node
            
        </akn:mod>
        """;
    
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final String xPathExpression = "//*[@eId='theEId']";
    final Optional<Node> optionalNodeByEId = XmlFunctions.getNode(xPathExpression, optionalDocument.get());

    // then
    assertTrue(optionalNodeByEId.isPresent());
  }
}
