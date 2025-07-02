package de.bund.digitalservice.ris.norms.adapter.input.restapi.formatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

class XmlFormattingResponseBodyAdviceTest {

  final XmlFormattingResponseBodyAdvice xmlFormatter = new XmlFormattingResponseBodyAdvice();

  @Test
  void shouldReturnTransformedXml() {
    String compactXml =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?><akn:doc><akn:meta><akn:identification><akn:FRBRWork><akn:FRBRthis value=\"test\"/></akn:FRBRWork></akn:identification></akn:meta><akn:body><akn:article eId=\"art-1\"><akn:heading>Test</akn:heading></akn:article></akn:body></akn:doc>";

    Class<? extends HttpMessageConverter<?>> dummyConverterType = StringHttpMessageConverter.class;
    ServerHttpRequest dummyRequest = mock(ServerHttpRequest.class);
    ServerHttpResponse dummyResponse = mock(ServerHttpResponse.class);

    Object result = xmlFormatter.beforeBodyWrite(
      compactXml,
      null,
      MediaType.APPLICATION_XML,
      dummyConverterType,
      dummyRequest,
      dummyResponse
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

    Class<? extends HttpMessageConverter<?>> dummyConverterType = StringHttpMessageConverter.class;
    ServerHttpRequest dummyRequest = mock(ServerHttpRequest.class);
    ServerHttpResponse dummyResponse = mock(ServerHttpResponse.class);

    Object result = xmlFormatter.beforeBodyWrite(
      invalidXml,
      null,
      MediaType.APPLICATION_XML,
      dummyConverterType,
      dummyRequest,
      dummyResponse
    );

    assertThat(result).isEqualTo(invalidXml);
  }
}
