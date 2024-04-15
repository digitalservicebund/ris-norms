package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.application.service.exceptions.XmlProcessingException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class NormTest {

  @Test
  void getEli() {
    // given
    String normString =
        """
      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
         <akn:act name="regelungstext">
            <!-- Metadaten -->
            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                  <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                  </akn:FRBRExpression>
              </akn:identification>
            </akn:meta>
         </akn:act>
      </akn:akomaNtoso>
    """;

    Norm norm = new Norm(stringToXmlDocument(normString));
    String expectedEli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

    // when
    String actualEli = norm.getEli().get();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getOptionalEmptyEliWhenItDoesntExist() throws XPathExpressionException {
    // given
    String normString =
        """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
         <akn:act name="regelungstext">
         </akn:act>
      </akn:akomaNtoso>
        """
            .strip();

    Norm norm = new Norm(stringToXmlDocument(normString));

    Optional<String> optionalEli = norm.getEli();
    assertThat(optionalEli).isEmpty();
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
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }
}
