package de.bund.digitalservice.ris.norms.adapter.input.scheduler;

import de.bund.digitalservice.ris.norms.application.port.input.PublishNormUseCase;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler class responsible for executing the task for publishing queued norms.
 *
 * <p>The {@code @Component} annotation is used here instead of {@code @Service} because this class
 * does not represent a business or application service, but rather a scheduler component for managing
 * periodic task execution. {@code @Component} is a general-purpose annotation for Spring-managed beans,
 * making it suitable for components that handle specific infrastructure or operational tasks like scheduling.</p>
 * <p>Using the {@code @Profile} annotation, this scheduler class will only be instantiated in staging, UAT, and production environments</p>
 */
@Component
@Slf4j
@ConditionalOnProperty("publish.enabled")
public class PublishingScheduler {

  private final PublishNormUseCase publishNormUseCase;

  public PublishingScheduler(PublishNormUseCase publishNormUseCase) {
    this.publishNormUseCase = publishNormUseCase;
  }

  /**
   * Executes the scheduled task at the configured time specified by the cron expression in application properties.
   * This method triggers the {@link PublishNormUseCase#processQueuedFilesForPublish} method to process any files
   * queued for publishing norms.
   *
   * <p>The cron schedule is controlled by the property {@code publish.cron}.</p>
   */
  @Scheduled(cron = "${publish.cron}", zone = "Europe/Berlin")
  @SchedulerLock(name = "scheduledPublishToBucket", lockAtMostFor = "240m", lockAtLeastFor = "120m")
  public void runPublishProcess() {
    // To assert that the lock is held (prevents misconfiguration errors)
    LockAssert.assertLocked();
    log.info("Job for publishing QUEUED_FOR_PUBLISH norms started.");
    publishNormUseCase.processQueuedFilesForPublish();
  }
}
