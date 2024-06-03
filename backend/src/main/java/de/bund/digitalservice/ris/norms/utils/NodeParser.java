package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Util class that is responsible for parsing a {@link Node}. */
public final class NodeParser {

  private NodeParser() {
    // Should not be instantiated as an object
  }

  /**
   * Get value using an XPath expression on an input node.
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param sourceNode the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the value identified by the <code>xPathExpression</code>
   */
  public static Optional<String> getValueFromExpression(String xPathExpression, Node sourceNode) {
    try {
      // text nodes can not be used with xpaths
      if (sourceNode.getNodeType() == Node.TEXT_NODE) {
        return Optional.empty();
      }

      // should be invoked on every method call since: An XPath object is not thread-safe and not
      // reentrant.
      final XPath xPath = XPathFactory.newInstance().newXPath();
      String result = (String) xPath.evaluate(xPathExpression, sourceNode, XPathConstants.STRING);
      return result.isEmpty() ? Optional.empty() : Optional.of(result);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }

  /**
   * Get a {@link List} of {@link Node}s using an XPath expression on an input node.
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param sourceNode the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the Nodes identified by the <code>xPathExpression</code>
   */
  public static List<Node> getNodesFromExpression(String xPathExpression, Node sourceNode) {
    try {
      // should be invoked on every method call since: An XPath object is not thread-safe and not
      // reentrant.
      final XPath xPath = XPathFactory.newInstance().newXPath();
      final NodeList nodeList =
          (NodeList) xPath.evaluate(xPathExpression, sourceNode, XPathConstants.NODESET);
      return nodeListToList(nodeList);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
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
  public static Optional<Node> getNodeFromExpression(String xPathExpression, Node sourceNode) {
    try {
      final XPathFactory xpathfactory = XPathFactory.newInstance();
      final XPath xpath = xpathfactory.newXPath();
      final var result = xpath.evaluate(xPathExpression, sourceNode, XPathConstants.NODE);
      if (result instanceof Node node) {
        return Optional.of(node);
      }
      return Optional.empty();
    } catch (XPathExpressionException | NullPointerException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }

  /**
   * Converts a {@link NodeList} into a {@link List} of {@link Node}s.
   *
   * @param nodeList the {@link NodeList} to convert
   * @return The {@link Node}s of the {@link NodeList} as a {@link List}
   */
  public static List<Node> nodeListToList(NodeList nodeList) {
    if (nodeList.getLength() == 0) {
      return List.of();
    }

    List<Node> nodes = new ArrayList<>();

    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      nodes.add(node);
    }

    return nodes;
  }
}
