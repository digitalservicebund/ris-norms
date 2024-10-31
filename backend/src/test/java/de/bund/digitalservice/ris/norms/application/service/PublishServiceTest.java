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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PublishServiceTest {

  final LoadNormsByPublishStatePort loadNormsByPublishStatePort = mock(
    LoadNormsByPublishStatePort.class
  );

  final PublishPublicNormPort publishPublicNormPort = mock(PublishPublicNormPort.class);

  final PublishPrivateNormPort publishPrivateNormPort = mock(PublishPrivateNormPort.class);

  final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);

  final DeletePublicNormPort deletePublicNormPort = mock(DeletePublicNormPort.class);

  final DeletePrivateNormPort deletePrivateNormPort = mock(DeletePrivateNormPort.class);

  final PublishService publishService = new PublishService(
    loadNormsByPublishStatePort,
    publishPublicNormPort,
    publishPrivateNormPort,
    updateOrSaveNormPort,
    deletePublicNormPort,
    deletePrivateNormPort
  );

  @Nested
  class processQueuedFilesForPublish {

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
