package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.adapter.output.exception.BucketException;
import de.bund.digitalservice.ris.norms.application.exception.MigrationJobException;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PublishServiceTest {

  final LoadNormIdsByPublishStatePort loadNormIdsByPublishStatePort = mock(
    LoadNormIdsByPublishStatePort.class
  );

  final PublishPublicNormPort publishPublicNormPort = mock(PublishPublicNormPort.class);

  final PublishPrivateNormPort publishPrivateNormPort = mock(PublishPrivateNormPort.class);

  final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);

  final DeletePublicNormPort deletePublicNormPort = mock(DeletePublicNormPort.class);

  final DeletePrivateNormPort deletePrivateNormPort = mock(DeletePrivateNormPort.class);

  final DeleteAllPublicNormsPort deleteAllPublicNormsPort = mock(DeleteAllPublicNormsPort.class);

  final DeleteAllPrivateNormsPort deleteAllPrivateNormsPort = mock(DeleteAllPrivateNormsPort.class);
  final LoadNormByIdPort loadNormByIdPort = mock(LoadNormByIdPort.class);

  final LoadMigrationLogByDatePort loadMigrationLogByDatePort = mock(
    LoadMigrationLogByDatePort.class
  );

  final PublishChangelogsPort publishChangelogsPort = mock(PublishChangelogsPort.class);

  final PublishService publishService = new PublishService(
    loadNormIdsByPublishStatePort,
    publishPublicNormPort,
    publishPrivateNormPort,
    updateOrSaveNormPort,
    deletePublicNormPort,
    deletePrivateNormPort,
    loadNormByIdPort,
    loadMigrationLogByDatePort,
    deleteAllPublicNormsPort,
    deleteAllPrivateNormsPort,
    publishChangelogsPort
  );

  @Nested
  class processQueuedFilesForPublish {

    @Test
    void publishNormToPublicAndPrivateStorage() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      final UUID uuid = UUID.randomUUID();
      when(loadNormIdsByPublishStatePort.loadNormIdsByPublishState(any()))
        .thenReturn(List.of(uuid));
      when(loadNormByIdPort.loadNormById(new LoadNormByIdPort.Command(uuid)))
        .thenReturn(Optional.of(norm));

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormIdsByPublishStatePort, times(1))
        .loadNormIdsByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(1)).publishChangelogs();
    }

    @Test
    void publishNormToPublicButPrivateFails() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      final UUID uuid = UUID.randomUUID();
      when(loadNormIdsByPublishStatePort.loadNormIdsByPublishState(any()))
        .thenReturn(List.of(uuid));
      when(loadNormByIdPort.loadNormById(new LoadNormByIdPort.Command(uuid)))
        .thenReturn(Optional.of(norm));
      doThrow(BucketException.class).when(publishPrivateNormPort).publishPrivateNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormIdsByPublishStatePort, times(1))
        .loadNormIdsByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
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
      verify(publishChangelogsPort, times(1)).publishChangelogs();
    }

    @Test
    void publishNormToPublicFails() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      final UUID uuid = UUID.randomUUID();
      when(loadNormIdsByPublishStatePort.loadNormIdsByPublishState(any()))
        .thenReturn(List.of(uuid));
      when(loadNormByIdPort.loadNormById(new LoadNormByIdPort.Command(uuid)))
        .thenReturn(Optional.of(norm));
      doThrow(BucketException.class).when(publishPublicNormPort).publishPublicNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormIdsByPublishStatePort, times(1))
        .loadNormIdsByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
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
      verify(publishChangelogsPort, times(1)).publishChangelogs();
    }

    @Test
    void deleteAllNormsIfMigrationLogExists() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      final MigrationLog migrationLog = MigrationLog
        .builder()
        .size(5)
        .createdAt(Instant.now())
        .build();

      final UUID uuid = UUID.randomUUID();
      when(loadNormIdsByPublishStatePort.loadNormIdsByPublishState(any()))
        .thenReturn(List.of(uuid));
      when(loadNormByIdPort.loadNormById(new LoadNormByIdPort.Command(uuid)))
        .thenReturn(Optional.of(norm));
      when(loadMigrationLogByDatePort.loadMigrationLogByDate(any()))
        .thenReturn(Optional.of(migrationLog)); // Migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormIdsByPublishStatePort, times(1))
        .loadNormIdsByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );

      // Check that deletion was called
      verify(deleteAllPublicNormsPort, times(1)).deleteAllPublicNorms();
      verify(deleteAllPrivateNormsPort, times(1)).deleteAllPrivateNorms();

      // Verify norm publishing actions
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(1)).publishChangelogs();
    }

    @Test
    void doNotdeleteAllNormsIfMigrationLogExistsButSizeIsZero() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      final MigrationLog migrationLog = MigrationLog
        .builder()
        .size(0)
        .createdAt(Instant.now())
        .build();

      final UUID uuid = UUID.randomUUID();
      when(loadNormIdsByPublishStatePort.loadNormIdsByPublishState(any()))
        .thenReturn(List.of(uuid));
      when(loadNormByIdPort.loadNormById(new LoadNormByIdPort.Command(uuid)))
        .thenReturn(Optional.of(norm));
      when(loadMigrationLogByDatePort.loadMigrationLogByDate(any()))
        .thenReturn(Optional.of(migrationLog)); // Migration log found

      // Then When

      assertThatThrownBy(publishService::processQueuedFilesForPublish)
        .isInstanceOf(MigrationJobException.class);

      // Then
      verify(loadNormIdsByPublishStatePort, times(0))
        .loadNormIdsByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );

      // Check that deletion was called
      verify(deleteAllPublicNormsPort, times(0)).deleteAllPublicNorms();
      verify(deleteAllPrivateNormsPort, times(0)).deleteAllPrivateNorms();

      // Verify norm publishing actions
      verify(publishPublicNormPort, times(0))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(0))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(0)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(0)).publishChangelogs();
    }

    @Test
    void doNotDeleteNormsIfNoMigrationLogExists() {
      // Given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

      final UUID uuid = UUID.randomUUID();
      when(loadNormIdsByPublishStatePort.loadNormIdsByPublishState(any()))
        .thenReturn(List.of(uuid));
      when(loadNormByIdPort.loadNormById(new LoadNormByIdPort.Command(uuid)))
        .thenReturn(Optional.of(norm));
      when(loadMigrationLogByDatePort.loadMigrationLogByDate(any())).thenReturn(Optional.empty()); // No migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormIdsByPublishStatePort, times(1))
        .loadNormIdsByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );

      // Verify that deletion was NOT called
      verify(deleteAllPublicNormsPort, never()).deleteAllPublicNorms();
      verify(deleteAllPrivateNormsPort, never()).deleteAllPrivateNorms();

      // Verify norm publishing actions
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(1)).publishChangelogs();
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
