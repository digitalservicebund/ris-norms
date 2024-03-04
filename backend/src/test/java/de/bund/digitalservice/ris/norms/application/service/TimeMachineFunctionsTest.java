package de.bund.digitalservice.ris.norms.application.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class TimeMachineFunctionsTest {

  /** applyTimeMachine() */
  @Test
  public void xmlDocumentsGoInAndOut() {
    // given
    final String minimalXmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";
    final Document amendingLaw = XmlFunctions.loadXMLFromString(minimalXmlString).get();
    final Document targetLaw = XmlFunctions.loadXMLFromString(minimalXmlString).get();
    // when
    final Document result = TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);
    // then
    assertNotNull(result);
  }

  @Test
  public void targetLawStaysUnchangedIfAmendingLawHasNoModifications() {
    // given
    final Document amendingLaw =
        XmlFunctions.loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>")
            .get();
    final Document targetLaw =
        XmlFunctions.loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><target/>").get();
    // when
    final Document result = TimeMachineFunctions.applyTimeMachine(amendingLaw, targetLaw);
    // then
    assertTrue(result.equals(targetLaw));
  }

  /** getFirstModification()  */
  @Test
  public void returnEmptyIfThereIsNoFirstModification(){
    // given
    final Document amendingLawWithoutModification =
        XmlFunctions.loadXMLFromString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>")
            .get();
    // when
    final Optional<Node> firstModificationNode = TimeMachineFunctions.getFirstModification(amendingLawWithoutModification);
    // then
    assertTrue(firstModificationNode.isEmpty());

  }

  @Test
  public void returnModificationNodeIfThereIsAFirstModification(){
    // given
    final String xmlText = """
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
          <akn:mod>§ 20 Absatz 1 Satz 2 wird ersetzt.</akn:mod>
        """;
    final Document amendingLawWithModification =
        XmlFunctions.loadXMLFromString(xmlText)
            .get();
    // when
    final Optional<Node> firstModificationNode = TimeMachineFunctions.getFirstModification(amendingLawWithModification);
    // then
    assertTrue(firstModificationNode.isPresent());
    // TODO:
    // assertTrue(firstModificationNode.get().toString().equals(xmlText));
  }

  /** findHrefInModification() */
  @Test
  public void returnEmptyIfNoHrefInModification(){
    // given
    final String xmlText = """
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
    final Optional<Node> optionalModificationNode = XmlFunctions.getNode("//*[local-name()='mod']", optionalDocument.get());

    // when
    final Optional<String> optionalHref = XmlFunctions.findHrefInModificationNode(optionalModificationNode.get());
    
    // then
    assertTrue(optionalHref.isEmpty());
  }

  @Test
  public void returnHrefInModification(){
    // given
    final String xmlText = """
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
    final Optional<Node> optionalModificationNode = XmlFunctions.getNode("//*[local-name()='mod']", optionalDocument.get());

    // when
    final Optional<String> optionalHref = XmlFunctions.findHrefInModificationNode(optionalModificationNode.get());
    
    // then
    assertTrue(optionalHref.isPresent());
    assertTrue(optionalHref.get().equals("some/href/with/slashes.xml"));
  }

  /** getEIdFromModificationHref */
  @Test
  public void getEIdFromModificationHref(){
    // given
    final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml";

    // when
    final String eId = getEIdFromModificationHref(eli);

    //then
    assertTrue(eId.equals("para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")));
  }
}
