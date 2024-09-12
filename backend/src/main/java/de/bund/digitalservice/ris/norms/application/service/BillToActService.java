package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for the reference pattern recognition. */
@Service
@Slf4j
public class BillToActService implements BillToActUseCase {

  @Override
  public Norm convert(Query query) {
    if (query.norm().isAct()) return query.norm();
    return updateXsdLocation(query.norm());
  }

  private Norm updateXsdLocation(Norm norm) {
    norm.getXsdLocation();
    // TODO
    // change to
    // <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/"
    //                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    //                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7/
    // ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.7/
    // ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
    return norm;
  }
}
