package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/** Convenience methods handling {@link Document} */
public class DocumentUtils {

  // Should not be instantiated
  private DocumentUtils() {}

  /**
   * Copies a {@link Document}
   *
   * @param originalDocument the document to be cloned
   * @return a copy of {@link Document}
   */
  public static Document cloneDocument(Document originalDocument) {
    try {
      final Node originalRootNode = originalDocument.getDocumentElement();
      final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      final DocumentBuilder db = dbf.newDocumentBuilder();

      final org.w3c.dom.Document clonedDocument = db.newDocument();
      final Node clonedRootNode = clonedDocument.importNode(originalRootNode, true);
      clonedDocument.appendChild(clonedRootNode);
      return clonedDocument;
    } catch (ParserConfigurationException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }
}
