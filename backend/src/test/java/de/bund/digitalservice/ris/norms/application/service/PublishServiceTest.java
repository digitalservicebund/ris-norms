package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
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

  final PublishPublicNormPort publishPublicNormPort = mock(PublishPublicNormPort.class);

  final PublishPrivateNormPort publishPrivateNormPort = mock(PublishPrivateNormPort.class);

  final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);

  final DeletePublicNormPort deletePublicNormPort = mock(DeletePublicNormPort.class);

  final DeletePrivateNormPort deletePrivateNormPort = mock(DeletePrivateNormPort.class);

  final DeleteAllPublicDokumentePort deleteAllPublicDokumentePort = mock(
    DeleteAllPublicDokumentePort.class
  );

  final DeleteAllPrivateDokumentePort deleteAllPrivateDokumentePort = mock(
    DeleteAllPrivateDokumentePort.class
  );
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);

  final LoadLastMigrationLogPort loadLastMigrationLogPort = mock(LoadLastMigrationLogPort.class);

  final PublishChangelogsPort publishChangelogsPort = mock(PublishChangelogsPort.class);

  final CompleteMigrationLogPort updateMigrationLogPort = mock(CompleteMigrationLogPort.class);

  final PublishService publishService = new PublishService(
    loadNormManifestationElisByPublishStatePort,
    loadNormPort,
    publishPublicNormPort,
    publishPrivateNormPort,
    updateOrSaveNormPort,
    deletePublicNormPort,
    deletePrivateNormPort,
    loadLastMigrationLogPort,
    deleteAllPublicDokumentePort,
    deleteAllPrivateDokumentePort,
    publishChangelogsPort,
    updateMigrationLogPort
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
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(1)).publishChangelogs(any());
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
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(1)).publishChangelogs(any());
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
      doThrow(BucketException.class).when(publishPrivateNormPort).publishPrivateNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
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
      verify(publishChangelogsPort, times(1)).publishChangelogs(any());
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
      doThrow(BucketException.class).when(publishPublicNormPort).publishPublicNorm(any());

      // When
      publishService.processQueuedFilesForPublish();

      // Then
      verify(loadNormManifestationElisByPublishStatePort, times(1))
        .loadNormManifestationElisByPublishState(
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
      verify(publishChangelogsPort, times(1)).publishChangelogs(any());
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
      verify(deleteAllPublicDokumentePort, times(1)).deleteAllPublicDokumente(any());
      verify(deleteAllPrivateDokumentePort, times(1)).deleteAllPrivateDokumente(any());

      // Verify norm publishing actions
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(1)).publishChangelogs(any());
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
      verify(deleteAllPublicDokumentePort, times(0)).deleteAllPublicDokumente(any());
      verify(deleteAllPrivateDokumentePort, times(0)).deleteAllPrivateDokumente(any());
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
      verify(deleteAllPublicDokumentePort, never()).deleteAllPublicDokumente(any());
      verify(deleteAllPrivateDokumentePort, never()).deleteAllPrivateDokumente(any());

      // Verify norm publishing actions
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(1)).publishChangelogs(any());
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
      verify(deleteAllPublicDokumentePort, never()).deleteAllPublicDokumente(any());
      verify(deleteAllPrivateDokumentePort, never()).deleteAllPrivateDokumente(any());

      // Verify norm publishing actions
      verify(publishPublicNormPort, times(1))
        .publishPublicNorm(new PublishPublicNormPort.Command(norm));
      verify(publishPrivateNormPort, times(1))
        .publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      verify(updateOrSaveNormPort, times(1)).updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      verify(publishChangelogsPort, times(1)).publishChangelogs(any());
    }
  }

  @Nested
  class prepareForPublish {

    @Test
    void removesOrganisationsEinheitFromMetadata() {
      // Given
      var norm = Fixtures.loadNormFromDisk("NormToBeReleased.xml");
      var proprietary = norm.getRegelungstext1().getMeta().getOrCreateProprietary();

      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT))
        .contains("Aktuelle Organisationseinheit");

      // When
      publishService.prepareForPublish(norm);

      // Then
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
    }
  }
}
