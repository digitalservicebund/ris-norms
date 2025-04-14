package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class NormendokumentationspaketService {

  private final SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort;

  NormendokumentationspaketService(
    SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort
  ) {
    this.saveVerkuendungImportProcessPort = saveVerkuendungImportProcessPort;
  }

  public void runProcessing(
    final UUID processId,
    final Resource zipFile,
    final Resource signature
  ) {
    saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
      new SaveVerkuendungImportProcessPort.Command(
        processId,
        VerkuendungImportProcess.Status.CREATED
      )
    );

    log.info("Scheduled job for processing Normendokumentationspaket with id: {}", processId);
    // TODO scan for virus
    // TODO verify sig
    // TODO unzip
    // TODO validate
  }
}
