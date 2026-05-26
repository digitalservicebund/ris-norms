package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.domain.entity.Bekanntmachung;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Rechtsetzungsdokument;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/** Validators for LDML.de XML files. */
@Slf4j
@Service
public class LdmlDeValidator {

  private final XsdSchemaService xsdSchemaService;

  public LdmlDeValidator(
    @Value("classpath:/LegalDocML.de/1.8.2/schema/legalDocML.de.xsl") Resource schematronXslt,
    XsdSchemaService xsdSchemaService
  ) {
    this.xsdSchemaService = xsdSchemaService;

    try {
      Source xsltSource = new StreamSource(schematronXslt.getInputStream());
      xsltSource.setSystemId(schematronXslt.getURL().toString());
    } catch (IOException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }

  /**
   * Validate the xsd schema for a Norm.
   *
   * @param norm the Norm to validate.
   * @throws LdmlDeNotValidException if a part of the norm is not valid according to the XSD.
   */
  public void validateXSDSchema(Norm norm) {
    norm.getDokumente().forEach(this::validateXSDSchema);
  }

  /**
   * Validate the xsd schema for a Dokument.
   *
   * @param dokument the Dokument to validate.
   * @throws LdmlDeNotValidException if the dokument is not valid according to the XSD.
   */
  public void validateXSDSchema(Dokument dokument) {
    var xmlContent = XmlMapper.toString(dokument.getDocument());

    try {
      switch (dokument) {
        case Regelungstext ignored -> this.parseAndValidateRegelungstext(xmlContent);
        case Bekanntmachung ignored -> this.parseAndValidateBekanntmachung(xmlContent);
        case OffeneStruktur ignored -> this.parseAndValidateOffeneStruktur(xmlContent);
        case Rechtsetzungsdokument ignored -> this.parseAndValidateRechtsetzungsdokument(
          xmlContent
        );
      }
    } catch (LdmlDeNotValidException exception) {
      throw new LdmlDeNotValidException(
        exception
          .getErrors()
          .stream()
          .map(error ->
            new LdmlDeNotValidException.ValidationError(
              error.type(),
              error.lineNumber(),
              error.columnNumber(),
              error.detail(),
              dokument.getManifestationEli().toString()
            )
          )
          .toList()
      );
    }
  }

  /**
   * Parses and validates the given LDML.de XML as a Regelungstext.
   *
   * @param ldmlDeString The XML string of the LDML.de document.
   * @return A Regelungstext instance.
   * @throws LdmlDeNotValidException if the document is not valid according to the XSD.
   */
  public Regelungstext parseAndValidateRegelungstext(String ldmlDeString) {
    Schema schema = xsdSchemaService.getRegelungstextSchema();
    Document document = parseDocument(ldmlDeString, schema);
    return new Regelungstext(document);
  }

  /**
   * Parses and validates the given LDML.de XML as a Bekanntmachung.
   *
   * @param ldmlDeString The XML string of the LDML.de document.
   * @return A Bekanntmachung instance.
   * @throws LdmlDeNotValidException if the document is not valid according to the XSD.
   */
  public Bekanntmachung parseAndValidateBekanntmachung(String ldmlDeString) {
    Schema schema = xsdSchemaService.getBekanntmachungSchema();
    Document document = parseDocument(ldmlDeString, schema);
    return new Bekanntmachung(document);
  }

  /**
   * Parses and validates the given LDML.de XML as an OffeneStruktur.
   *
   * @param ldmlDeString The XML string of the LDML.de document.
   * @return An OffeneStruktur instance.
   * @throws LdmlDeNotValidException if the document is not valid according to the XSD.
   */
  public OffeneStruktur parseAndValidateOffeneStruktur(String ldmlDeString) {
    Schema schema = xsdSchemaService.getOffeneStrukturSchema();
    Document document = parseDocument(ldmlDeString, schema);
    return new OffeneStruktur(document);
  }

  /**
   * Parses and validates the given LDML.de XML as a Rechtsetzungsdokument.
   *
   * @param ldmlDeString The XML string of the LDML.de document.
   * @return An Rechtsetzungsdokument instance.
   * @throws LdmlDeNotValidException if the document is not valid according to the XSD.
   */
  public Rechtsetzungsdokument parseAndValidateRechtsetzungsdokument(String ldmlDeString) {
    Schema schema = xsdSchemaService.getRechtsetzungsdokumentSchema();
    Document document = parseDocument(ldmlDeString, schema);
    return new Rechtsetzungsdokument(document);
  }

  // Helper method to parse an XML string into a Document with validation.
  private Document parseDocument(String xmlString, Schema schema) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setSchema(schema);
    factory.setIgnoringElementContentWhitespace(true);

    List<SAXParseException> parsingExceptions = new ArrayList<>();
    ErrorHandler errorHandler = new ErrorHandler() {
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

    Document document = XmlMapper.toDocument(xmlString, factory, errorHandler);
    if (!parsingExceptions.isEmpty()) {
      var validationErrors = parsingExceptions
        .stream()
        .map(e ->
          new LdmlDeNotValidException.ValidationError(
            URI.create(LdmlDeNotValidException.TYPE + "/").resolve(e.getMessage().split(":")[0]),
            e.getLineNumber(),
            e.getColumnNumber(),
            e.getMessage()
          )
        )
        .toList();

      log.debug("Validation errors: {}", validationErrors);

      throw new LdmlDeNotValidException(validationErrors);
    }
    return document;
  }
}
