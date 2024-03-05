package de.bund.digitalservice.ris.norms.application.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

class TimeMachineFunctionsTest {

  /** applyTimeMachine() */
  @Test
  void returnEmptyOnFailure() {
    // given
    final Document amendingLaw =
        XmlFunctions.loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>")
            .get();
    final Document targetLaw =
        XmlFunctions.loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><target/>").get();
    // when
    final Optional<Document> optionalResultingLaw =
        TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);
    // then
    assertTrue(optionalResultingLaw.isEmpty());
  }


  @Test
  void xmlDocumentsGoInAndOut() {
    // given
     // given
    final String amendingLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
            </akn:mod>

            "old" -> "new"

          </akn:body>
        """;
    final String targetLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:p eId="one">old text</akn:p>
            <akn:p eId="two">old text</akn:p>
          </akn:body>
        """;

    final Document amendingLaw = XmlFunctions.loadXMLFromString(amendingLawXmlText).get();
    final Document targetLaw = XmlFunctions.loadXMLFromString(targetLawXmlText).get();
    // when 
    final Optional<Document> resultingLaw =
    TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);

    // then
    assertTrue(resultingLaw.isPresent());
  }

  @Test
  void targetLawStaysUnchangedIfAmendingLawHasNoModifications() {
    // given
    final Document amendingLaw =
        XmlFunctions.loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>")
            .get();
    final Document targetLaw =
        XmlFunctions.loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><target/>").get();
    // when
    final Optional<Document> resultingLaw =
        TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);
    // then
    assertTrue(resultingLaw.get().equals(targetLaw));
  }

  @Test
  void targetLawToContainTheNewTextInPlaceOfTheOldOne() {
    // given
    final String amendingLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
            </akn:mod>

            "old" -> "new"

          </akn:body>
        """;
    final String targetLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:p eId="one">old text</akn:p>
            <akn:p eId="two">old text</akn:p>
          </akn:body>
        """;

    final String expectedResultingLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:p eId="one">new text</akn:p>
            <akn:p eId="two">new text</akn:p>
          </akn:body>
        """;
    final Document optionalAmendingLaw = XmlFunctions.loadXMLFromString(amendingLawXmlText).get();
    final Document optionalTargetLaw = XmlFunctions.loadXMLFromString(targetLawXmlText).get();
    final Document expectedResultingLaw =
        XmlFunctions.loadXMLFromString(expectedResultingLawXmlText).get();

    // when applying the TimeMachine
    final Optional<Document> resultingLaw =
        TimeMachineFunctions.applyTimeMachine(optionalAmendingLaw, optionalTargetLaw);

    // the result contains the new text in place of the old text
    assertTrue(resultingLaw.isPresent());
    assertTrue(resultingLaw.get().toString().equals(expectedResultingLaw.toString()));
  }

  /** getFirstModification() */
  @Test
  void returnEmptyIfThereIsNoFirstModification() {
    // given
    final Document amendingLawWithoutModification =
        XmlFunctions.loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>")
            .get();
    // when
    final Optional<Node> firstModificationNode =
        TimeMachineFunctions.getFirstModification(amendingLawWithoutModification);
    // then
    assertTrue(firstModificationNode.isEmpty());
  }

  @Test
  void returnModificationNodeIfThereIsAFirstModification() {
    // given
    final String xmlText =
        """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
          <akn:mod>§ 20 Absatz 1 Satz 2 wird ersetzt.</akn:mod>
        """;
    final Document amendingLawWithModification = XmlFunctions.loadXMLFromString(xmlText).get();
    // when
    final Optional<Node> firstModificationNode =
        TimeMachineFunctions.getFirstModification(amendingLawWithModification);
    // then
    assertTrue(firstModificationNode.isPresent());
    assertTrue(firstModificationNode.get().getTextContent().equals("§ 20 Absatz 1 Satz 2 wird ersetzt."));
  }

  /** findHrefInModification() */
  @Test
  void returnEmptyIfNoHrefInModification() {
    // given
    final String xmlText =
        """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
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
        """;

    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);
    final Optional<Node> optionalModificationNode =
        XmlFunctions.getNode("//*[local-name()='mod']", optionalDocument.get());

    // when
    final Optional<String> optionalHref =
        XmlFunctions.findHrefInModificationNode(optionalModificationNode.get());

    // then
    assertTrue(optionalHref.isEmpty());
  }

  @Test
  void returnHrefInModification() {
    // given
    final String xmlText =
        """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
        <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                    eId="art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
                                    refersTo="aenderungsbefehl-ersetzen">
            In

            <akn:ref href="some/href/with/slashes.xml">
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
        """;

    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);
    final Optional<Node> optionalModificationNode =
        XmlFunctions.getNode("//*[local-name()='mod']", optionalDocument.get());

    // when
    final Optional<String> optionalHref =
        XmlFunctions.findHrefInModificationNode(optionalModificationNode.get());

    // then
    assertTrue(optionalHref.isPresent());
    assertTrue(optionalHref.get().equals("some/href/with/slashes.xml"));
  }

  /** getEIdFromModificationHref */
  @Test
  void returnEmptyIfModificationHrefCannotBeSplitToGetEli() {
    // given
    final String modificationHref = "can't be split as it has no slashes";

    // when
    final Optional<String> optionalEId =
        TimeMachineFunctions.getEIdfromModificationHref(modificationHref);

    // then
    assertTrue(optionalEId.isEmpty());
  }

  @Test
  void getEIdFromModificationHref() {
    // given
    final String modificationHref =
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml";

    // when
    final Optional<String> optionalEId =
        TimeMachineFunctions.getEIdfromModificationHref(modificationHref);

    // then
    assertTrue(optionalEId.isPresent());
    assertTrue(optionalEId.get().equals("para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"));
  }

  /** findNodeByEId */
  @Test
  void returnEmptyIfNoNodeFoundByEId() {
    // given
    final String xmlText =
        """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
        <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                    eId="art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
                                    refersTo="aenderungsbefehl-ersetzen">
            Note that the eId above does not match what we're looking for, below.
        </akn:mod>
        """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final Optional<Node> optionalNode =
        TimeMachineFunctions.findNodeByEId("non-matching eId", optionalDocument.get());

    // then
    assertTrue(optionalNode.isEmpty());
  }

  @Test
  void findNodeByEId() {
    // given
    // given
    final String xmlText =
        """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
        <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                    eId="theEIdWereLookingFor"
                                    refersTo="aenderungsbefehl-ersetzen">
            Note the eId in the attributes
        </akn:mod>
        """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final Optional<Node> optionalNode =
        TimeMachineFunctions.findNodeByEId("theEIdWereLookingFor", optionalDocument.get());

    // then
    assertTrue(optionalNode.isPresent());
    assertTrue(optionalNode.get().getTextContent().contains("Note the eId in the attributes"));
  }

  /** getTextToBeReplaced */
  @Test
  void returnEmptyIfNoQuotedTextIsFound() {
    final String xmlText =
        """
      <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace
            </akn:mod>

            Note that no quoted texts were given

          </akn:body>
        """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final Optional<String> optionalTextToBeReplaced =
        TimeMachineFunctions.getTextToBeReplaced(optionalDocument.get());

    // then
    assertTrue(optionalTextToBeReplaced.isEmpty());
  }

  @Test
  void returnEmptyIfOnlyOneQuotedTextIsFound() {
    // given
    final String xmlText =
        """
      <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
                In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old text</akn:quotedText> with NO SECOND QUOTE GIVEN.
            </akn:mod>

            Note that no second quoted text is given

          </akn:body>
        """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final Optional<String> optionalTextToBeReplaced =
        TimeMachineFunctions.getTextToBeReplaced(optionalDocument.get());

    // then
    assertTrue(optionalTextToBeReplaced.isEmpty());
  }

  @Test
  void getTextToBeReplaced() {
    // given
    final String xmlText =
        """
      <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old text</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
            </akn:mod>
          </akn:body>
        """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final Optional<String> optionalTextToBeReplaced =
        TimeMachineFunctions.getTextToBeReplaced(optionalDocument.get());

    // then
    assertTrue(optionalTextToBeReplaced.isPresent());
    assertTrue(optionalTextToBeReplaced.get().equals("old text"));
  }

  /** getNewTextInReplacement() */
  @Test
  void returnEmptyIfNoQuotedTextIsFoundInReplacement() {
    // given
    final String xmlText =
        """
      <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace
            </akn:mod>

            Note that no quoted texts were given

          </akn:body>
        """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final Optional<String> optionalNewTextInReplacement =
        TimeMachineFunctions.getNewTextInReplacement(optionalDocument.get());

    // then
    assertTrue(optionalNewTextInReplacement.isEmpty());
  }

  @Test
  void returnEmptyIfOnlyOneQuotedTextIsFoundInReplacement() {
    // given
    final String xmlText =
        """
      <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old text</akn:quotedText> with ...
            </akn:mod>

            Note that there is only one "quotedText" element.

          </akn:body>
        """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final Optional<String> optionalNewTextInReplacement =
        TimeMachineFunctions.getNewTextInReplacement(optionalDocument.get());

    // then
    assertTrue(optionalNewTextInReplacement.isEmpty());
  }

  @Test
  void getNewTextInReplacement() {
    // given
    final String xmlText =
        """
      <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old text</akn:quotedText> with <akn:quotedText>new text</akn:quotedText>.
            </akn:mod>
          </akn:body>
        """;
    final Optional<Document> optionalDocument = XmlFunctions.loadXMLFromString(xmlText);

    // when
    final Optional<String> optionalNewTextInReplacement =
        TimeMachineFunctions.getNewTextInReplacement(optionalDocument.get());

    // then
    assertTrue(optionalNewTextInReplacement.isPresent());
    assertTrue(optionalNewTextInReplacement.get().equals("new text"));
  }
}
