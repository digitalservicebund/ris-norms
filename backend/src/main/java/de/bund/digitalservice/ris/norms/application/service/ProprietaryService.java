package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.MetadatenDs;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import org.springframework.stereotype.Service;

/** Implements operations related to the "proprietary" of a {@link Norm} */
@Service
public class ProprietaryService
    implements LoadProprietaryFromNormUseCase, UpdateProprietaryFromNormUseCase {

  final LoadNormPort loadNormPort;
  final UpdateNormPort updateNormPort;

  ProprietaryService(LoadNormPort loadNormPort, UpdateNormPort updateNormPort) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
  }

  @Override
  public Proprietary loadProprietaryFromNorm(LoadProprietaryFromNormUseCase.Query query)
      throws NormNotFoundException {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(m -> m.getMeta().getOrCreateProprietary())
        .orElseThrow(() -> new NormNotFoundException((query.eli())));
  }

  @Override
  public Proprietary updateProprietaryFromNorm(UpdateProprietaryFromNormUseCase.Query query)
      throws NormNotFoundException {
    final Norm norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException((query.eli())));
    final Proprietary proprietary = norm.getMeta().getOrCreateProprietary();
    final MetadatenDs metadatenDs = proprietary.getOrCreateMetadatenDs();

    metadatenDs.setFnaAt(query.atDate(), query.metadata().fna());
    metadatenDs.setArtAt(query.atDate(), query.metadata().art());
    metadatenDs.setTypAt(query.atDate(), query.metadata().typ());
    metadatenDs.setSubtypAt(query.atDate(), query.metadata().subtyp());

    updateNormPort.updateNorm(new UpdateNormPort.Command(norm));

    return proprietary;
  }
}
