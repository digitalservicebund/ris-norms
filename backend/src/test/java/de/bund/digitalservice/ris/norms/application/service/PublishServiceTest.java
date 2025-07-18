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

  final UpdateNormPublishStatePort updateNormPublishStatePort = mock(
    UpdateNormPublishStatePort.class
  );

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
    deletePublishedNormPort,
    deletePrivateNormPort,
    loadLastMigrationLogPort,
    deleteAllPublishedDokumentePort,
    deleteAllPrivateDokumentePort,
    publishChangelogPort,
    publishPrivateChangelogsPort,
    updateMigrationLogPort,
    confidentialDataCleanupService,
    updateNormPublishStatePort
  );

  @Nested
  class processQueuedFilesForPublish {

    @Test
    void publishNormToPublicAndPrivateStorage() {
      // Given
      final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
      );
      final Norm norm = new Norm(
        NormPublishState.QUEUED_FOR_PUBLISH,
        Set.of(regelungstext1, offenestruktur1),
        Set.of()
      );
      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      ).thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getManifestationEli()))).thenReturn(
        Optional.of(norm)
      );

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(1)
      ).loadNormManifestationElisByPublishState(
        argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      );
      verify(confidentialDataCleanupService, times(1)).clean(norm);
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(updateNormPublishStatePort, times(1)).updateNormPublishState(
        new UpdateNormPublishStatePort.Options(
          norm.getManifestationEli(),
          NormPublishState.PUBLISHED
        )
      );
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void publishNormToPublicAndPrivateStorageWhenMigrationLogExists() {
      // Given
      final Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      final OffeneStruktur offenestruktur1 = Fixtures.loadOffeneStrukturFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
      );
      final Norm norm = new Norm(
        NormPublishState.QUEUED_FOR_PUBLISH,
        Set.of(regelungstext1, offenestruktur1),
        Set.of()
      );
      final MigrationLog migrationLog = MigrationLog.builder()
        .xmlSize(5)
        .createdAt(Instant.now())
        .completed(false)
        .build();
      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      ).thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getManifestationEli()))).thenReturn(
        Optional.of(norm)
      );
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog));

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(1)
      ).loadNormManifestationElisByPublishState(
        argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      );
      verify(confidentialDataCleanupService, times(1)).clean(norm);
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(updateNormPublishStatePort, times(1)).updateNormPublishState(
        new UpdateNormPublishStatePort.Options(
          norm.getManifestationEli(),
          NormPublishState.PUBLISHED
        )
      );
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
      verify(updateMigrationLogPort).completeMigrationLog(
        new CompleteMigrationLogPort.Options(migrationLog.getId())
      );
    }

    @Test
    void publishNormToPublicButPrivateFails() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      ).thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getManifestationEli()))).thenReturn(
        Optional.of(norm)
      );
      doThrow(BucketException.class).when(publishPrivateNormPort).publishNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(1)
      ).loadNormManifestationElisByPublishState(
        argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      );
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(deletePublishedNormPort, times(1)).deletePublishedNorm(
        new DeletePublishedNormPort.Options(norm)
      );
      verify(deletePrivateNormPort, never()).deletePublishedNorm(
        new DeletePublishedNormPort.Options(norm)
      );
      verify(updateNormPublishStatePort, never()).updateNormPublishState(any());
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void publishNormToPublicFails() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      ).thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getManifestationEli()))).thenReturn(
        Optional.of(norm)
      );
      doThrow(BucketException.class).when(publishNormPort).publishNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(1)
      ).loadNormManifestationElisByPublishState(
        argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      );
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(publishPrivateNormPort, never()).publishNorm(new PublishNormPort.Options(norm));
      verify(deletePublishedNormPort, never()).deletePublishedNorm(
        new DeletePublishedNormPort.Options(norm)
      );
      verify(deletePrivateNormPort, never()).deletePublishedNorm(
        new DeletePublishedNormPort.Options(norm)
      );
      verify(updateNormPublishStatePort, never()).updateNormPublishState(any());
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void deleteAllNormsIfUnpublishedMigrationLogExists() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      final MigrationLog migrationLog = MigrationLog.builder()
        .xmlSize(5)
        .createdAt(Instant.now())
        .completed(false)
        .build();

      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      ).thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getManifestationEli()))).thenReturn(
        Optional.of(norm)
      );
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog)); // Migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(1)
      ).loadNormManifestationElisByPublishState(
        argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      );

      // Check that deletion was called
      verify(deleteAllPublishedDokumentePort, times(1)).deleteAllPublishedDokumente(
        argThat(command -> command.lastChangeBefore() == migrationLog.getCreatedAt())
      );
      verify(deleteAllPrivateDokumentePort, times(1)).deleteAllPublishedDokumente(
        argThat(command -> command.lastChangeBefore() == migrationLog.getCreatedAt())
      );

      // Verify norm publishing actions
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(updateNormPublishStatePort, times(1)).updateNormPublishState(
        new UpdateNormPublishStatePort.Options(
          norm.getManifestationEli(),
          NormPublishState.PUBLISHED
        )
      );
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void doNotdeleteAllNormsIfMigrationLogExistsButSizeIsZero() {
      // Given
      final MigrationLog migrationLog = MigrationLog.builder()
        .xmlSize(0)
        .createdAt(Instant.now())
        .build();

      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog)); // Migration log found

      // Then When
      assertThatThrownBy(publishService::processQueuedFilesForPublish).isInstanceOf(
        MigrationJobException.class
      );

      // Check that neither loading,nor publish methods nor deletion were called
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(0)
      ).loadNormManifestationElisByPublishState(any());
      verify(loadNormPort, times(0)).loadNorm(any());
      verify(publishNormPort, times(0)).publishNorm(any());
      verify(publishPrivateNormPort, times(0)).publishNorm(any());
      verify(deleteAllPublishedDokumentePort, times(0)).deleteAllPublishedDokumente(any());
      verify(deleteAllPrivateDokumentePort, times(0)).deleteAllPublishedDokumente(any());
    }

    @Test
    void doNotDeleteNormsIfNoMigrationLogExists() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );

      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      ).thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getManifestationEli()))).thenReturn(
        Optional.of(norm)
      );
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.empty()); // No migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(1)
      ).loadNormManifestationElisByPublishState(
        argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      );

      // Verify that deletion was NOT called
      verify(deleteAllPublishedDokumentePort, never()).deleteAllPublishedDokumente(any());
      verify(deleteAllPrivateDokumentePort, never()).deleteAllPublishedDokumente(any());

      // Verify norm publishing actions
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(updateNormPublishStatePort, times(1)).updateNormPublishState(
        new UpdateNormPublishStatePort.Options(
          norm.getManifestationEli(),
          NormPublishState.PUBLISHED
        )
      );
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void doNotDeleteNormsIfMigrationLogIsAlreadyPublished() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      final MigrationLog migrationLog = MigrationLog.builder()
        .xmlSize(5)
        .createdAt(Instant.now())
        .completed(true)
        .build();

      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      ).thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getManifestationEli()))).thenReturn(
        Optional.of(norm)
      );
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog)); // Migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(1)
      ).loadNormManifestationElisByPublishState(
        argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      );

      // Check that deletion was not called
      verify(deleteAllPublishedDokumentePort, never()).deleteAllPublishedDokumente(any());
      verify(deleteAllPrivateDokumentePort, never()).deleteAllPublishedDokumente(any());

      // Verify norm publishing actions
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(updateNormPublishStatePort, times(1)).updateNormPublishState(
        new UpdateNormPublishStatePort.Options(
          norm.getManifestationEli(),
          NormPublishState.PUBLISHED
        )
      );
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }

    @Test
    void deleteAllNormsIfMigrationLogExistsAndIsOldButNotCompleted() {
      // Given
      final Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      final MigrationLog migrationLog = MigrationLog.builder()
        .xmlSize(5)
        .createdAt(Instant.parse("2007-12-03T10:15:30.00Z"))
        .completed(false)
        .build();

      when(
        loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(any())
      ).thenReturn(List.of(norm.getManifestationEli()));
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getManifestationEli()))).thenReturn(
        Optional.of(norm)
      );
      when(loadLastMigrationLogPort.loadLastMigrationLog()).thenReturn(Optional.of(migrationLog)); // Migration log found

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(
        loadNormManifestationElisByPublishStatePort,
        times(1)
      ).loadNormManifestationElisByPublishState(
        argThat(command -> command.publishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      );

      // Check that deletion was called
      verify(deleteAllPublishedDokumentePort, times(1)).deleteAllPublishedDokumente(
        argThat(command -> command.lastChangeBefore() == migrationLog.getCreatedAt())
      );
      verify(deleteAllPrivateDokumentePort, times(1)).deleteAllPublishedDokumente(
        argThat(command -> command.lastChangeBefore() == migrationLog.getCreatedAt())
      );

      // Verify norm publishing actions
      verify(publishNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(publishPrivateNormPort, times(1)).publishNorm(new PublishNormPort.Options(norm));
      verify(updateNormPublishStatePort, times(1)).updateNormPublishState(
        new UpdateNormPublishStatePort.Options(
          norm.getManifestationEli(),
          NormPublishState.PUBLISHED
        )
      );
      verify(publishChangelogPort, times(1)).publishChangelogs(any());
    }
  }
}
