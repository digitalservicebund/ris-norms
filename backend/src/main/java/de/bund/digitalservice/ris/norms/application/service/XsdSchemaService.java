package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.Bekanntmachung;
import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Rechtsetzungsdokument;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

/**
 * Service for providing XSD-Schema files
 */
@Service
public class XsdSchemaService {

  Resource risNormsRegelungstextVerkuendungsfassungXsdSchema;
  Resource risNormsBekanntmachungXsdSchema;
  Resource risNormsOffeneStrukturXsdSchema;
  Resource risNormsRechtsetzungsdokumentXsdSchema;

  public XsdSchemaService(
    @Value(
      "classpath:/LegalDocML.de/1.8.2/legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
    ) Resource risNormsRegelungstextVerkuendungsfassungXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.8.2/legalDocML.de-risnorms-bekanntmachung.xsd"
    ) Resource risNormsBekanntmachungXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.8.2/legalDocML.de-risnorms-offenestruktur.xsd"
    ) Resource risNormsOffeneStrukturXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.8.2/legalDocML.de-risnorms-rechtsetzungsdokument.xsd"
    ) Resource risNormsRechtsetzungsdokumentXsdSchema
  ) {
    this.risNormsRegelungstextVerkuendungsfassungXsdSchema =
      risNormsRegelungstextVerkuendungsfassungXsdSchema;
    this.risNormsBekanntmachungXsdSchema = risNormsBekanntmachungXsdSchema;
    this.risNormsOffeneStrukturXsdSchema = risNormsOffeneStrukturXsdSchema;
    this.risNormsRechtsetzungsdokumentXsdSchema = risNormsRechtsetzungsdokumentXsdSchema;
  }

  /**
   * Load the neuris-norm specific schema for validating a {@link Regelungstext}
   * @return the schema
   */
  public Schema getRegelungstextSchema() {
    return createSchemaFromResource(risNormsRegelungstextVerkuendungsfassungXsdSchema);
  }

  /**
   * Load the neuris-norm specific schema for validating a {@link Bekanntmachung}
   * @return the schema
   */
  public Schema getBekanntmachungSchema() {
    return createSchemaFromResource(risNormsBekanntmachungXsdSchema);
  }

  /**
   * Load the neuris-norm specific schema for validating a {@link OffeneStruktur}
   * @return the schema
   */
  public Schema getOffeneStrukturSchema() {
    return createSchemaFromResource(risNormsOffeneStrukturXsdSchema);
  }

  /**
   * Load the neuris-norm specific schema for validating a {@link Rechtsetzungsdokument}
   * @return the schema
   */
  public Schema getRechtsetzungsdokumentSchema() {
    return createSchemaFromResource(risNormsRechtsetzungsdokumentXsdSchema);
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
}
