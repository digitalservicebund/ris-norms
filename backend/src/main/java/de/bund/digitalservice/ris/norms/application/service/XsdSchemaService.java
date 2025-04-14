package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Service for providing XSD-Schema files
 */
@Service
public class XsdSchemaService {

  Resource baukastenXsdSchema;
  Resource metadatenXsdSchema;
  Resource regelungstextVerkuendungsfassungXsdSchema;
  Resource risNormsRegelungstextVerkuendungsfassungXsdSchema;
  Resource risNormsOffeneStrukturXsdSchema;

  public XsdSchemaService(
    @Value(
      "classpath:/LegalDocML.de/1.7.2/schema/legalDocML.de-baukasten.xsd"
    ) Resource baukastenXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.7.2/schema/legalDocML.de-metadaten.xsd"
    ) Resource metadatenXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.7.2/schema/legalDocML.de-regelungstextverkuendungsfassung.xsd"
    ) Resource regelungstextVerkuendungsfassungXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.7.2/legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
    ) Resource risNormsRegelungstextVerkuendungsfassungXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.7.2/legalDocML.de-risnorms-offenestruktur.xsd"
    ) Resource risNormsOffeneStrukturXsdSchema
  ) {
    this.baukastenXsdSchema = baukastenXsdSchema;
    this.metadatenXsdSchema = metadatenXsdSchema;
    this.regelungstextVerkuendungsfassungXsdSchema = regelungstextVerkuendungsfassungXsdSchema;
    this.risNormsRegelungstextVerkuendungsfassungXsdSchema =
    risNormsRegelungstextVerkuendungsfassungXsdSchema;
    this.risNormsOffeneStrukturXsdSchema = risNormsOffeneStrukturXsdSchema;
  }

  /**
   * Provide all LegalDocML.de XSD-Schemas as {@link Document}s.
   * @return a list of all the xsd Schemas.
   */
  public List<Document> loadLdmlDeSchemaDocuments() {
    return Stream
      .of(
        baukastenXsdSchema,
        metadatenXsdSchema,
        regelungstextVerkuendungsfassungXsdSchema,
        risNormsRegelungstextVerkuendungsfassungXsdSchema,
        risNormsOffeneStrukturXsdSchema
      )
      .map(this::loadDocumentForResource)
      .toList();
  }

  /**
   * Load the neuris-norm specific schema for validating a {@link Regelungstext}
   * @return the schema
   */
  public Schema getRegelungstextSchema() {
    return createSchemaFromResource(risNormsRegelungstextVerkuendungsfassungXsdSchema);
  }

  /**
   * Load the neuris-norm specific schema for validating a {@link OffeneStruktur}
   * @return the schema
   */
  public Schema getOffeneStrukturSchema() {
    return createSchemaFromResource(risNormsOffeneStrukturXsdSchema);
  }

  private Schema createSchemaFromResource(Resource schemaResource) {
    try {
      Source schemaSource = new StreamSource(schemaResource.getInputStream());
      schemaSource.setSystemId(schemaResource.getURL().toString());
      return SchemaFactory.newDefaultInstance().newSchema(schemaSource);
    } catch (IOException | SAXException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }

  private Document loadDocumentForResource(Resource schemaResource) {
    try {
      return XmlMapper.toDocument(schemaResource.getContentAsString(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }
}
