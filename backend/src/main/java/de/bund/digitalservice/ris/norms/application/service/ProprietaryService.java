package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadElementsByTypeFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Implements operations related to the data in a {@Link Norm}'s "proprietary" section */
@Service
public class ProprietaryService implements LoadProprietaryFromNormUseCase {
  @Override
  public Optional<Proprietary> loadProprietaryFromNorm(Query query)
      throws LoadElementsByTypeFromNormUseCase.NormNotFoundException {
    return Optional.empty();
  }
}
