package de.bund.digitalservice.ris.norms.application.service;

import java.io.StringReader;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

  static Optional<Node> getNode(String expression, Document document) {
    try {
      throw new Exception("not implemented, yet");
    } catch (Exception e) {
      // TODO: do something with e?
    }

    return Optional.empty();
  }
}
