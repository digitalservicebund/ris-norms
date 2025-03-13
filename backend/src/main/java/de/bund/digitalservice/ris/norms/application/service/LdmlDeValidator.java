package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeSchematronException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
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
import net.sf.saxon.TransformerFactoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/** Validators for LDML.de XML files. */
@Service
public class LdmlDeValidator {

  private final Transformer schematronValidationTransformer;
  private final XsdSchemaService xsdSchemaService;

  public LdmlDeValidator(
    @Value("classpath:/LegalDocML.de/1.7.2/schema/legalDocML.de.xsl") Resource schematronXslt,
    XsdSchemaService xsdSchemaService
  ) {
    this.xsdSchemaService = xsdSchemaService;

    try {
      Source xsltSource = new StreamSource(schematronXslt.getInputStream());
      xsltSource.setSystemId(schematronXslt.getURL().toString());
      this.schematronValidationTransformer =
      new TransformerFactoryImpl().newTransformer(xsltSource);
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
    norm
      .getRegelungstexte()
      .stream()
      .map(Regelungstext::getDocument)
      .map(XmlMapper::toString)
      .forEach(this::parseAndValidateRegelungstext);
    norm
      .getOffenestrukturen()
      .stream()
      .map(OffeneStruktur::getDocument)
      .map(XmlMapper::toString)
      .forEach(this::parseAndValidateOffeneStruktur);
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
      throw new LdmlDeNotValidException(
        parsingExceptions
          .stream()
          .map(e ->
            new LdmlDeNotValidException.ValidationError(
              URI.create(e.getMessage().split(":")[0]),
              e.getLineNumber(),
              e.getColumnNumber(),
              e.getMessage()
            )
          )
          .toList()
      );
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
    // For a Norm, validate each contained Regelungstext.
    norm.getRegelungstexte().forEach(this::validateSchematron);
    // TODO: (Victor del Campo, 2025-02-02) add also schematron validation for offene-struktur
  }

  /**
   * Validate the regelungstext against the schematron rules. Throws if a rule is not fulfilled.
   *
   * @param regelungstext the Regelungstext to validate
   * @throws XmlProcessingException A problem occurred during the processing of the XML
   * @throws LdmlDeSchematronException A Schematron rule is violated
   */
  public void validateSchematron(Regelungstext regelungstext) {
    Source xmlSource = new DOMSource(regelungstext.getDocument());

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

    var xPathEIdCache = new HashMap<String, String>();
    var errors = Stream
      .concat(failedAssertMessages, successfulReportMessages)
      .filter(node -> {
        // TODO: (Malte Laukötter, 2024-09-23) remove once we support elis with point-in-time-manifestation
        String ruleId = Optional
          .ofNullable(node.getAttributes().getNamedItem("id"))
          // for some rules (the verkündungsfassung specific once) the rule id is not added to the sch:assert but only to the sch:rule so we need to get the id from the svrl:fired-rule before the svrl:failed-assert in the validation result
          .orElseGet(() -> node.getPreviousSibling().getAttributes().getNamedItem("id"))
          .getNodeValue();

        var rulesCheckingPointInTimeManifestation = List.of(
          "SCH-00520-010",
          "SCH-00550-010",
          "SCH-VERK-valueLiterals.manifestation.FRBRthis",
          "SCH-VERK-valueLiterals.manifestation.FRBRuri"
        );

        return !rulesCheckingPointInTimeManifestation.contains(ruleId);
      })
      .filter(node ->
        // Allow warnings
        Optional
          .ofNullable(node.getAttributes().getNamedItem("role"))
          .map(role -> !role.getNodeValue().equals("warn"))
          // This should not happen, but if it does, assume the message should be included, i.e. only exclude items
          // if they're explicitly declared as "warn"
          .orElse(true)
      )
      .map(node -> {
        // The location includes an XPath with expanded QNames
        // (Q{namespace}<localPart>).
        var xPath = node.getAttributes().getNamedItem("location").getNodeValue();

        // Find the eId of the node responsible for this problem. Sometimes the location
        // points to an attribute, so we might need to move up in the element tree to
        // find the eId.
        String eId = xPathEIdCache.computeIfAbsent(
          xPath + "/ancestor-or-self::*/@eId",
          key ->
            NodeParser
              .getNodesFromExpression(key, regelungstext.getDocument())
              .stream()
              .map(Node::getNodeValue)
              .reduce("", (a, b) -> a.length() > b.length() ? a : b)
        );

        String ruleId = Optional
          .ofNullable(node.getAttributes().getNamedItem("id"))
          // for some rules (the verkündungsfassung specific once) the rule id is not added to the sch:assert but only to the sch:rule so we need to get the id from the svrl:fired-rule before the svrl:failed-assert in the validation result
          .orElseGet(() -> node.getPreviousSibling().getAttributes().getNamedItem("id"))
          .getNodeValue();

        return new LdmlDeSchematronException.ValidationError(
          "/errors/ldml-de-not-schematron-valid/%s/%s".formatted(node.getLocalName(), ruleId),
          xPath,
          node.getTextContent(),
          eId
        );
      })
      .toList();

    if (!errors.isEmpty()) {
      throw new LdmlDeSchematronException(errors);
    }
  }
}
