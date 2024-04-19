package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
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
   * Get {@link NodeList} using an XPath expression on an input node.
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param sourceNode the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the NodeList identified by the <code>xPathExpression</code>
   */
  public static NodeList getNodesFromExpression(String xPathExpression, Node sourceNode) {
    try {
      // should be invoked on every method call since: An XPath object is not thread-safe and not
      // reentrant.
      final XPath xPath = XPathFactory.newInstance().newXPath();
      return (NodeList) xPath.evaluate(xPathExpression, sourceNode, XPathConstants.NODESET);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }
}
