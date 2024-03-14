package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.service.exceptions.XmlProcessingException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying LDML.de
 * "modifications" to LDML.de files. For details on LDML.de modifications, cf. <a
 * href="https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads">LDML-details</a>
 */
@Service
public class TimeMachineService {

  XmlDocumentService xmlDocumentService;

  public TimeMachineService(XmlDocumentService xmlDocumentService) {
    this.xmlDocumentService = xmlDocumentService;
  }

  /**
   * Applies the modifications of the amending law onto the target law.
   *
   * @param amendingLawString a Document that contains LDML.de modifications to be applied on the
   *     target law
   * @param targetLawString The Document that the modifications will be applied to
   * @return the Document that results in applying the amending law's modifications to the target
   *     law
   */
  public String apply(final String amendingLawString, final String targetLawString) {

    final Document amendingLaw = stringToXmlDocument(amendingLawString);
    final Document targetLaw = stringToXmlDocument(targetLawString);

    final Node firstModificationNodeInAmendingLaw =
        xmlDocumentService.getFirstModification(amendingLaw);

    final Document appliedTargetLaw =
        applyOneSubstitutionModification(firstModificationNodeInAmendingLaw, targetLaw);

    return convertDocumentToString(appliedTargetLaw);
  }

  /**
   * Applies one substitution modification
   *
   * @param modificationNode the node containing the substitution
   * @param targetLaw the document version of the target law
   * @return the <code>targetLaw</code> with the applied modification
   */
  private Document applyOneSubstitutionModification(Node modificationNode, Document targetLaw) {
    final Document targetLawClone = xmlDocumentService.cloneDocument(targetLaw);

    final XmlDocumentService.ReplacementPair replacementPair =
        xmlDocumentService.getReplacementPair(modificationNode);

    final Node targetLawNodeToBeModified =
        xmlDocumentService.findTargetLawNodeToBeModified(targetLawClone, modificationNode);

    final String modifiedTextContent =
        targetLawNodeToBeModified
            .getTextContent()
            .replaceFirst(replacementPair.oldText(), replacementPair.newText());
    targetLawNodeToBeModified.setTextContent(modifiedTextContent);
    return targetLawClone;
  }

  private Document stringToXmlDocument(String xmlText) {

    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    // XXE vulnerability hardening, cf. https://www.sonarsource.com/blog/secure-xml-processor/
    try {
      factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

      factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      factory.setExpandEntityReferences(false);

      final DocumentBuilder builder = factory.newDocumentBuilder();
      final InputSource is = new InputSource(new StringReader(xmlText));
      return builder.parse(is);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new XmlProcessingException();
    }
  }

  private String convertDocumentToString(Document doc) {
    try {
      final DOMSource domSource = new DOMSource(doc);
      final StringWriter writer = new StringWriter();
      final StreamResult result = new StreamResult(writer);

      final TransformerFactory factory = TransformerFactory.newInstance();
      // Disable the use of external general and parameter entities to prevent XXE attacks
      factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

      final Transformer transformer = factory.newTransformer();
      transformer.transform(domSource, result);
      return writer.toString();
    } catch (TransformerException e) {
      throw new XmlProcessingException();
    }
  }
}
