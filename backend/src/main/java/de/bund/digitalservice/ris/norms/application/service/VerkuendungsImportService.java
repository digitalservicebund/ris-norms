package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.StatusNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormendokumentationspacketProcessingStatusUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveNormendokumentationspaketPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobBuilder;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class VerkuendungsImportService
  implements
    StoreNormendokumentationspaketUseCase, LoadNormendokumentationspacketProcessingStatusUseCase {

  private final SaveNormendokumentationspaketPort saveNormendokumentationspaketPort;
  private final SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort;
  private final LoadVerkuendungImportProcessPort loadVerkuendungImportProcessPort;
  private final JobScheduler jobScheduler;

  public VerkuendungsImportService(
    SaveNormendokumentationspaketPort saveNormendokumentationspaketPort,
    SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort,
    LoadVerkuendungImportProcessPort loadVerkuendungImportProcessPort,
    JobScheduler jobScheduler
  ) {
    this.saveNormendokumentationspaketPort = saveNormendokumentationspaketPort;
    this.saveVerkuendungImportProcessPort = saveVerkuendungImportProcessPort;
    this.loadVerkuendungImportProcessPort = loadVerkuendungImportProcessPort;
    this.jobScheduler = jobScheduler;
  }

  @Override
  public UUID storeNormendokumentationspaket(StoreNormendokumentationspaketUseCase.Query query)
    throws IOException {
    final UUID processId = UUID.randomUUID();
    saveNormendokumentationspaketPort.saveNormendokumentationspaket(
      new SaveNormendokumentationspaketPort.Command(processId, query.file(), query.signature())
    );

    saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
      new SaveVerkuendungImportProcessPort.Command(
        processId,
        VerkuendungImportProcess.Status.CREATED
      )
    );

    jobScheduler.create(
      JobBuilder
        .aJob()
        .withId(processId)
        .withName("Process Normendokumentationspaket")
        .<NormendokumentationspaketService>withDetails(service ->
          service.runProcessing(processId, query.file(), query.signature())
        )
    );
    return processId;
  }

  @Override
  public VerkuendungImportProcess getStatus(
    LoadNormendokumentationspacketProcessingStatusUseCase.Query query
  ) {
    return loadVerkuendungImportProcessPort
      .loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(query.processingId())
      )
      .orElseThrow(() -> new StatusNotFoundException(query.processingId()));
  }
}
