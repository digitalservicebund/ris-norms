package de.bund.digitalservice.ris.norms.adapter.input.scheduler;

import de.bund.digitalservice.ris.norms.application.port.input.PublishNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.PublishNormsToPortalPrototypeUseCase;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler class responsible for executing the task of publishing norms to the portal prototype.
 *
 * <p>Using the {@code @Profile} annotation, this scheduler class will only be instantiated in production environment</p>
 */
@Component
@Slf4j
@ConditionalOnProperty("publish.portal-prototype.enabled")
public class PortalPrototypePublishingScheduler {

  private final PublishNormsToPortalPrototypeUseCase publishNormsToPortalPrototypeUseCase;

  public PortalPrototypePublishingScheduler(
    PublishNormsToPortalPrototypeUseCase publishNormsToPortalPrototypeUseCase
  ) {
    this.publishNormsToPortalPrototypeUseCase = publishNormsToPortalPrototypeUseCase;
  }

  /**
   * Executes the scheduled task at the configured time specified by the cron expression in application properties.
   * This method triggers the {@link PublishNormUseCase#processQueuedFilesForPublish} method to process any files
   * queued for publishing norms.
   *
   * <p>The cron schedule is controlled by the property {@code publish.cron}.</p>
   */
  @Scheduled(cron = "${publish.portal-prototype.cron}", zone = "Europe/Berlin")
  @SchedulerLock(
    name = "scheduledPublishToPortalPrototype",
    lockAtMostFor = "240m",
    lockAtLeastFor = "120m"
  )
  public void runPortalPrototypePublishing() {
    // To assert that the lock is held (prevents misconfiguration errors)
    LockAssert.assertLocked();
    log.info("Job for publishing norms to the portal prototype started.");
    publishNormsToPortalPrototypeUseCase.publishNormsToPortalPrototype();
  }
}
