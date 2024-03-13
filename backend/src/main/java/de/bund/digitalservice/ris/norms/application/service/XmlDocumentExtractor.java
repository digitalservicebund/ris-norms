package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.service.exceptions.ModificationException;
import de.bund.digitalservice.ris.norms.application.service.exceptions.XmlProcessingException;
import java.util.Optional;
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
public class XmlDocumentExtractor {

  /**
   * Create a deep copy of a Document
   *
   * @param originalDocument the document we want to clone
   * @return a deep copy of the <code>originalDocument</code>
   */
  Document cloneDocument(Document originalDocument) {
    try {
      Node originalRootNode = originalDocument.getDocumentElement();
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();

      Document clonedDocument = db.newDocument();
      Node clonedRootNode = clonedDocument.importNode(originalRootNode, true);
      clonedDocument.appendChild(clonedRootNode);
      return clonedDocument;
    } catch (ParserConfigurationException e) {
      throw new XmlProcessingException();
    }
  }

  ReplacementPair getReplacementText(Node firstModificationNodeInAmendingLaw) {
    try {
      final Optional<String> oldText = getTextToBeReplaced(firstModificationNodeInAmendingLaw);

      final Optional<String> newText = getNewTextInReplacement(firstModificationNodeInAmendingLaw);
      if (oldText.isEmpty() || newText.isEmpty()) throw new ModificationException();
      return new ReplacementPair(oldText.get(), newText.get());
    } catch (XPathExpressionException e) {
      throw new XmlProcessingException();
    }
  }

  record ReplacementPair(String oldText, String newText) {}

  Node findTargetLawNodeToBeModified(Document targetLaw, Node firstModificationNodeInAmendingLaw) {
    try {
      final Optional<String> modificationHref;

      modificationHref = findHrefInModificationNode(firstModificationNodeInAmendingLaw);
      if (modificationHref.isEmpty()) throw new ModificationException();

      final Optional<String> eId = getEIdfromModificationHref(modificationHref.get());
      if (eId.isEmpty()) throw new ModificationException();

      final Optional<Node> targetLawNodeToBeModified = findNodeByEId(eId.get(), targetLaw);
      if (targetLawNodeToBeModified.isEmpty()) throw new ModificationException();
      return targetLawNodeToBeModified.get();
    } catch (XPathExpressionException e) {
      throw new XmlProcessingException();
    }
  }

  /**
   * Get the first modification in the amending law that can be found
   *
   * @param amendingLaw the law to be searched
   * @return the Node containing the modification
   */
  Node getFirstModification(Document amendingLaw) {
    try {
      Optional<Node> firstModification = getNodeByXPath("//*[local-name()='mod']", amendingLaw);
      if (firstModification.isEmpty()) throw new ModificationException();
      return firstModification.get();
    } catch (XPathExpressionException e) {
      throw new XmlProcessingException();
    }
  }

  private Optional<String> findHrefInModificationNode(Node modificationNode)
      throws XPathExpressionException {
    Optional<Node> optionalNodeHrefAttribute =
        getNodeByXPath("//*[local-name()='ref']/@href", modificationNode);

    return optionalNodeHrefAttribute.map(Node::getNodeValue);
  }

  private Optional<String> getEIdfromModificationHref(String modificationHref) {
    final String[] hrefParts = modificationHref.split("/");

    if (hrefParts.length >= 2) {
      final String eId = hrefParts[hrefParts.length - 2];
      return Optional.of(eId);
    }

    return Optional.empty();
  }

  private Optional<Node> findNodeByEId(String eId, Node node) throws XPathExpressionException {
    final String xPathExpression = "//*[@eId='" + eId + "']";
    return getNodeByXPath(xPathExpression, node);
  }

  private Optional<String> getTextToBeReplaced(Node node) throws XPathExpressionException {
    // make sure there are two texts
    final String xPathExpressionSecondNode = "(//*[local-name()='quotedText'])[2]";
    final Optional<Node> optionalSecondNode = getNodeByXPath(xPathExpressionSecondNode, node);
    if (optionalSecondNode.isEmpty()) return Optional.empty();

    // now get the first one
    final String xPathExpression = "//*[local-name()='quotedText']";
    final Optional<Node> optionalNode = getNodeByXPath(xPathExpression, node);

    if (optionalNode.isPresent()) {
      final String textToBeReplaced = optionalNode.get().getTextContent();
      return Optional.of(textToBeReplaced);
    }

    return Optional.empty();
  }

  private Optional<String> getNewTextInReplacement(Node node) throws XPathExpressionException {
    final String xPathExpression = "(//*[local-name()='quotedText'])[2]";
    final Optional<Node> optionalNode = getNodeByXPath(xPathExpression, node);

    if (optionalNode.isPresent()) {
      final String newText = optionalNode.get().getTextContent();
      return Optional.of(newText);
    }
    return Optional.empty();
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
  private Optional<Node> getNodeByXPath(String xPathExpression, Node sourceNode)
      throws XPathExpressionException {
    try {
      XPathFactory xpathfactory = XPathFactory.newInstance();
      XPath xpath = xpathfactory.newXPath();
      Node nodeByXPath = (Node) xpath.evaluate(xPathExpression, sourceNode, XPathConstants.NODE);
      return Optional.of(nodeByXPath);
    } catch (NullPointerException e) {
      return Optional.empty();
    }
  }
}
