package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.XsdLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for the reference pattern recognition. */
@Service
@Slf4j
public class BillToActService implements BillToActUseCase {

  private static final String AKN_NAME_SPACE = "http://Inhaltsdaten.LegalDocML.de/1.7/";
  private static final String LDML_SCHEMA_LOCATION =
      "http://Metadaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd";

  @Override
  public Norm convert(Query query) {
    if (query.norm().isAct()) return query.norm();
    return updateXsdLocation(query.norm());
  }

  private Norm updateXsdLocation(Norm norm) {
    XsdLocation xsdLocation = norm.getXsdLocation();
    xsdLocation.setAknNameSpace(AKN_NAME_SPACE);
    xsdLocation.setXsiSchemaLocation(LDML_SCHEMA_LOCATION);
    return norm;
  }
}
