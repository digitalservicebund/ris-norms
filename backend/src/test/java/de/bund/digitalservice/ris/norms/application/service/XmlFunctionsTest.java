package de.bund.digitalservice.ris.norms.application.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

class XmlFunctionsTest {

  @Nested
  class StringToXmlDocument {
    @Test
    void documentGeneratedFromValidXmlStringMustNotBeEmpty() {
      // given
      final String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";

      // when
      final Optional<Document> result = XmlFunctions.stringToXmlDocument(input);

      // then
      assertTrue(result.isPresent());
    }

    @Test
    void documentGeneratedFromInValidXmlStringMustBeEmpty() {
      // given
      final String input = "invalid XML; does not even have an XML declaration";

      // when
      final Optional<Document> result = XmlFunctions.stringToXmlDocument(input);

      // then
      assertTrue(result.isEmpty());
    }
  }

  @Nested
  class GetNodeByXPath {
    @Test
    void returnEmptyIfNoNodeMatches() {
      // given
      final String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";
      final Optional<Document> document = XmlFunctions.stringToXmlDocument(xmlString);

      // when
      final Optional<Node> node = XmlFunctions.getNodeByXPath("", document.get());

      // then
      assertTrue(node.isEmpty());
    }

    @Test
    void returnMatchingNodeByName() {
      // given
      final String xmlString =
          """
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
      final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlString);

      // when
      String xPathExpression = "//*[local-name()='my-node']";
      final Optional<Node> optionalMyNode =
          XmlFunctions.getNodeByXPath(xPathExpression, optionalDocument.get());

      // then
      assertTrue(optionalMyNode.isPresent());
      assertTrue(optionalMyNode.get().getTextContent().contains("my node"));
    }

    @Test
    void returnMatchingNodeByAttributeValue() {
      // given
      final String xmlText =
          """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
        <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                    eId="theEId"
                                    refersTo="aenderungsbefehl-ersetzen">

            my node

        </akn:mod>
        """;

      final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

      // when
      final String xPathExpression = "//*[@eId='theEId']";
      final Optional<Node> optionalNodeByEId =
          XmlFunctions.getNodeByXPath(xPathExpression, optionalDocument.get());

      // then
      assertTrue(optionalNodeByEId.isPresent());
    }
  }

  /** cloneDocument() */
  @Test
  void clonedDocumentMustContainSameTextButNoBeIdentical() {
    // given
    final String xmlText =
        """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
        <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                    eId="theEId"
                                    refersTo="aenderungsbefehl-ersetzen">

            my node

        </akn:mod>
        """;

    final Document originalDocument = XmlFunctions.stringToXmlDocument(xmlText).get();
    final String xPathExpresion = "//*[@eId='theEId']";
    final String textOfOriginalDocumentModification =
        XmlFunctions.getNodeByXPath(xPathExpresion, originalDocument).get().getTextContent();

    // when
    final Document clonedDocument = XmlFunctions.cloneDocument(originalDocument).get();
    final String textOfClonedDocumentModification =
        XmlFunctions.getNodeByXPath(xPathExpresion, clonedDocument).get().getTextContent();

    // then
    assertEquals(textOfOriginalDocumentModification, textOfClonedDocumentModification);
    assertNotEquals((Object) originalDocument, (Object) clonedDocument);
  }
}
