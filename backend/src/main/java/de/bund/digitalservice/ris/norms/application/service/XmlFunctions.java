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

/** TODO */
public class XmlFunctions {
  // TODO: This is not a time machine function. Move somewhere else.

  /**
   * TODO
   *
   * @param xml TODO
   * @return TODO
   */
  public static Optional<Document> loadXMLFromString(String xml) {
    try {

      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder builder = factory.newDocumentBuilder();
      final InputSource is = new InputSource(new StringReader(xml));
      final Document document = builder.parse(is);
      return Optional.of(document);
    } catch (Exception e) {
      // TODO: do something with e. Maybe log it?
    }

    return Optional.empty();
  }

  static Optional<Node> getNode(String xPathExpression, Node node) {
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

  // TODO: Move to TimeMachineFunctions
  static Optional<String> findHrefInModificationNode(Node modificationNode){
    try {
      Optional<Node> optionalNodeHrefAttribute = getNode("//*[local-name()='ref']/@href", modificationNode);
      String href = optionalNodeHrefAttribute.get().getNodeValue();
      return Optional.of(href);
    } catch (Exception e) {
      // TODO: do something with e?
    }

    return Optional.empty();
  }

  static Optional<Document> cloneDocument(Document originalDocument){
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