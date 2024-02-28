package de.bund.digitalservice.ris.norms.domain.functions;

import static org.junit.Assert.assertTrue;

import java.util.Optional;
import org.junit.Test;
import org.w3c.dom.Document;

public class TimeMachineFunctionsTest {

  final String minimalXmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";

  //   @Test
  //   public void XmlDocumentsGoInAndOut() {
  //     // given
  //     final Document amendingLaw = TimeMachineFunctions.loadXMLFromString(minimalXmlString);
  //   }

  @Test
  public void DocumentGeneratedFromStringMustNotBeEmpty() {
    // given
    final String input = minimalXmlString;
    // when
    final Optional<Document> result = TimeMachineFunctions.loadXMLFromString(input);
    // then
    assertTrue(result.isPresent());
  }
}
