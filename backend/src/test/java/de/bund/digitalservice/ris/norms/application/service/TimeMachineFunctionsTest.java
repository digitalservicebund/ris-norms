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
    assertTrue(firstModificationNode.get().toString().equals(xmlText));

  }
}
