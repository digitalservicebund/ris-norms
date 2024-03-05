package de.bund.digitalservice.ris.norms.application.service;

import java.io.StringReader;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/** Namespace for functions dealing with XML Documents or Nodes */
public class XmlFunctions {

  /**
   * Create an XML Document from an xml string representation.
   *
   * @param xmlText an XML represented as a string
   * @return a Document representation of the string's XML
   */
  public static Optional<Document> stringToXmlDocument(String xmlText) {
    try {

      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder builder = factory.newDocumentBuilder();
      final InputSource is = new InputSource(new StringReader(xmlText));
      final Document document = builder.parse(is);
      return Optional.of(document);
    } catch (Exception e) {
      // TODO: do something with e. Maybe log it?
    }

    return Optional.empty();
  }

  /**
   * Get single node using an XPath expression on an input node. Note that it only supports single
   * node results (not NODELISTs)
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param node the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the Node identified by the <code>xPathExpression</code>
   */
  public static Optional<Node> getNode(String xPathExpression, Node node) {
    try {
      XPathFactory xpathfactory = XPathFactory.newInstance();
      XPath xpath = xpathfactory.newXPath();
      Node nodeByXPath = (Node) xpath.evaluate(xPathExpression, node, XPathConstants.NODE);
      return Optional.of(nodeByXPath);
    } catch (Exception e) {
      // TODO: do something with e?
    }

    return Optional.empty();
  }

  /**
   * Create a deep copy of a Document
   * 
   * @param originalDocument the document we want to clone
   * @return a deep copy of the <code>originalDocument</code>
   */
  public static Optional<Document> cloneDocument(Document originalDocument) {
    try {
      Node originalRootNode = originalDocument.getDocumentElement();
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document clonedDocument = db.newDocument();
      Node clonedRootNode = clonedDocument.importNode(originalRootNode, true);
      clonedDocument.appendChild(clonedRootNode);
      return Optional.of(clonedDocument);
    } catch (Exception e) {
      // TODO: do something with e?
    }

    return Optional.empty();
  }
}
