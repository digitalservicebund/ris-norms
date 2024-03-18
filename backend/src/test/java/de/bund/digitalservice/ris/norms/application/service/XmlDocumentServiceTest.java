package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import de.bund.digitalservice.ris.norms.application.service.exceptions.ModificationException;
import de.bund.digitalservice.ris.norms.application.service.exceptions.XmlProcessingException;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class XmlDocumentServiceTest {
  final XmlDocumentService xmlDocumentService = new XmlDocumentService();

  @Test
  void throwModificationExceptionIfNoHrefInModification() {
    // given
    final String amendingLaw =
        """
            <?xml version="1.0" encoding="UTF-8"?>
            <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"

     eId="art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
                                        refersTo="aenderungsbefehl-ersetzen">
                In

                <akn:ref >
                    § 20 Absatz 1 Satz 2
                </akn:ref>

                wird die Angabe

                <akn:quotedText startQuote="„" endQuote="“">
                    § 9 Abs. 1 Satz
                </akn:quotedText>

                durch die Wörter

                <akn:quotedText startQuote="„" endQuote="“">
                    § 9 Absatz 1 Satz
                </akn:quotedText>

                ersetzt.
            </akn:mod>
            """
            .strip();

    String targetLawString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw></targetLaw>";

    final Document amendingLawDocument = stringToXmlDocument(amendingLaw);
    final Document targetLawDocument = stringToXmlDocument(targetLawString);

    // when
    Throwable thrown =
        catchThrowable(
            () ->
                xmlDocumentService.findTargetLawNodeToBeModified(
                    targetLawDocument, amendingLawDocument));

    // then
    assertThat(thrown).isInstanceOf(ModificationException.class);
  }

  @Test
  void findsHrefInModification() {
    // given
    final String amendingLaw =
        """
          <?xml version="1.0" encoding="UTF-8"?>
          <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"

   eId="art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
                                      refersTo="aenderungsbefehl-ersetzen">
              In

              <akn:ref href="some/href/eIdValue/slashes.xml">
                  § 20 Absatz 1 Satz 2
              </akn:ref>

              wird die Angabe

              <akn:quotedText startQuote="„" endQuote="“">
                  § 9 Abs. 1 Satz
              </akn:quotedText>

              durch die Wörter

              <akn:quotedText startQuote="„" endQuote="“">
                  § 9 Absatz 1 Satz
              </akn:quotedText>

              ersetzt.
          </akn:mod>
          """
            .strip();

    final String targetLaw =
        """
                <?xml version="1.0" encoding="UTF-8"?>
                <akn:body>
                    <akn:p eId="one">old text</akn:p>
                    <akn:p eId="eIdValue">old text</akn:p>
                  </akn:body>
                """;

    final Document amendingLawDocument = stringToXmlDocument(amendingLaw);
    final Document targetLawDocument = stringToXmlDocument(targetLaw);

    // when
    final Node targetNode =
        xmlDocumentService.findTargetLawNodeToBeModified(targetLawDocument, amendingLawDocument);

    // then
    assertThat(targetNode.getAttributes().getNamedItem("eId").getNodeValue()).isEqualTo("eIdValue");
  }

  @Test
  void throwModificationExceptionIfNoQuotedTextIsFound() {
    final String amendingLawString =
        """
        <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:mod>
               In <akn:ref
   href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/eIdValue/9-34.xml">paragraph
   2</akn:ref> replace
              </akn:mod>

              Note that no quoted texts were given

            </akn:body>
          """
            .strip();

    final String targetLawString =
        """
                <?xml version="1.0" encoding="UTF-8"?>
                <akn:body>
                    <akn:p eId="one">old text</akn:p>
                    <akn:p eId="eIdValue">old text</akn:p>
                  </akn:body>
                """;
    final Document amendingLawDocument = stringToXmlDocument(amendingLawString);
    final Document targetLawDocument = stringToXmlDocument(targetLawString);
    final Node targetNode =
        xmlDocumentService.findTargetLawNodeToBeModified(targetLawDocument, amendingLawDocument);

    // when
    Throwable thrown = catchThrowable(() -> xmlDocumentService.getReplacementPair(targetNode));

    // then
    assertThat(thrown).isInstanceOf(ModificationException.class);
  }

  @Test
  void throwModificationExceptionIfOnlyOneQuotedTextIsFound() {
    // given
    final String amendingLawString =
        """
        <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:mod>
                  In <akn:ref
   href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/eIdValue/9-34.xml">paragraph
   2</akn:ref> replace <akn:quotedText>old text</akn:quotedText> with NO SECOND QUOTE GIVEN.
              </akn:mod>

              Note that no second quoted text is given

            </akn:body>
          """
            .strip();

    final String targetLawString =
        """
                <?xml version="1.0" encoding="UTF-8"?>
                <akn:body>
                    <akn:p eId="one">old text</akn:p>
                    <akn:p eId="eIdValue">old text</akn:p>
                  </akn:body>
                """;
    final Document amendingLawDocument = stringToXmlDocument(amendingLawString);
    final Document targetLawDocument = stringToXmlDocument(targetLawString);
    final Node targetNode =
        xmlDocumentService.findTargetLawNodeToBeModified(targetLawDocument, amendingLawDocument);

    // when
    Throwable thrown = catchThrowable(() -> xmlDocumentService.getReplacementPair(targetNode));

    // then
    assertThat(thrown).isInstanceOf(ModificationException.class);
  }

  @Test
  void returnReplacementPair() {
    // given
    final String amendingLawString =
        """
        <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:mod>
               In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/eIdValue/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old text</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
              </akn:mod>
            </akn:body>
          """
            .strip();

    final Document amendingLawDocument = stringToXmlDocument(amendingLawString);
    final Node firstModificationNodeInAmendingLaw =
        xmlDocumentService.getFirstModification(amendingLawDocument);

    // when
    final XmlDocumentService.ReplacementPair replacementPair =
        xmlDocumentService.getReplacementPair(firstModificationNodeInAmendingLaw);

    // then
    assertThat(replacementPair.newText()).isEqualTo("new");
    assertThat(replacementPair.oldText()).isEqualTo("old text");
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
