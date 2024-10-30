package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.adapter.output.exception.BucketException;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * This unit tests uses a slightly different set-up as the other unit tests (manually instantiating the service to be tested
 * and passing the mocked dependencies). In order to test that the task is successfully scheduled and runs we need to spy on the {@link PublishService}.
 * To have a {@link SpyBean} we need to {@link MockBean} the dependencies and to use these two annotations we would need whether {@link org.springframework.boot.test.context.SpringBootTest}
 * or {@link SpringExtension}. The first one would load the entire application context and would require an extra test config so that flyway is not initiated.
 * The second one does not load the entire application context and is enough for enabling Spring features like the dependency injection.
 * Furthermore, since the {@link PublishService} has a {@link org.springframework.scheduling.annotation.Scheduled} method we need to manually override
 * the application property of the cron expression (so that we don't do this for all tests) using {@link TestPropertySource} and to do this we need one of the two
 * test annotations mentioned before.
 */
@ExtendWith(SpringExtension.class)
@EnableScheduling
@TestPropertySource(properties = "publish.cron=*/10 * * * * *") // Runs every 5 seconds
class PublishServiceTest {

  @MockBean
  private LoadNormsByPublishStatePort loadNormsByPublishStatePort;

  @MockBean
  private PublishPublicNormPort publishPublicNormPort;

  @MockBean
  private PublishPrivateNormPort publishPrivateNormPort;

  @MockBean
  private UpdateOrSaveNormPort updateOrSaveNormPort;

  @MockBean
  private DeletePublicNormPort deletePublicNormPort;

  @MockBean
  private DeletePrivateNormPort deletePrivateNormPort;

  @SpyBean
  private PublishService publishService;

  @Nested
  class processQueuedFilesForPublish {

    @Test
    void taskIsScheduled() {
      Awaitility
        .await()
        .atMost(11, TimeUnit.SECONDS)
        .untilAsserted(() -> verify(publishService, times(1)).processQueuedFilesForPublish());
    }

    @Test
    void publishNormToPublicAndPrivateStorage() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      when(loadNormsByPublishStatePort.loadNormsByPublishState(any())).thenReturn(List.of(norm));

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormsByPublishStatePort, times(1))
        .loadNormsByPublishState(
          new LoadNormsByPublishStatePort.Command(NormPublishState.QUEUED_FOR_PUBLISH)
        );
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
    }

    @Test
    void publishNormToPublicButPrivateFails() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      when(loadNormsByPublishStatePort.loadNormsByPublishState(any())).thenReturn(List.of(norm));
      doThrow(BucketException.class).when(publishPrivateNormPort).publishPrivateNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormsByPublishStatePort, times(1))
        .loadNormsByPublishState(
          new LoadNormsByPublishStatePort.Command(NormPublishState.QUEUED_FOR_PUBLISH)
        );
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(deletePublicNormPort, times(1))
        .deletePublicNorm(new DeletePublicNormPort.Command(norm));
      verify(deletePrivateNormPort, never())
        .deletePrivateNorm(new DeletePrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, never()).updateOrSave(any(UpdateOrSaveNormPort.Command.class));
    }

    @Test
    void publishNormToPublicFails() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      when(loadNormsByPublishStatePort.loadNormsByPublishState(any())).thenReturn(List.of(norm));
      doThrow(BucketException.class).when(publishPublicNormPort).publishPublicNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormsByPublishStatePort, times(1))
        .loadNormsByPublishState(
          new LoadNormsByPublishStatePort.Command(NormPublishState.QUEUED_FOR_PUBLISH)
        );
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, never())
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(deletePublicNormPort, never())
        .deletePublicNorm(new DeletePublicNormPort.Command(norm));
      verify(deletePrivateNormPort, never())
        .deletePrivateNorm(new DeletePrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, never()).updateOrSave(any(UpdateOrSaveNormPort.Command.class));
    }
  }

  @Nested
  class prepareForPublish {

    @Test
    void removesOrganisationsEinheitFromMetadata() {
      // Given
      var norm = NormFixtures.loadFromDisk("NormToBeReleased.xml");
      var proprietary = norm.getMeta().getProprietary().get();

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2005, 1, 1)))
        .contains("Aktuelle Organisationseinheit");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2028, 6, 1)))
        .contains("Nächste Organisationseinheit");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2029, 6, 1)))
        .contains("Übernächste Organisationseinheit");

      // When
      publishService.prepareForPublish(norm);

      // Then
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2005, 1, 1))).isEmpty();
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2028, 6, 1))).isEmpty();
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2029, 6, 1))).isEmpty();
    }
  }
}
