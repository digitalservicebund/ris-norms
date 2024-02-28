package de.bund.digitalservice.ris.norms.domain.functions;

import java.io.StringReader;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/** TODO */
public class TimeMachineFunctions {

  // TODO: This is not a time machine function. Move somewhere else.

  /**
   * TODO
   *
   * @param xml TODO
   * @return TODO
   */
  public static Optional<Document> loadXMLFromString(String xml) {
    try {

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(xml));
      Document document = builder.parse(is);
      return Optional.of(document);
    } catch (Exception e) {
      // TODO: do something with e. Maybe log it?
    }

    return Optional.empty();
  }

  // TODO documentation

  /**
   * 
   * @param amendingLaw
   * @param targetLaw
   * @return TODO
   */
  public static Optional<Document> applyTimeMachine(Optional<Document> amendingLaw, Optional<Document> targetLaw){
    final Optional<Document> someDocument = loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>");
    return someDocument;
  }
}
