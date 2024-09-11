package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** Validator for LDML.de XML files. */
@Service
public class LdmlDeValidator {
  private final Resource xsdSchema;

  public LdmlDeValidator(
      @Value("classpath:schema/fixtures/ldml1.6_ds_regelungstext.xsd") Resource xsdSchema) {
    this.xsdSchema = xsdSchema;
  }

  /**
   * Parses the given text as a LDML.de XML. It validates the XSD Schema and parses the norm
   * namespace-aware.
   *
   * @param ldmlDeString The xml-string of the LDML.de document.
   * @return The parsed norm (namespace-aware)
   * @throws LdmlDeNotValidException The document is not valid according to the XSD-Schema
   */
  public Norm parseAndValidate(String ldmlDeString) throws LdmlDeNotValidException {
    Source schemaSource = null;
    try {
      schemaSource = new StreamSource(xsdSchema.getInputStream());
      schemaSource.setSystemId(xsdSchema.getURL().toString());
    } catch (IOException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }

    Schema schema = null;
    try {
      schema = SchemaFactory.newDefaultInstance().newSchema(schemaSource);
    } catch (SAXException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    factory.setSchema(schema);
    factory.setIgnoringElementContentWhitespace(true);

    List<SAXParseException> parsingExceptions = new ArrayList<>();
    ErrorHandler errorHandler =
        new ErrorHandler() {
          @Override
          public void warning(SAXParseException exception) {
            parsingExceptions.add(exception);
          }

          @Override
          public void error(SAXParseException exception) {
            parsingExceptions.add(exception);
          }

          @Override
          public void fatalError(SAXParseException exception) {
            parsingExceptions.add(exception);
          }
        };

    var result = XmlMapper.toDocument(ldmlDeString, factory, errorHandler);

    if (!parsingExceptions.isEmpty()) {
      throw new LdmlDeNotValidException(parsingExceptions);
    }

    return Norm.builder().document(result).build();
  }
}
