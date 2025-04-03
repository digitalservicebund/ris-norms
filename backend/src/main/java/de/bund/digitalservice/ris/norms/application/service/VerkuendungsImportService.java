package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.SaveNormendokumentationspaketPort;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobBuilder;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class VerkuendungsImportService implements StoreNormendokumentationspaketUseCase {

  SaveNormendokumentationspaketPort saveNormendokumentationspaketPort;
  private final JobScheduler jobScheduler;

  public VerkuendungsImportService(
    SaveNormendokumentationspaketPort saveNormendokumentationspaketPort,
    JobScheduler jobScheduler
  ) {
    this.saveNormendokumentationspaketPort = saveNormendokumentationspaketPort;
    this.jobScheduler = jobScheduler;
  }

  @Override
  public UUID storeNormendokumentationspaket(Query query) throws IOException {
    UUID processId = UUID.randomUUID();
    saveNormendokumentationspaketPort.saveNormendokumentationspaket(
      new SaveNormendokumentationspaketPort.Command(processId, query.file(), query.signature())
    );

    jobScheduler.create(
      JobBuilder
        .aJob()
        .withId(processId)
        .withName("Process Normendokumentationspaket")
        .withDetails(() ->
          log.info("Scheduled job for processing Normendokumentationspaket with id: {}", processId)
        // TODO scan for virus
        // TODO verify sig
        // TODO unzip
        // TODO validate
        )
    );

    return processId;
  }
}
