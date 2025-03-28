package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.SaveNormendokumentationspaketPort;
import org.springframework.stereotype.Service;

@Service
class VerkuendungsImportService implements StoreNormendokumentationspaketUseCase {

  SaveNormendokumentationspaketPort saveNormendokumentationspaketPort;

  public VerkuendungsImportService(
    SaveNormendokumentationspaketPort saveNormendokumentationspaketPort
  ) {
    this.saveNormendokumentationspaketPort = saveNormendokumentationspaketPort;
  }

  @Override
  public String storeNormendokumentationspaket(Query query) {
    saveNormendokumentationspaketPort.saveNormendokumentationspaket(
      new SaveNormendokumentationspaketPort.Command(query.file(), query.signature())
    );

    // TODO start processing
    return "";
  }
}
