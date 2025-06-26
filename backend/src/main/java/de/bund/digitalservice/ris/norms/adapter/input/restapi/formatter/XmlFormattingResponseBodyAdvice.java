package de.bund.digitalservice.ris.norms.adapter.input.restapi.formatter;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

/**
 * ResponseBodyAdvice that formats XML responses
 */
@ControllerAdvice
public class XmlFormattingResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(
    MethodParameter returnType,
    Class<? extends HttpMessageConverter<?>> converterType
  ) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(
    Object body,
    MethodParameter returnType,
    MediaType selectedContentType,
    Class<? extends HttpMessageConverter<?>> selectedConverterType,
    ServerHttpRequest request,
    ServerHttpResponse response
  ) {
    if (
      selectedContentType != null &&
      selectedContentType.isCompatibleWith(MediaType.APPLICATION_XML) &&
      body instanceof String xmlBody
    ) {
      return formatXml(xmlBody);
    }
    return body;
  }

  private String formatXml(String xml) {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      dbf.setExpandEntityReferences(false);
      Document document = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));

      DOMImplementationLS impl = (DOMImplementationLS) document
        .getImplementation()
        .getFeature("LS", "3.0");
      LSSerializer serializer = impl.createLSSerializer();

      serializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
      serializer.getDomConfig().setParameter("xml-declaration", Boolean.FALSE);

      LSOutput lsOutput = impl.createLSOutput();
      StringWriter writer = new StringWriter();
      lsOutput.setCharacterStream(writer);

      serializer.write(document, lsOutput);
      return writer.toString();
    } catch (Exception e) {
      return xml;
    }
  }
}
