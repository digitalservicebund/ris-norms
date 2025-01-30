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
import de.bund.digitalservice.ris.norms.application.port.output.DeleteNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteQueuedReleasesPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveReleaseToAnnouncementPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReleaseServiceTest {

  private final LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase = mock(
    LoadAnnouncementByNormEliUseCase.class
  );
  private final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);
  private final NormService normService = mock(NormService.class);
  private final TimeMachineService timeMachineService = mock(TimeMachineService.class);
  private final CreateNewVersionOfNormService createNewVersionOfNormService = mock(
    CreateNewVersionOfNormService.class
  );
  private final DeleteNormPort deleteNormPort = mock(DeleteNormPort.class);
  private final SaveReleaseToAnnouncementPort saveReleaseToAnnouncementPort = mock(
    SaveReleaseToAnnouncementPort.class
  );
  private final DeleteQueuedReleasesPort deleteQueuedReleasesPort = mock(
    DeleteQueuedReleasesPort.class
  );
  private final LdmlDeValidator ldmlDeValidator = mock(LdmlDeValidator.class);
  private final ReleaseService releaseService = new ReleaseService(
    loadAnnouncementByNormEliUseCase,
    updateOrSaveNormPort,
    normService,
    timeMachineService,
    createNewVersionOfNormService,
    deleteNormPort,
    saveReleaseToAnnouncementPort,
    deleteQueuedReleasesPort,
    ldmlDeValidator
  );

  @Test
  void itShouldReleaseAnAnnouncementWithoutTargetNorms() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModifications.xml"
    );
    var newNewestUnpublishedManifestationOfNorm = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );

    var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(deleteQueuedReleasesPort.deleteQueuedReleases(any())).thenReturn(List.of());
    when(
      normService.loadNorm(argThat(command -> command.eli().equals(norm.getNormExpressionEli())))
    )
      .thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any()))
      .thenReturn(manifestationOfNormToQueue);
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    )
      .thenReturn(newNewestUnpublishedManifestationOfNorm);

    // When
    releaseService.releaseAnnouncement(
      new ReleaseAnnouncementUseCase.Query(norm.getNormExpressionEli())
    );

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(createNewVersionOfNormService, times(1))
      .createNewManifestation(norm, LocalDate.now().plusDays(1));
    verify(deleteQueuedReleasesPort, times(1))
      .deleteQueuedReleases(new DeleteQueuedReleasesPort.Command(announcement.getEli()));
    verify(ldmlDeValidator, times(1))
      .parseAndValidate(
        XmlMapper.toString(manifestationOfNormToQueue.getRegelungstext1().getDocument())
      );
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfNormToQueue);
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(manifestationOfNormToQueue));
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(newNewestUnpublishedManifestationOfNorm));
    verify(deleteNormPort, times(1))
      .deleteNorm(
        new DeleteNormPort.Command(norm.getNormManifestationEli(), NormPublishState.UNPUBLISHED)
      );
    verify(saveReleaseToAnnouncementPort, times(1)).saveReleaseToAnnouncement(any());

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
    var amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
    var targetNorm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var targetNormExpressionAtDateOne = Fixtures.loadNormFromDisk(
      "NormWithAppliedQuotedStructure.xml"
    );
    var manifestationOfAmendingNormToQueue = Fixtures.loadNormFromDisk(
      "NormWithModsSameTarget.xml"
    );
    var manifestationOfTargetNormToUseForCreatingExpressions = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );
    var manifestationOfTargetNormToUseInTimeMachine = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModificationsSameTarget.xml"
    );
    var manifestationOfTargetNormToQueue = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModsQuotedStructure.xml"
    );
    var newNewestUnpublishedManifestationOfAmendingNorm = Fixtures.loadNormFromDisk(
      "NormWithMultipleMods.xml"
    );
    var newNewestUnpublishedManifestationOfTargetNorm = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModsQuotedStructureAndUpTo.xml"
    );

    var announcement = Announcement.builder().eli(amendingNorm.getNormExpressionEli()).build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(normService.loadNorm(any())).thenReturn(amendingNorm).thenReturn(targetNorm);
    when(createNewVersionOfNormService.createNewManifestation(amendingNorm))
      .thenReturn(manifestationOfAmendingNormToQueue);
    when(createNewVersionOfNormService.createNewManifestation(targetNorm))
      .thenReturn(manifestationOfTargetNormToUseForCreatingExpressions);
    when(deleteQueuedReleasesPort.deleteQueuedReleases(any())).thenReturn(List.of());
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
      .thenReturn(manifestationOfTargetNormToQueue.getRegelungstext1());
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    )
      .thenReturn(newNewestUnpublishedManifestationOfAmendingNorm)
      .thenReturn(newNewestUnpublishedManifestationOfTargetNorm);

    // When
    releaseService.releaseAnnouncement(
      new ReleaseAnnouncementUseCase.Query(amendingNorm.getNormExpressionEli())
    );

    // Then
    // previously queued norms are deleted
    verify(deleteQueuedReleasesPort, times(1))
      .deleteQueuedReleases(new DeleteQueuedReleasesPort.Command(announcement.getEli()));

    // results are validated
    verify(ldmlDeValidator, times(1))
      .parseAndValidate(
        XmlMapper.toString(manifestationOfAmendingNormToQueue.getRegelungstext1().getDocument())
      );
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfAmendingNormToQueue);

    // the queue norms are saved
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(manifestationOfAmendingNormToQueue));
    assertThat(manifestationOfAmendingNormToQueue.getPublishState())
      .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(
        argThat(command ->
          command.norm().getPublishState().equals(NormPublishState.QUEUED_FOR_PUBLISH) &&
          command.norm().equals(manifestationOfTargetNormToQueue)
        )
      );
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(manifestationOfTargetNormToUseInTimeMachine));
    assertThat(manifestationOfTargetNormToUseInTimeMachine.getPublishState())
      .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

    // the old manifestations for editing are deleted (as they might no longer be the newest manifestation, that's why new once are created)
    verify(deleteNormPort, times(1))
      .deleteNorm(
        new DeleteNormPort.Command(
          amendingNorm.getNormManifestationEli(),
          NormPublishState.UNPUBLISHED
        )
      );
    verify(deleteNormPort, times(1))
      .deleteNorm(
        new DeleteNormPort.Command(
          targetNorm.getNormManifestationEli(),
          NormPublishState.UNPUBLISHED
        )
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
    verify(saveReleaseToAnnouncementPort, times(1)).saveReleaseToAnnouncement(any());

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
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(normService.loadNorm(any())).thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any())).thenReturn(norm);

    // When
    var instantBeforeRelease = Instant.now();
    releaseService.releaseAnnouncement(
      new ReleaseAnnouncementUseCase.Query(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      )
    );

    // Then
    verify(loadAnnouncementByNormEliUseCase, times(1))
      .loadAnnouncementByNormEli(
        new LoadAnnouncementByNormEliUseCase.Query(norm.getNormExpressionEli())
      );

    verify(saveReleaseToAnnouncementPort, times(1)).saveReleaseToAnnouncement(any());
    assertThat(announcement.getReleases()).hasSize(1);
    assertThat(announcement.getReleases().getFirst().getReleasedAt()).isAfter(instantBeforeRelease);
  }

  @Test
  void itShouldThrowWhenTryingToReleaseXsdInvalidNorm() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");

    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModifications.xml"
    );
    var newNewestUnpublishedManifestationOfNorm = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );

    var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(
      normService.loadNorm(argThat(command -> command.eli().equals(norm.getNormExpressionEli())))
    )
      .thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any()))
      .thenReturn(manifestationOfNormToQueue);
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    )
      .thenReturn(newNewestUnpublishedManifestationOfNorm);
    when(ldmlDeValidator.parseAndValidate(any())).thenThrow(new LdmlDeNotValidException(List.of()));

    var query = new ReleaseAnnouncementUseCase.Query(norm.getNormExpressionEli());

    // When
    assertThatThrownBy(() -> releaseService.releaseAnnouncement(query))
      .isInstanceOf(LdmlDeNotValidException.class);

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(ldmlDeValidator, times(1))
      .parseAndValidate(
        XmlMapper.toString(manifestationOfNormToQueue.getRegelungstext1().getDocument())
      );
    verify(ldmlDeValidator, times(0)).validateSchematron(any(Norm.class));
    verify(createNewVersionOfNormService, times(0))
      .createNewManifestation(norm, LocalDate.now().plusDays(1));
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(saveReleaseToAnnouncementPort, times(0)).saveReleaseToAnnouncement(any());

    assertThat(manifestationOfNormToQueue.getPublishState())
      .isEqualTo(NormPublishState.UNPUBLISHED);
  }

  @Test
  void itShouldThrowWhenTryingToReleaseSchematronInvalidNorm() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");

    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModifications.xml"
    );
    var newNewestUnpublishedManifestationOfNorm = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );

    var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(
      normService.loadNorm(argThat(command -> command.eli().equals(norm.getNormExpressionEli())))
    )
      .thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any()))
      .thenReturn(manifestationOfNormToQueue);
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    )
      .thenReturn(newNewestUnpublishedManifestationOfNorm);
    doThrow(new LdmlDeSchematronException(List.of()))
      .when(ldmlDeValidator)
      .validateSchematron(any(Norm.class));

    var query = new ReleaseAnnouncementUseCase.Query(norm.getNormExpressionEli());

    // When
    assertThatThrownBy(() -> releaseService.releaseAnnouncement(query))
      .isInstanceOf(LdmlDeSchematronException.class);

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(ldmlDeValidator, times(1))
      .parseAndValidate(
        XmlMapper.toString(manifestationOfNormToQueue.getRegelungstext1().getDocument())
      );
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfNormToQueue);
    verify(createNewVersionOfNormService, times(0))
      .createNewManifestation(norm, LocalDate.now().plusDays(1));
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(saveReleaseToAnnouncementPort, times(0)).saveReleaseToAnnouncement(any());

    assertThat(manifestationOfNormToQueue.getPublishState())
      .isEqualTo(NormPublishState.UNPUBLISHED);
  }
}
