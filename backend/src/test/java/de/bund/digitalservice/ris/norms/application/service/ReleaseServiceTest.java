package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeSchematronException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteQueuedNormsPort;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteUnpublishedNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReleaseServiceTest {

  private final LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase = mock(
    LoadAnnouncementByNormEliUseCase.class
  );
  private final UpdateAnnouncementPort updateAnnouncementPort = mock(UpdateAnnouncementPort.class);
  private final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);
  private final NormService normService = mock(NormService.class);
  private final TimeMachineService timeMachineService = mock(TimeMachineService.class);
  private final CreateNewVersionOfNormService createNewVersionOfNormService = mock(
    CreateNewVersionOfNormService.class
  );
  private final DeleteQueuedNormsPort deleteQueuedNormsPort = mock(DeleteQueuedNormsPort.class);
  private final DeleteUnpublishedNormPort deleteUnpublishedNormPort = mock(
    DeleteUnpublishedNormPort.class
  );
  private final LdmlDeValidator ldmlDeValidator = mock(LdmlDeValidator.class);
  private final ReleaseService releaseService = new ReleaseService(
    loadAnnouncementByNormEliUseCase,
    updateAnnouncementPort,
    updateOrSaveNormPort,
    normService,
    timeMachineService,
    createNewVersionOfNormService,
    deleteQueuedNormsPort,
    deleteUnpublishedNormPort,
    ldmlDeValidator
  );

  @Test
  void itShouldReleaseAnAnnouncementWithoutTargetNorms() {
    // Given
    var norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModifications.xml"
    );
    var newNewestUnpublishedManifestationOfNorm = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );

    var announcement = Announcement
      .builder()
      .eli(norm.getExpressionEli())
      .releasedByDocumentalistAt(null)
      .build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(normService.loadNorm(argThat(command -> command.eli().equals(norm.getExpressionEli()))))
      .thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any()))
      .thenReturn(manifestationOfNormToQueue);
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    )
      .thenReturn(newNewestUnpublishedManifestationOfNorm);

    // When
    releaseService.releaseAnnouncement(
      new ReleaseAnnouncementUseCase.Query(norm.getExpressionEli())
    );

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(createNewVersionOfNormService, times(1))
      .createNewManifestation(norm, LocalDate.now().plusDays(1));
    verify(deleteQueuedNormsPort, times(1))
      .deleteQueuedForPublishNorms(new DeleteQueuedNormsPort.Command(norm.getWorkEli()));
    verify(ldmlDeValidator, times(1))
      .parseAndValidate(XmlMapper.toString(manifestationOfNormToQueue.getDocument()));
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfNormToQueue);
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(manifestationOfNormToQueue));
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(newNewestUnpublishedManifestationOfNorm));
    verify(deleteUnpublishedNormPort, times(1))
      .deleteUnpublishedNorm(new DeleteUnpublishedNormPort.Command(norm.getManifestationEli()));
    verify(updateAnnouncementPort, times(1)).updateAnnouncement(any());

    assertThat(manifestationOfNormToQueue.getPublishState())
      .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    assertThat(newNewestUnpublishedManifestationOfNorm.getPublishState())
      .isEqualTo(NormPublishState.UNPUBLISHED);

    assertThat(announcement.getReleases()).hasSize(1);
    assertThat(announcement.getReleases().getFirst().getPublishedNorms()).hasSize(1);
    assertThat(announcement.getReleases().getFirst().getPublishedNorms())
      .contains(manifestationOfNormToQueue);
  }

  @Test
  void itShouldReleaseAnAnnouncementWithTargetNorm() {
    // Given
    var amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    var targetNorm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var targetNormExpressionAtDateOne = NormFixtures.loadFromDisk(
      "NormWithAppliedQuotedStructure.xml"
    );
    var manifestationOfAmendingNormToQueue = NormFixtures.loadFromDisk(
      "NormWithModsSameTarget.xml"
    );
    var manifestationOfTargetNormToUseForCreatingExpressions = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );
    var manifestationOfTargetNormToUseInTimeMachine = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModificationsSameTarget.xml"
    );
    var manifestationOfTargetNormToQueue = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModsQuotedStructure.xml"
    );
    var newNewestUnpublishedManifestationOfAmendingNorm = NormFixtures.loadFromDisk(
      "NormWithMultipleMods.xml"
    );
    var newNewestUnpublishedManifestationOfTargetNorm = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModsQuotedStructureAndUpTo.xml"
    );

    var announcement = Announcement
      .builder()
      .eli(amendingNorm.getExpressionEli())
      .releasedByDocumentalistAt(null)
      .build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(normService.loadNorm(any())).thenReturn(amendingNorm).thenReturn(targetNorm);
    when(createNewVersionOfNormService.createNewManifestation(amendingNorm))
      .thenReturn(manifestationOfAmendingNormToQueue);
    when(createNewVersionOfNormService.createNewManifestation(targetNorm))
      .thenReturn(manifestationOfTargetNormToUseForCreatingExpressions);
    when(
      createNewVersionOfNormService.createNewExpression(any(), eq(LocalDate.parse("2017-03-23")))
    )
      .thenReturn(
        new CreateNewVersionOfNormService.CreateNewExpressionResult(
          targetNormExpressionAtDateOne,
          manifestationOfTargetNormToUseInTimeMachine
        )
      );
    when(timeMachineService.applyPassiveModifications(any()))
      .thenReturn(manifestationOfTargetNormToQueue);
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    )
      .thenReturn(newNewestUnpublishedManifestationOfAmendingNorm)
      .thenReturn(newNewestUnpublishedManifestationOfTargetNorm);

    // When
    releaseService.releaseAnnouncement(
      new ReleaseAnnouncementUseCase.Query(amendingNorm.getExpressionEli())
    );

    // Then
    // previously queued norms are deleted
    verify(deleteQueuedNormsPort, times(1))
      .deleteQueuedForPublishNorms(new DeleteQueuedNormsPort.Command(amendingNorm.getWorkEli()));
    verify(deleteQueuedNormsPort, times(1))
      .deleteQueuedForPublishNorms(new DeleteQueuedNormsPort.Command(targetNorm.getWorkEli()));

    // results are validated
    verify(ldmlDeValidator, times(1))
      .parseAndValidate(XmlMapper.toString(manifestationOfAmendingNormToQueue.getDocument()));
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfAmendingNormToQueue);

    // the queue norms are saved
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(manifestationOfAmendingNormToQueue));
    assertThat(manifestationOfAmendingNormToQueue.getPublishState())
      .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(manifestationOfTargetNormToQueue));
    assertThat(manifestationOfTargetNormToQueue.getPublishState())
      .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(manifestationOfTargetNormToUseInTimeMachine));
    assertThat(manifestationOfTargetNormToUseInTimeMachine.getPublishState())
      .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

    // the old manifestations for editing are deleted (as they might no longer be the newest manifestation, that's why new once are created)
    verify(deleteUnpublishedNormPort, times(1))
      .deleteUnpublishedNorm(
        new DeleteUnpublishedNormPort.Command(amendingNorm.getManifestationEli())
      );
    verify(deleteUnpublishedNormPort, times(1))
      .deleteUnpublishedNorm(
        new DeleteUnpublishedNormPort.Command(targetNorm.getManifestationEli())
      );

    // new manifestations for further editing are saved
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(
        new UpdateOrSaveNormPort.Command(newNewestUnpublishedManifestationOfAmendingNorm)
      );
    assertThat(newNewestUnpublishedManifestationOfAmendingNorm.getPublishState())
      .isEqualTo(NormPublishState.UNPUBLISHED);
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(
        new UpdateOrSaveNormPort.Command(newNewestUnpublishedManifestationOfTargetNorm)
      );
    assertThat(newNewestUnpublishedManifestationOfTargetNorm.getPublishState())
      .isEqualTo(NormPublishState.UNPUBLISHED);

    // the announcement is updated
    verify(updateAnnouncementPort, times(1)).updateAnnouncement(any());

    assertThat(announcement.getReleases()).hasSize(1);
    assertThat(announcement.getReleases().getFirst().getPublishedNorms()).hasSize(3);
    assertThat(announcement.getReleases().getFirst().getPublishedNorms())
      .contains(manifestationOfAmendingNormToQueue);
    assertThat(announcement.getReleases().getFirst().getPublishedNorms())
      .contains(manifestationOfTargetNormToQueue);
    assertThat(announcement.getReleases().getFirst().getPublishedNorms())
      .contains(manifestationOfTargetNormToUseInTimeMachine);
  }

  @Test
  void itShouldUpdateTheReleasedByDocumentalistAtDate() {
    // Given
    var norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    var announcement = Announcement
      .builder()
      .eli(norm.getExpressionEli())
      .releasedByDocumentalistAt(null)
      .build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(normService.loadNorm(any())).thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any())).thenReturn(norm);

    // When
    var instantBeforeRelease = Instant.now();
    releaseService.releaseAnnouncement(
      new ReleaseAnnouncementUseCase.Query(
        ExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
      )
    );

    // Then
    verify(loadAnnouncementByNormEliUseCase, times(1))
      .loadAnnouncementByNormEli(
        new LoadAnnouncementByNormEliUseCase.Query(norm.getExpressionEli())
      );

    verify(updateAnnouncementPort, times(1))
      .updateAnnouncement(new UpdateAnnouncementPort.Command(announcement));
    assertThat(announcement.getReleasedByDocumentalistAt()).isAfter(instantBeforeRelease);
    assertThat(announcement.getReleases()).hasSize(1);
    assertThat(announcement.getReleases().getFirst().getReleasedAt()).isAfter(instantBeforeRelease);
  }

  @Test
  void itShouldThrowWhenTryingToReleaseXsdInvalidNorm() {
    // Given
    var norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModifications.xml"
    );
    var newNewestUnpublishedManifestationOfNorm = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );

    var announcement = Announcement
      .builder()
      .eli(norm.getExpressionEli())
      .releasedByDocumentalistAt(null)
      .build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(normService.loadNorm(argThat(command -> command.eli().equals(norm.getExpressionEli()))))
      .thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any()))
      .thenReturn(manifestationOfNormToQueue);
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    )
      .thenReturn(newNewestUnpublishedManifestationOfNorm);
    when(ldmlDeValidator.parseAndValidate(any())).thenThrow(new LdmlDeNotValidException(List.of()));

    var query = new ReleaseAnnouncementUseCase.Query(norm.getExpressionEli());

    // When
    assertThatThrownBy(() -> releaseService.releaseAnnouncement(query))
      .isInstanceOf(LdmlDeNotValidException.class);

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(deleteQueuedNormsPort, times(1))
      .deleteQueuedForPublishNorms(new DeleteQueuedNormsPort.Command(norm.getWorkEli()));
    verify(ldmlDeValidator, times(1))
      .parseAndValidate(XmlMapper.toString(manifestationOfNormToQueue.getDocument()));
    verify(ldmlDeValidator, times(0)).validateSchematron(any());
    verify(createNewVersionOfNormService, times(0))
      .createNewManifestation(norm, LocalDate.now().plusDays(1));
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(deleteUnpublishedNormPort, times(0)).deleteUnpublishedNorm(any());
    verify(updateAnnouncementPort, times(0)).updateAnnouncement(any());

    assertThat(manifestationOfNormToQueue.getPublishState())
      .isEqualTo(NormPublishState.UNPUBLISHED);
  }

  @Test
  void itShouldThrowWhenTryingToReleaseSchematronInvalidNorm() {
    // Given
    var norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModifications.xml"
    );
    var newNewestUnpublishedManifestationOfNorm = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );

    var announcement = Announcement
      .builder()
      .eli(norm.getExpressionEli())
      .releasedByDocumentalistAt(null)
      .build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(normService.loadNorm(argThat(command -> command.eli().equals(norm.getExpressionEli()))))
      .thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any()))
      .thenReturn(manifestationOfNormToQueue);
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    )
      .thenReturn(newNewestUnpublishedManifestationOfNorm);
    doThrow(new LdmlDeSchematronException(List.of()))
      .when(ldmlDeValidator)
      .validateSchematron(any());

    var query = new ReleaseAnnouncementUseCase.Query(norm.getExpressionEli());

    // When
    assertThatThrownBy(() -> releaseService.releaseAnnouncement(query))
      .isInstanceOf(LdmlDeSchematronException.class);

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(deleteQueuedNormsPort, times(1))
      .deleteQueuedForPublishNorms(new DeleteQueuedNormsPort.Command(norm.getWorkEli()));
    verify(ldmlDeValidator, times(1))
      .parseAndValidate(XmlMapper.toString(manifestationOfNormToQueue.getDocument()));
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfNormToQueue);
    verify(createNewVersionOfNormService, times(0))
      .createNewManifestation(norm, LocalDate.now().plusDays(1));
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(deleteUnpublishedNormPort, times(0)).deleteUnpublishedNorm(any());
    verify(updateAnnouncementPort, times(0)).updateAnnouncement(any());

    assertThat(manifestationOfNormToQueue.getPublishState())
      .isEqualTo(NormPublishState.UNPUBLISHED);
  }

  @Nested
  class prepareForRelease {

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
      releaseService.prepareForRelease(norm);

      // Then
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2005, 1, 1))).isEmpty();
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2028, 6, 1))).isEmpty();
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2029, 6, 1))).isEmpty();
    }
  }
}
