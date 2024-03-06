package de.bund.digitalservice.ris.norms.application.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

class TimeMachineFunctionsTest {

  /** applyTimeMachine() */

  @Test
  void returnEmptyIfOnlyOneQuotedText() {
    // given
    // given
    final String amendingLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace with <akn:quotedText>new</akn:quotedText>.
            </akn:mod>

            only one quotedText

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

    final Document amendingLaw = XmlFunctions.stringToXmlDocument(amendingLawXmlText).get();
    final Document targetLaw = XmlFunctions.stringToXmlDocument(targetLawXmlText).get();
    // when
    final Optional<Document> resultingLaw =
        TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);

    // then
    assertTrue(resultingLaw.isEmpty());
  }

  @Test
  void returnEmptyIfEIdNotFoundInTargetLaw() {
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
    final Document amendingLaw = XmlFunctions.stringToXmlDocument(amendingLawXmlText).get();
    final Document targetLaw =
        XmlFunctions.stringToXmlDocument("<?xml version=\"1.0\" encoding=\"UTF-8\"?><target/>")
            .get();
    // when
    final Optional<Document> optionalResultingLaw =
        TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);
    // then
    assertTrue(optionalResultingLaw.isEmpty());
  }

  @Test
  void returnEmptyIfTheresNoModificationHref() {
    // given
    // given
    final String amendingLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:mod>
             In <akn:ref>paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
            </akn:mod>

            no href attribute in "ref" tag

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

    final Document amendingLaw = XmlFunctions.stringToXmlDocument(amendingLawXmlText).get();
    final Document targetLaw = XmlFunctions.stringToXmlDocument(targetLawXmlText).get();
    // when
    final Optional<Document> resultingLaw =
        TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);

    // then
    assertTrue(resultingLaw.isEmpty());
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

    final Document amendingLaw = XmlFunctions.stringToXmlDocument(amendingLawXmlText).get();
    final Document targetLaw = XmlFunctions.stringToXmlDocument(targetLawXmlText).get();
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
        XmlFunctions.stringToXmlDocument("<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>")
            .get();
    final Document targetLaw =
        XmlFunctions.stringToXmlDocument("<?xml version=\"1.0\" encoding=\"UTF-8\"?><target/>")
            .get();
    // when
    final Optional<Document> resultingLaw =
        TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);
    // then
    assertEquals(resultingLaw.get().toString(), targetLaw.toString());
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
    final Document optionalAmendingLaw = XmlFunctions.stringToXmlDocument(amendingLawXmlText).get();
    final Document optionalTargetLaw = XmlFunctions.stringToXmlDocument(targetLawXmlText).get();
    final Document expectedResultingLaw =
        XmlFunctions.stringToXmlDocument(expectedResultingLawXmlText).get();

    // when applying the TimeMachine
    final Optional<Document> resultingLaw =
        TimeMachineFunctions.applyTimeMachine(optionalAmendingLaw, optionalTargetLaw);

    // the result contains the new text in place of the old text
    assertTrue(resultingLaw.isPresent());
    assertEquals(resultingLaw.get().toString(), expectedResultingLaw.toString());
  }

  /** getFirstModification() */
  @Test
  void returnEmptyIfThereIsNoFirstModification() {
    // given
    final Document amendingLawWithoutModification =
        XmlFunctions.stringToXmlDocument("<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>")
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
    final Document amendingLawWithModification = XmlFunctions.stringToXmlDocument(xmlText).get();
    // when
    final Optional<Node> firstModificationNode =
        TimeMachineFunctions.getFirstModification(amendingLawWithModification);
    // then
    assertTrue(firstModificationNode.isPresent());
    assertEquals("§ 20 Absatz 1 Satz 2 wird ersetzt.", 
        firstModificationNode.get().getTextContent());
  }

  /** findHrefInModificationNode() */
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

    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);
    final Optional<Node> optionalModificationNode =
        XmlFunctions.getNode("//*[local-name()='mod']", optionalDocument.get());

    // when
    final Optional<String> optionalHref =
        TimeMachineFunctions.findHrefInModificationNode(optionalModificationNode.get());

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

    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);
    final Optional<Node> optionalModificationNode =
        XmlFunctions.getNode("//*[local-name()='mod']", optionalDocument.get());

    // when
    final Optional<String> optionalHref =
        TimeMachineFunctions.findHrefInModificationNode(optionalModificationNode.get());

    // then
    assertTrue(optionalHref.isPresent());
    assertEquals("some/href/with/slashes.xml", optionalHref.get());
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
    assertEquals("para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1", optionalEId.get());
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
    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

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
    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

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
    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

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
    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

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
    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

    // when
    final Optional<String> optionalTextToBeReplaced =
        TimeMachineFunctions.getTextToBeReplaced(optionalDocument.get());

    // then
    assertTrue(optionalTextToBeReplaced.isPresent());
    assertEquals("old text", optionalTextToBeReplaced.get());
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
    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

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
    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

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
    final Optional<Document> optionalDocument = XmlFunctions.stringToXmlDocument(xmlText);

    // when
    final Optional<String> optionalNewTextInReplacement =
        TimeMachineFunctions.getNewTextInReplacement(optionalDocument.get());

    // then
    assertTrue(optionalNewTextInReplacement.isPresent());
    assertEquals("new text", optionalNewTextInReplacement.get());
  }
}
