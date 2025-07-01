package de.bund.digitalservice.ris.norms.adapter.input.restapi.formatter;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * ResponseBodyAdvice that formats XML responses
 */
@NullMarked
@Slf4j
@ControllerAdvice
public class XmlFormattingResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(
    MethodParameter returnType,
    Class<? extends HttpMessageConverter<?>> converterType
  ) {
    return true;
  }

  @Nullable
  @Override
  public Object beforeBodyWrite(
    @Nullable Object body,
    MethodParameter returnType,
    MediaType selectedContentType,
    Class<? extends HttpMessageConverter<?>> selectedConverterType,
    ServerHttpRequest request,
    ServerHttpResponse response
  ) {
    if (
      selectedContentType.isCompatibleWith(MediaType.APPLICATION_XML) &&
      body instanceof String xmlBody
    ) {
      return formatXml(xmlBody);
    }
    return body;
  }

  private String formatXml(String xml) {
    try {
      Document document = XmlMapper.toDocument(xml);

      DOMImplementationLS impl = (DOMImplementationLS) document
        .getImplementation()
        .getFeature("LS", "3.0");
      LSSerializer serializer = impl.createLSSerializer();

      serializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
      serializer.getDomConfig().setParameter("xml-declaration", Boolean.TRUE);

      LSOutput lsOutput = impl.createLSOutput();
      StringWriter writer = new StringWriter();
      lsOutput.setCharacterStream(writer);

      serializer.write(document, lsOutput);
      return writer.toString();
    } catch (Exception e) {
      log.error("Failed to format XML response", e);
      return xml;
    }
  }
}
