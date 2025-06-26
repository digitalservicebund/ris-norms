package de.bund.digitalservice.ris.norms.adapter.input.restapi.formatter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class XmlFormattingResponseBodyAdviceTest {

  final XmlFormattingResponseBodyAdvice xmlFormatter = new XmlFormattingResponseBodyAdvice();

  @Test
  void shouldReturnTransformedXml() {
    String compactXml =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?><akn:doc><akn:meta><akn:identification><akn:FRBRWork><akn:FRBRthis value=\"test\"/></akn:FRBRWork></akn:identification></akn:meta><akn:body><akn:article eId=\"art-1\"><akn:heading>Test</akn:heading></akn:article></akn:body></akn:doc>";

    var result = xmlFormatter.beforeBodyWrite(
      compactXml,
      null,
      MediaType.APPLICATION_XML,
      null,
      null,
      null
    );

    assertThat((String) result).isEqualToIgnoringWhitespace(
      """
      <?xml version="1.0" encoding="UTF-8"?>
      <akn:doc>
        <akn:meta>
          <akn:identification>
            <akn:FRBRWork>
              <akn:FRBRthis value="test"/>
            </akn:FRBRWork>
          </akn:identification>
        </akn:meta>
        <akn:body>
          <akn:article eId="art-1">
            <akn:heading>Test</akn:heading>
          </akn:article>
        </akn:body>
      </akn:doc>
      """
    );
  }

  @Test
  void shouldReturnOriginalWhenXmlIsInvalid() {
    String invalidXml = "<akn:doc><invalid xml"; // malformed

    var result = xmlFormatter.beforeBodyWrite(
      invalidXml,
      null,
      MediaType.APPLICATION_XML,
      null,
      null,
      null
    );

    assertThat(result).isEqualTo(invalidXml);
  }
}
