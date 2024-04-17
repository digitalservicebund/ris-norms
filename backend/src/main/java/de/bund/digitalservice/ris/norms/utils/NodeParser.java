package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.domain.exceptions.XmlProcessingException;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Util class that is responsible for parsing a {@link Node}. */
public class NodeParser {
  private NodeParser() {
    // Should not be instantiated
  }

  /**
   * Get {@link NodeList} using an XPath expression on an input node.
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param sourceNode the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the Node identified by the <code>xPathExpression</code>
   */
  public static Optional<String> getValueFromExpression(String xPathExpression, Node sourceNode) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    String result;
    try {
      result = (String) xPath.evaluate(xPathExpression, sourceNode, XPathConstants.STRING);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
    if (result.isEmpty()) return Optional.empty();

    return Optional.of(result);
  }

  /**
   * Get {@link NodeList} using an XPath expression on an input node.
   *
   * @param xPathExpression an XPath expression used for identifying the node that's returned
   * @param sourceNode the Node we're applying the XPath expression on (may also be a Document, as
   *     Document extends Node)
   * @return the Node identified by the <code>xPathExpression</code>
   */
  public static Optional<NodeList> getNodesFromExpression(String xPathExpression, Node sourceNode) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    NodeList result;
    try {
      result = (NodeList) xPath.evaluate(xPathExpression, sourceNode, XPathConstants.NODESET);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
    return Optional.of(result);
  }
}
