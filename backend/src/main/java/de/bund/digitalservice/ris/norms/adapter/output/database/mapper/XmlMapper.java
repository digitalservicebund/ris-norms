package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.domain.exceptions.XmlProcessingException;
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
import net.sf.saxon.TransformerFactoryImpl;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/** Mapper class for converting between a string containing xml and {@link org.w3c.dom.Document}. */
public class XmlMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private XmlMapper() {}

  /**
   * Maps a string containing xml to a {@link Document}.
   *
   * @param xmlText The input string containing xml to be mapped to a {@link Document}
   * @return the resulting {@link Document}
   */
  public static Document toDocument(String xmlText) {

    var factory = DocumentBuilderFactory.newInstance();

    // XXE vulnerability hardening, cf. https://www.sonarsource.com/blog/secure-xml-processor/
    try {
      factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

      factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      factory.setExpandEntityReferences(false);

      final DocumentBuilder builder = factory.newDocumentBuilder();
      final InputSource is = new InputSource(new StringReader(xmlText));
      return builder.parse(is);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }

  /**
   * Maps a {@link Document} to a string.
   *
   * @param document The input {@link Document} to be mapped to a string
   * @return the resulting text representation of the {@link Document}
   */
  public static String toString(Document document) {
    var writer = new StringWriter();

    try {
      new TransformerFactoryImpl()
          .newTransformer()
          .transform(new DOMSource(document), new StreamResult(writer));
    } catch (TransformerException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }

    return writer.toString();
  }
}
