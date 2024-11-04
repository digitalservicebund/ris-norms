package de.bund.digitalservice.ris.norms.adapter.input.scheduler;

import de.bund.digitalservice.ris.norms.application.port.input.PublishNormUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler class responsible for executing a scheduled task to process scheduled tasks, for now just one task for publishing queued norms.
 *
 * <p>The {@code @Component} annotation is used here instead of {@code @Service} because this class
 * does not represent a business or application service, but rather a scheduler component for managing
 * periodic task execution. {@code @Component} is a general-purpose annotation for Spring-managed beans,
 * making it suitable for components that handle specific infrastructure or operational tasks like scheduling.</p>
 */
@Component
@Slf4j
public class Scheduler {

  private final PublishNormUseCase publishNormUseCase;

  public Scheduler(PublishNormUseCase publishNormUseCase) {
    this.publishNormUseCase = publishNormUseCase;
  }

  /**
   * Executes the scheduled task at the configured time specified by the cron expression in application properties.
   * This method triggers the {@link PublishNormUseCase#processQueuedFilesForPublish} method to process any files
   * queued for publishing norms.
   *
   * <p>Using the {@code @Profile} annotation, this method will only run in staging, UAT, and production environments.
   * The cron schedule is controlled by the property {@code publish.cron}.</p>
   */
  @Scheduled(cron = "${publish.cron}", zone = "Europe/Berlin")
  @Profile({ "staging", "uat", "production" })
  public void runPublishProcess() {
    log.info("Job for publishing QUEUED_FOR_PUBLISH norms started.");
    publishNormUseCase.processQueuedFilesForPublish();
  }
}
