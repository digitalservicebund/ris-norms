package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Util class that is responsible for parsing a {@link Node}. */
public final class NodeParser {
  // the XPathFactory is not thread safe so every thread gets its own one. It should not be a
  // problem that we do not call remove() on it as it can be reused even after returning the thread
  // to a ThreadPool.
  private static final ThreadLocal<XPathFactory> xPathFactory =
      ThreadLocal.withInitial(XPathFactory::newInstance);

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

      // Clone the node if the xpath is relative to the node. This drastically improves the
      // performance.
      if (xPathExpression.startsWith("./")) {
        sourceNode = sourceNode.cloneNode(true);
      }

      // should be invoked on every method call since: An XPath object is not thread-safe and not
      // reentrant.
      final XPath xPath = xPathFactory.get().newXPath();
      String result = (String) xPath.evaluate(xPathExpression, sourceNode, XPathConstants.STRING);
      return result.isEmpty() ? Optional.empty() : Optional.of(result);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }

  /**
   * Get the value of a mandatory node using an XPath expression on an input node. If node not
   * found, throws a {@link MandatoryNodeNotFoundException}
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param sourceNode the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the Node identified by the <code>xPathExpression</code>
   */
  public static String getValueFromMandatoryNodeFromExpression(
      String xPathExpression, Node sourceNode) {
    return getValueFromExpression(xPathExpression, sourceNode)
        .orElseThrow(() -> throwMandatoryNotFoundException(xPathExpression, sourceNode));
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
      final NodeList nodeList =
          (NodeList)
              xPathFactory
                  .get()
                  .newXPath()
                  .evaluate(xPathExpression, sourceNode, XPathConstants.NODESET);
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
      final XPath xpath = xPathFactory.get().newXPath();
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
   * Get single mandatory node using an XPath expression on an input node. If node not found, throws
   * a {@link MandatoryNodeNotFoundException}
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param sourceNode the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the Node identified by the <code>xPathExpression</code>
   */
  public static Node getMandatoryNodeFromExpression(String xPathExpression, Node sourceNode) {
    return getNodeFromExpression(xPathExpression, sourceNode)
        .orElseThrow(() -> throwMandatoryNotFoundException(xPathExpression, sourceNode));
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

  private static MandatoryNodeNotFoundException throwMandatoryNotFoundException(
      String xPathExpression, Node sourceNode) {
    final Optional<String> optionalEli =
        sourceNode.getOwnerDocument() == null
            ? getEli((Document) sourceNode)
            : getEli(sourceNode.getOwnerDocument());

    final String nodeName = sourceNode.getNodeName();

    return optionalEli
        .map(
            eli ->
                "#document".equals(nodeName)
                    ? new MandatoryNodeNotFoundException(xPathExpression, eli)
                    : new MandatoryNodeNotFoundException(xPathExpression, nodeName, eli))
        .orElseGet(() -> new MandatoryNodeNotFoundException(xPathExpression));
  }

  private static Optional<String> getEli(final Document document) {
    return NodeParser.getValueFromExpression("//FRBRExpression/FRBRthis/@value", document)
        .or(
            () ->
                NodeParser.getValueFromExpression("//FRBRManifestation/FRBRthis/@value", document)
                    .map(m -> m.replace(".xml", "")));
  }
}
