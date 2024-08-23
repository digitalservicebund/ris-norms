package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.SchemaFactory;
import net.sf.saxon.TransformerFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Mapper class for converting between a string containing xml and various types of w3c DOM
 * elements.
 */
public class XmlMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private XmlMapper() {}

  /**
   * Maps a string containing xml to a {@link Document}. The schema is not validated and all
   * whitespace is treated as significant.
   *
   * @param xmlText The input string containing xml to be mapped to a {@link Document}
   * @return the resulting {@link Document}
   */
  public static Document toDocument(String xmlText) {
    return toDocument(xmlText, false);
  }

  /**
   * Maps a string containing xml to a {@link Document}.
   *
   * @param xmlText The input string containing xml to be mapped to a {@link Document}
   * @param validateSchema Should the schema of the XML be validated to be valid LDML.de? When this
   *     is false all whitespace will be treated as significant.
   * @return the resulting {@link Document}
   */
  public static Document toDocument(String xmlText, boolean validateSchema) {

    var factory = DocumentBuilderFactory.newInstance();

    try {
      // XXE vulnerability hardening, cf. https://www.sonarsource.com/blog/secure-xml-processor/
      factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      factory.setExpandEntityReferences(false);

      if (validateSchema) {
        factory.setNamespaceAware(true);
        factory.setSchema(
            SchemaFactory.newDefaultInstance()
                .newSchema(
                    XmlMapper.class.getResource("/schema/fixtures/ldml1.6_ds_regelungstext.xsd")));
        factory.setIgnoringElementContentWhitespace(
            true); // does only work when a schema is provided
      }

      final DocumentBuilder builder = factory.newDocumentBuilder();
      final InputSource is = new InputSource(new StringReader(xmlText));
      return builder.parse(is);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }

  /**
   * Maps a string containing xml to a {@link Node}.
   *
   * @param xmlText The input string containing xml to be mapped to a {@link Node}
   * @return the resulting {@link Node}
   */
  public static Node toNode(String xmlText) {
    return toDocument(xmlText).getDocumentElement();
  }

  /**
   * Maps a {@link Node} to a string. This can also be used for an entire {@link Document}, as a
   * document is just a more specific version of a node.
   *
   * @param node The input {@link Node} to be mapped to a string
   * @return the resulting text representation of the {@link Node}
   */
  public static String toString(Node node) {
    var writer = new StringWriter();

    try {
      new TransformerFactoryImpl()
          .newTransformer()
          .transform(new DOMSource(node), new StreamResult(writer));
    } catch (TransformerException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }

    return writer.toString();
  }
}
