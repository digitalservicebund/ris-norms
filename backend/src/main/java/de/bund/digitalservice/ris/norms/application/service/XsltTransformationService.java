package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.sf.saxon.TransformerFactoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/** Service for transforming LegalDocML.de to HTML. */
@Service
public class XsltTransformationService implements TransformLegalDocMlToHtmlUseCase {

  private static final String LEGISLATION_IDENTIFIER_PARAMETER = "legislation-identifier";
  private final Resource xslt;

  public XsltTransformationService(@Value("classpath:XSLT/html/legislation.xslt") Resource xslt) {
    this.xslt = xslt;
  }

  /**
   * Transform LegalDocMl.de to HTML
   *
   * @param query The query specifying the XML of the law to be transformed to HTML.
   * @return A html version of the law
   */
  public String transformLegalDocMlToHtml(TransformLegalDocMlToHtmlUseCase.Query query)
      throws TransformLegalDocMlToHtmlUseCase.XmlTransformationException {
    try {
      Transformer transformer =
          new TransformerFactoryImpl().newTransformer(new StreamSource(xslt.getInputStream()));
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setParameter(LEGISLATION_IDENTIFIER_PARAMETER, query.eli());
      transformer.setParameter("outputMode", "html");

      StringWriter output = new StringWriter();
      transformer.transform(
          new StreamSource(new ByteArrayInputStream(query.xml().getBytes())),
          new StreamResult(output));
      return output.toString();
    } catch (IOException | TransformerConfigurationException e) {
      throw new RuntimeException(e);
    } catch (TransformerException e) {
      // This exception can happen when a malformed xml is provided to the service. Therefore, we
      // want to send the
      // exception to the thing calling this service.
      throw new TransformLegalDocMlToHtmlUseCase.XmlTransformationException(
          e.getMessageAndLocation());
    }
  }
}
