package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class DocumentUtilsTest {
  @Test
  void cloneDocument() {
    Document document =
        XmlMapper.toDocument(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>applied-passive-modification</test>");
    Document clone = DocumentUtils.cloneDocument(document);
    assertThat(clone.isEqualNode(document)).isTrue();
  }
}
