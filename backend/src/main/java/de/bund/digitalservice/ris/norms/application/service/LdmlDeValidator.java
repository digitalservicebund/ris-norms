package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeSchematronException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import net.sf.saxon.TransformerFactoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** Validators for LDML.de XML files. */
@Service
public class LdmlDeValidator {

  private final Resource schematronXslt;
  private final Resource xsdSchema;

  public LdmlDeValidator(
    @Value("classpath:schema/fixtures/legalDocML.de.xsl") Resource schematronXslt,
    @Value("classpath:schema/fixtures/ldml1.6_ds_regelungstext.xsd") Resource xsdSchema
  ) {
    this.schematronXslt = schematronXslt;
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
  public Norm parseAndValidate(String ldmlDeString) {
    Schema schema = null;
    try {
      Source schemaSource = new StreamSource(xsdSchema.getInputStream());
      schemaSource.setSystemId(xsdSchema.getURL().toString());
      schema = SchemaFactory.newDefaultInstance().newSchema(schemaSource);
    } catch (IOException | SAXException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
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

    var result = XmlMapper.toDocument(ldmlDeString, factory, errorHandler);

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

    return Norm.builder().document(result).build();
  }

  /**
   * Validate the norm against the schematron rules. Throws if a rule is not fulfilled.
   *
   * @param norm the norm to validate
   * @throws XmlProcessingException A problem occurred during the processing of the XML
   * @throws LdmlDeSchematronException A Schematron rule is violated
   */
  public void validateSchematron(Norm norm) {
    Transformer transformer = null;
    try {
      Source xsltSource = new StreamSource(schematronXslt.getInputStream());
      xsltSource.setSystemId(schematronXslt.getURL().toString());
      transformer = new TransformerFactoryImpl().newTransformer(xsltSource);
    } catch (IOException | TransformerConfigurationException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }

    String xmlText = XmlMapper.toString(norm.getDocument());
    Source xmlSource = new StreamSource(new ByteArrayInputStream(xmlText.getBytes()));

    var result = new DOMResult();
    try {
      transformer.transform(xmlSource, result);
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

    if (failedAsserts.getLength() > 0 || successfulReports.getLength() > 0) {
      var failedAssertMessages = NodeParser.nodeListToList(failedAsserts).stream();
      var successfulReportMessages = NodeParser.nodeListToList(successfulReports).stream();

      var errors = Stream
        .concat(failedAssertMessages, successfulReportMessages)
        .map(node -> {
          // The location includes an XPath with expanded QNames
          // (Q{namespace}<localPart>).
          var xPath = node.getAttributes().getNamedItem("location").getNodeValue();

          // Find the eId of the node responsible for this problem. Sometimes the location
          // points to an attribute, so we might need to move up in the element tree to
          // find the eId.
          List<Node> eIdNodes = NodeParser.getNodesFromExpression(
            xPath + "/ancestor-or-self::*/@eId",
            norm.getDocument()
          );
          String eId = eIdNodes
            .stream()
            .map(Node::getNodeValue)
            .reduce("", (a, b) -> a.length() > b.length() ? a : b);

          return new LdmlDeSchematronException.ValidationError(
            "/errors/ldml-de-not-schematron-valid/%s/%s".formatted(
                node.getLocalName(),
                node.getAttributes().getNamedItem("id").getNodeValue()
              ),
            xPath,
            node.getTextContent(),
            eId
          );
        })
        .toList();

      throw new LdmlDeSchematronException(errors);
    }
  }
}
