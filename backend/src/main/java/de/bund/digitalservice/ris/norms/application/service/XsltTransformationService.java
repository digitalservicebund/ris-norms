package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.TransformationException;
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

  private static final String SHOW_METADATA = "show-metadata";
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
  public String transformLegalDocMlToHtml(TransformLegalDocMlToHtmlUseCase.Query query) {
    try {
      Source xsltSource = new StreamSource(xslt.getInputStream());
      // Fix the location of the source so xsl:import works
      xsltSource.setSystemId(xslt.getURL().toString());
      Transformer transformer = new TransformerFactoryImpl().newTransformer(xsltSource);
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setParameter(SHOW_METADATA, query.showMetadata());
      transformer.setParameter("outputMode", "html");

      StringWriter output = new StringWriter();
      transformer.transform(
          new StreamSource(new ByteArrayInputStream(query.xml().getBytes())),
          new StreamResult(output));
      return output.toString();
    } catch (IOException | TransformerException e) {
      throw new TransformationException(e.getMessage(), e);
    }
  }
}
