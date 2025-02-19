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
    createNewVersionOfNormService,
    deleteNormPort,
    saveReleaseToAnnouncementPort,
    deleteQueuedReleasesPort,
    ldmlDeValidator
  );

  @Test
  void itShouldReleaseAnAnnouncement() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModifications.xml"
    );
    var newNewestUnpublishedManifestationOfNorm = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );

    var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);
    when(deleteQueuedReleasesPort.deleteQueuedReleases(any())).thenReturn(List.of());
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
    verify(deleteQueuedReleasesPort, times(1))
      .deleteQueuedReleases(new DeleteQueuedReleasesPort.Command(announcement.getEli()));
    verify(ldmlDeValidator, times(1))
      .parseAndValidateRegelungstext(
        XmlMapper.toString(manifestationOfNormToQueue.getRegelungstext1().getDocument())
      );
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfNormToQueue);
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(manifestationOfNormToQueue));
    verify(updateOrSaveNormPort, times(1))
      .updateOrSave(new UpdateOrSaveNormPort.Command(newNewestUnpublishedManifestationOfNorm));
    verify(deleteNormPort, times(1))
      .deleteNorm(
        new DeleteNormPort.Command(norm.getManifestationEli(), NormPublishState.UNPUBLISHED)
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
  void itShouldUpdateTheReleasedByDocumentalistAtDate() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

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
        new LoadAnnouncementByNormEliUseCase.Query(norm.getExpressionEli())
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

    var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

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
    when(ldmlDeValidator.parseAndValidateRegelungstext(any()))
      .thenThrow(new LdmlDeNotValidException(List.of()));

    var query = new ReleaseAnnouncementUseCase.Query(norm.getExpressionEli());

    // When
    assertThatThrownBy(() -> releaseService.releaseAnnouncement(query))
      .isInstanceOf(LdmlDeNotValidException.class);

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(ldmlDeValidator, times(1))
      .parseAndValidateRegelungstext(
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

    var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

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
      .validateSchematron(any(Norm.class));

    var query = new ReleaseAnnouncementUseCase.Query(norm.getExpressionEli());

    // When
    assertThatThrownBy(() -> releaseService.releaseAnnouncement(query))
      .isInstanceOf(LdmlDeSchematronException.class);

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(ldmlDeValidator, times(1))
      .parseAndValidateRegelungstext(
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
