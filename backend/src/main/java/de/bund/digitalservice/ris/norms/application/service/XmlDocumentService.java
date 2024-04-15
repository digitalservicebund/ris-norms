package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.exceptions.XmlProcessingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Service class within the application core part of the backend. It is responsible for working with
 * w3c documents. It is annotated with {@link Service} to indicate that it's a service component in
 * the Spring context.
 */
@Service
public class XmlDocumentService {

  /**
   * Create a deep copy of a Document
   *
   * @param originalDocument the document we want to clone
   * @return a deep copy of the <code>originalDocument</code>
   */
  Document cloneDocument(Document originalDocument) {
    try {
      final Node originalRootNode = originalDocument.getDocumentElement();
      final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      final DocumentBuilder db = dbf.newDocumentBuilder();

      final Document clonedDocument = db.newDocument();
      final Node clonedRootNode = clonedDocument.importNode(originalRootNode, true);
      clonedDocument.appendChild(clonedRootNode);
      return clonedDocument;
    } catch (ParserConfigurationException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }

  ReplacementPair getReplacementPair(Node firstModificationNodeInAmendingLaw) {
    final String oldText = getTextToBeReplaced(firstModificationNodeInAmendingLaw);

    final String newText = getNewTextInReplacement(firstModificationNodeInAmendingLaw);
    return new ReplacementPair(oldText, newText);
  }

  record ReplacementPair(String oldText, String newText) {}

  Node findTargetLawNodeToBeModified(Document targetLaw, Node firstModificationNodeInAmendingLaw) {
    final String modificationHref = findHrefInModificationNode(firstModificationNodeInAmendingLaw);

    final String eId = getEIdFromModificationHref(modificationHref);

    return findNodeByEId(eId, targetLaw);
  }

  /**
   * Get the first modification in the amending law that can be found
   *
   * @param amendingLaw the law to be searched
   * @return the Node containing the modification
   */
  Node getFirstModification(Document amendingLaw) {
    return getNodeByXPath("//*[local-name()='mod']", amendingLaw);
  }

  private String findHrefInModificationNode(Node modificationNode) {
    try {
      return getNodeByXPath("//*[local-name()='ref']/@href", modificationNode).getNodeValue();
    } catch (NullPointerException e) {
      throw new XmlProcessingException("Href was not present", e);
    }
  }

  private String getEIdFromModificationHref(String modificationHref) {
    final String[] hrefParts = modificationHref.split("/");

    if (hrefParts.length >= 2) {
      return hrefParts[hrefParts.length - 2];
    } else throw new XmlProcessingException("Href in modification had less than 2 parts", null);
  }

  private Node findNodeByEId(String eId, Node node) {
    final String xPathExpression = "//*[@eId='" + eId + "']";
    return getNodeByXPath(xPathExpression, node);
  }

  private String getTextToBeReplaced(Node node) {
    final String xPathExpression = "//*[local-name()='quotedText']";
    final Node quotedTextNode = getNodeByXPath(xPathExpression, node);

    try {
      return quotedTextNode.getTextContent();
    } catch (NullPointerException e) {
      throw new XmlProcessingException("No former text was found in modification.", e);
    }
  }

  private String getNewTextInReplacement(Node node) {
    final String xPathExpression = "(//*[local-name()='quotedText'])[2]";
    final Node quotedTextNode = getNodeByXPath(xPathExpression, node);
    try {
      return quotedTextNode.getTextContent();
    } catch (NullPointerException e) {
      throw new XmlProcessingException("No text replacement was found in modification.", e);
    }
  }

  /**
   * Get single node using an XPath expression on an input node. Note that it only supports single
   * node results (not NODELISTs)
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param sourceNode the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the Node identified by the <code>xPathExpression</code>
   */
  private Node getNodeByXPath(String xPathExpression, Node sourceNode) {
    try {
      final XPathFactory xpathfactory = XPathFactory.newInstance();
      final XPath xpath = xpathfactory.newXPath();
      return (Node) xpath.evaluate(xPathExpression, sourceNode, XPathConstants.NODE);
    } catch (XPathExpressionException | NullPointerException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }
}
