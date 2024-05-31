package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadElementsByTypeFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Implements operations related to the "proprietary" of a {@link Norm} */
@Service
public class ProprietaryService implements LoadProprietaryFromNormUseCase {

  final LoadNormPort loadNormPort;

  ProprietaryService(LoadNormPort loadNormPort) {
    this.loadNormPort = loadNormPort;
  }

  @Override
  public Optional<Proprietary> loadProprietaryFromNorm(Query query)
      throws LoadElementsByTypeFromNormUseCase.NormNotFoundException {

    var norm = loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));
    if (norm.isEmpty())
      throw new LoadElementsByTypeFromNormUseCase.NormNotFoundException(query.eli());

    return norm.get().getProprietary();
  }
}
