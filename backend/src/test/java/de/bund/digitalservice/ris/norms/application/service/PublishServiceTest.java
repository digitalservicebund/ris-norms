package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.adapter.output.exception.BucketException;
import de.bund.digitalservice.ris.norms.application.exception.MigrationJobException;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.OffeneStruktur;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PublishServiceTest {

  final LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort =
    mock(LoadNormManifestationElisByPublishStatePort.class);

  final PublishNormPort publishNormPort = mock(PublishNormPort.class);

  final PublishNormPort publishPrivateNormPort = mock(PublishNormPort.class);

  final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);

  final DeletePublishedNormPort deletePublishedNormPort = mock(DeletePublishedNormPort.class);
  final DeletePublishedNormPort deletePrivateNormPort = mock(DeletePublishedNormPort.class);

  final DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort = mock(
    DeleteAllPublishedDokumentePort.class
  );

  final DeleteAllPublishedDokumentePort deleteAllPrivateDokumentePort = mock(
    DeleteAllPublishedDokumentePort.class
  );
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);

  final LoadLastMigrationLogPort loadLastMigrationLogPort = mock(LoadLastMigrationLogPort.class);

  final PublishChangelogPort publishChangelogPort = mock(PublishChangelogPort.class);
  final PublishChangelogPort publishPrivateChangelogsPort = mock(PublishChangelogPort.class);

  final CompleteMigrationLogPort updateMigrationLogPort = mock(CompleteMigrationLogPort.class);
  final ConfidentialDataCleanupService confidentialDataCleanupService = mock(
    ConfidentialDataCleanupService.class
  );

  final PublishService publishService = new PublishService(
    loadNormManifestationElisByPublishStatePort,
    loadNormPort,
    publishNormPort,
    publishPrivateNormPort,
    updateOrSaveNormPort,
    deletePublishedNormPort,
    deletePrivateNormPort,
    loadLastMigrationLogPort,
    deleteAllPublishedDokumentePort,
    deleteAllPrivateDokumentePort,
    publishChangelogPort,
    publishPrivateChangelogsPort,
    updateMigrationLogPort,
    confidentialDataCleanupService
  );

  @Nested
  class processQueuedFilesForPublish {

    @Test
    void publishNormToPublicAndPrivateStorage() {
      // Given
      final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
      final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
        "SimpleOffenestruktur.xml"
      );
      final Norm norm = new Norm(
        NormPublishState.QUEUED_FOR_PUBLISH,
        Set.of(regelungstext1, offenestruktur1),
        Set.of()
      );
      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      )
        .thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(norm.getManifestationEli())))
        .thenReturn(Optional.of(norm));

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );
      verify(confidentialDataCleanupService, times(1)).clean(norm);
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void publishNormToPublicAndPrivateStorageWhenMigrationLogExists() {
      // Given
      final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml");
      final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
        "SimpleOffenestruktur.xml"
      );
      final Norm norm = new Norm(
        NormPublishState.QUEUED_FOR_PUBLISH,
        Set.of(regelungstext1, offenestruktur1),
        Set.of()
      );
      final MigrationLog migrationLog = MigrationLog
        .builder()
        .size(5)
        .createdAt(Instant.now())
        .completed(false)
        .build();
      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      )
        .thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(norm.getManifestationEli())))
        .thenReturn(Optional.of(norm));
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog));

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );
      verify(confidentialDataCleanupService, times(1)).clean(norm);
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
      verify(updateMigrationLogPort)
        .completeMigrationLog(new CompleteMigrationLogPort.Command(migrationLog.getId()));
    }

    @Test
    void publishNormToPublicButPrivateFails() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      )
        .thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(norm.getManifestationEli())))
        .thenReturn(Optional.of(norm));
      doThrow(BucketException.class).when(publishPrivateNormPort).publishNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(deletePublishedNormPort, times(1))
        .deletePublishedNorm(new DeletePublishedNormPort.Command(norm));
      verify(deletePrivateNormPort, never())
        .deletePublishedNorm(new DeletePublishedNormPort.Command(norm));
      verify(updateOrSaveNormPort, never()).updateOrSave(any(UpdateOrSaveNormPort.Command.class));
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void publishNormToPublicFails() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      )
        .thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(norm.getManifestationEli())))
        .thenReturn(Optional.of(norm));
      doThrow(BucketException.class).when(publishNormPort).publishNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(publishPrivateNormPort, never()).publishNorm(new PublishNormPort.Command(norm));
      verify(deletePublishedNormPort, never())
        .deletePublishedNorm(new DeletePublishedNormPort.Command(norm));
      verify(deletePrivateNormPort, never())
        .deletePublishedNorm(new DeletePublishedNormPort.Command(norm));
      verify(updateOrSaveNormPort, never()).updateOrSave(any(UpdateOrSaveNormPort.Command.class));
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void deleteAllNormsIfUnpublishedMigrationLogExists() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      final MigrationLog migrationLog = MigrationLog
        .builder()
        .size(5)
        .createdAt(Instant.now())
        .completed(false)
        .build();

      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      )
        .thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(norm.getManifestationEli())))
        .thenReturn(Optional.of(norm));
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog)); // Migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );

      // Check that deletion was called
      verify(deleteAllPublishedDokumentePort, times(1)).deleteAllPublishedDokumente(any());
      verify(deleteAllPrivateDokumentePort, times(1)).deleteAllPublishedDokumente(any());

      // Verify norm publishing actions
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void doNotdeleteAllNormsIfMigrationLogExistsButSizeIsZero() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      final MigrationLog migrationLog = MigrationLog
        .builder()
        .size(0)
        .createdAt(Instant.now())
        .build();

      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      )
        .thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(norm.getManifestationEli())))
        .thenReturn(Optional.of(norm));
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog)); // Migration log found

      // Then When
      assertThatThrownBy(publishService::processQueuedFilesForPublish)
        .isInstanceOf(MigrationJobException.class);

      // Check that deletion was not called
      verify(deleteAllPublishedDokumentePort, times(0)).deleteAllPublishedDokumente(any());
      verify(deleteAllPrivateDokumentePort, times(0)).deleteAllPublishedDokumente(any());
    }

    @Test
    void doNotDeleteNormsIfNoMigrationLogExists() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");

      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      )
        .thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(norm.getManifestationEli())))
        .thenReturn(Optional.of(norm));
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.empty()); // No migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );

      // Verify that deletion was NOT called
      verify(deleteAllPublishedDokumentePort, never()).deleteAllPublishedDokumente(any());
      verify(deleteAllPrivateDokumentePort, never()).deleteAllPublishedDokumente(any());

      // Verify norm publishing actions
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void doNotDeleteNormsIfMigrationLogIsAlreadyPublished() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      final MigrationLog migrationLog = MigrationLog
        .builder()
        .size(5)
        .createdAt(Instant.now())
        .completed(true)
        .build();

      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      )
        .thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(norm.getManifestationEli())))
        .thenReturn(Optional.of(norm));
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog)); // Migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
          argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
        );

      // Check that deletion was not called
      verify(deleteAllPublishedDokumentePort, never()).deleteAllPublishedDokumente(any());
      verify(deleteAllPrivateDokumentePort, never()).deleteAllPublishedDokumente(any());

      // Verify norm publishing actions
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }
  }
}
