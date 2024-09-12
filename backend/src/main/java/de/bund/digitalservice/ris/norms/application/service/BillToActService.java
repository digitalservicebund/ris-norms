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
    return query.norm();
  }
}
