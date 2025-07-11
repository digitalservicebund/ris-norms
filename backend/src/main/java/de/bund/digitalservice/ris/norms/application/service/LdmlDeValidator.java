package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeSchematronException;
import de.bund.digitalservice.ris.norms.domain.entity.Bekanntmachung;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.DokumentType;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Rechtsetzungsdokument;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.InvalidDokumentTypeException;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.TransformerFactoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/** Validators for LDML.de XML files. */
@Slf4j
@Service
public class LdmlDeValidator {

  private static final List<String> DISABLED_SCHEMATRON_RULES = List.of(
    "SCH-00110-005",
    "SCH-00071-005"
  );

  private final Transformer schematronValidationTransformer;
  private final XsdSchemaService xsdSchemaService;

  public LdmlDeValidator(
    @Value("classpath:/LegalDocML.de/1.8.1/schema/legalDocML.de.xsl") Resource schematronXslt,
    XsdSchemaService xsdSchemaService
  ) {
    this.xsdSchemaService = xsdSchemaService;

    try {
      Source xsltSource = new StreamSource(schematronXslt.getInputStream());
      xsltSource.setSystemId(schematronXslt.getURL().toString());
      this.schematronValidationTransformer = new TransformerFactoryImpl().newTransformer(
        xsltSource
      );
    } catch (IOException | TransformerConfigurationException e) {
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
        case Regelungstext regelungstext -> this.parseAndValidateRegelungstext(xmlContent);
        case Bekanntmachung bekanntmachung -> this.parseAndValidateBekanntmachung(xmlContent);
        case OffeneStruktur offeneStruktur -> this.parseAndValidateOffeneStruktur(xmlContent);
        case Rechtsetzungsdokument rechtsetzungsdokument -> this.parseAndValidateRechtsetzungsdokument(
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
   * Parse and validate the given dokument. The concrete dokument type is determined based on the dokumentName.
   *
   * @param dokumentName the file name of the dokument
   * @param ldmlDeString the xml string of the dokument
   * @return the parsed and validated dokument.
   * @throws LdmlDeNotValidException the dokument is not valid
   * @throws InvalidDokumentTypeException the dokument name could not be used to determine the dokument type
   */
  public Dokument parseAndValidateDokument(String dokumentName, String ldmlDeString) {
    try {
      return switch (DokumentType.getByFileName(dokumentName)) {
        case RECHTSETZUNGSDOKUMENT -> parseAndValidateRechtsetzungsdokument(ldmlDeString);
        case REGELUNGSTEXT_VERKUENDUNG -> parseAndValidateRegelungstext(ldmlDeString);
        case ANLAGE_REGELUNGSTEXT -> parseAndValidateOffeneStruktur(ldmlDeString);
        case BEKANNTMACHUNGSTEXT -> parseAndValidateBekanntmachung(ldmlDeString);
      };
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
              dokumentName
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

  /**
   * Validate the schematron rules for a Norm.
   *
   * @param norm the Norm to validate.
   * @throws XmlProcessingException if an error occurs during processing.
   * @throws LdmlDeSchematronException if a Schematron rule is violated.
   */
  public void validateSchematron(Norm norm) {
    norm.getDokumente().forEach(this::validateSchematron);
  }

  /**
   * Validate a Dokument against the schematron rules. Throws if a rule is not fulfilled.
   *
   * @param dokument the Dokument to validate
   * @throws XmlProcessingException A problem occurred during the processing of the XML
   * @throws LdmlDeSchematronException A Schematron rule is violated
   */
  public void validateSchematron(Dokument dokument) {
    Source xmlSource = new DOMSource(dokument.getDocument());

    var result = new DOMResult();
    try {
      schematronValidationTransformer.transform(xmlSource, result);
    } catch (TransformerException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }

    if (!(result.getNode() instanceof Document resultDoc)) {
      throw new XmlProcessingException("Result is not a document.", null);
    }

    var failedAsserts = resultDoc.getElementsByTagNameNS(
      "http://purl.oclc.org/dsdl/svrl",
      "failed-assert"
    );
    // successful-reports are often used for warnings, and do not indicate that an assert was not
    // failed
    var successfulReports = resultDoc.getElementsByTagNameNS(
      "http://purl.oclc.org/dsdl/svrl",
      "successful-report"
    );

    var failedAssertMessages = NodeParser.nodeListToList(failedAsserts).stream();
    var successfulReportMessages = NodeParser.nodeListToList(successfulReports).stream();

    var xPathEIdCache = new HashMap<String, EId>();
    var errors = Stream.concat(failedAssertMessages, successfulReportMessages)
      .filter(node -> {
        // Allow warnings
        boolean isNotWarning = Optional.ofNullable(node.getAttributes().getNamedItem("role"))
          .map(role -> !role.getNodeValue().equals("warn"))
          // This should not happen, but if it does, assume the message should be included, i.e. only exclude items
          // if they're explicitly declared as "warn"
          .orElse(true);

        // Ignore disabled rules
        String ruleId = Optional.ofNullable(node.getAttributes().getNamedItem("id"))
          .orElseGet(() -> node.getPreviousSibling().getAttributes().getNamedItem("id"))
          .getNodeValue();
        boolean isNotIgnored = !DISABLED_SCHEMATRON_RULES.contains(ruleId);

        return isNotWarning && isNotIgnored;
      })
      .map(node -> {
        // The location includes an XPath with expanded QNames
        // (Q{namespace}<localPart>).
        var xPath = node.getAttributes().getNamedItem("location").getNodeValue();

        // Find the eId of the node responsible for this problem. Sometimes the location
        // points to an attribute, so we might need to move up in the element tree to
        // find the eId.
        EId eId = xPathEIdCache.computeIfAbsent(xPath + "/ancestor-or-self::*/@eId", key ->
          new EId(
            NodeParser.getNodesFromExpression(key, dokument.getDocument())
              .stream()
              .map(Node::getNodeValue)
              .reduce("", (a, b) -> a.length() > b.length() ? a : b)
          )
        );

        String ruleId = Optional.ofNullable(node.getAttributes().getNamedItem("id"))
          // for some rules (the verkündungsfassung specific once) the rule id is not added to the sch:assert but only to the sch:rule so we need to get the id from the svrl:fired-rule before the svrl:failed-assert in the validation result
          .orElseGet(() -> node.getPreviousSibling().getAttributes().getNamedItem("id"))
          .getNodeValue();

        return new LdmlDeSchematronException.ValidationError(
          "/errors/ldml-de-not-schematron-valid/%s/%s".formatted(node.getLocalName(), ruleId),
          xPath,
          node.getTextContent(),
          eId.toString(),
          dokument.getManifestationEli().toString()
        );
      })
      .toList();

    if (!errors.isEmpty()) {
      throw new LdmlDeSchematronException(errors);
    }
  }
}
