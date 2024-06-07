package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import org.springframework.stereotype.Service;

/** Implements operations related to the "proprietary" of a {@link Norm} */
@Service
public class ProprietaryService implements LoadProprietaryFromNormUseCase {

  final LoadNormPort loadNormPort;

  ProprietaryService(LoadNormPort loadNormPort) {
    this.loadNormPort = loadNormPort;
  }

  @Override
  public Proprietary loadProprietaryFromNorm(Query query) throws NormNotFoundException {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(m -> m.getMeta().getOrCreateProprietary())
        .orElseThrow(() -> new NormNotFoundException((query.eli())));
  }
}
