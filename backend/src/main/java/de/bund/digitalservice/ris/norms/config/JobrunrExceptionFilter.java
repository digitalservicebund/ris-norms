package de.bund.digitalservice.ris.norms.config;

import de.bund.digitalservice.ris.norms.application.exception.NormendokumentationspaketImportJobRunFailedException;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.Job;
import org.jobrunr.jobs.filters.JobServerFilter;
import org.springframework.stereotype.Component;

/**
 * Provides a filter for Jobrunner that makes sure that after retrying the maximum number of times
 * we write a new status to our error details of the job
 */
@Component
@Slf4j
public class JobrunrExceptionFilter implements JobServerFilter {

  private final SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort;

  public JobrunrExceptionFilter(SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort) {
    this.saveVerkuendungImportProcessPort = saveVerkuendungImportProcessPort;
  }

  @Override
  public void onFailedAfterRetries(Job job) {
    saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
      new SaveVerkuendungImportProcessPort.Command(
        job.getId(),
        VerkuendungImportProcess.Status.ERROR,
        new NormendokumentationspaketImportJobRunFailedException()
      )
    );

    log.error("Tried to import a Normendokumentationspacket the max amount of times but failed");
  }
}
