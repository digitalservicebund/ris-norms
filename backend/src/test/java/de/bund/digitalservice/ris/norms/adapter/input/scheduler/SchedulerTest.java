package de.bund.digitalservice.ris.norms.adapter.input.scheduler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.bund.digitalservice.ris.norms.application.port.input.PublishNormUseCase;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableScheduling
@TestPropertySource(properties = "publish.cron=*/1 * * * * *") // Runs every 5 seconds
class SchedulerTest {

  @SpyBean
  private Scheduler scheduler;

  @MockBean
  private PublishNormUseCase publishNormUseCase;

  @Test
  void taskIsScheduled() {
    Awaitility
      .await()
      .atMost(2, TimeUnit.SECONDS)
      .untilAsserted(() -> {
        verify(scheduler, times(1)).runPublishProcess();
        verify(publishNormUseCase, times(1)).processQueuedFilesForPublish();
      });
  }
}
