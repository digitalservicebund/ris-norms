package de.bund.digitalservice.ris.norms.domain.functions;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.w3c.dom.Document;

public class TimeMachineFunctionsTest {
    // @Test
    // public void XmlDocumentsGoInAndOut() {}

    @Test
    public void DocumentGeneratedFromString() {
        // given
        final String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";
        // when
        final Document result = TimeMachineFunctions.readXmlDocumentFromString(input);
        // then
        assertNotNull(result);

    }
}
