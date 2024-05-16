package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import org.springframework.stereotype.Service;

/** Service for updating norms. */
@Service
public class UpdateNormService implements UpdatePassiveModificationsUseCase {
  @Override
  public Norm updatePassiveModifications(Query query) {
    return query.norm();
  }
}
