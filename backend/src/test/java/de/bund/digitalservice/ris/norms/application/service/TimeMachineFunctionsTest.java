package de.bund.digitalservice.ris.norms.application.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.w3c.dom.Document;

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
}
