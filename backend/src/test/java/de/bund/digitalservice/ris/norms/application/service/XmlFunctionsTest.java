package de.bund.digitalservice.ris.norms.application.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.Test;
import org.w3c.dom.Document;

public class XmlFunctionsTest {

  @Test
  public void documentGeneratedFromValidXmlStringMustNotBeEmpty() {
    // given
    final String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";
    // when
    final Optional<Document> result = XmlFunctions.loadXMLFromString(input);
    // then
    assertTrue(result.isPresent());
  }

  @Test
  public void documentGeneratedFromInValidXmlStringMustBeEmpty() {
    // given
    final String input = "invalid XML; does not even have an XML declaration";
    // when
    final Optional<Document> result = XmlFunctions.loadXMLFromString(input);
    // then
    assertTrue(result.isEmpty());
  }
}
