package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeSchematronException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadReleasesByNormExpressionEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseNormExpressionUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteQueuedReleasesPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadReleasesByNormExpressionEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveReleasePort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReleaseServiceTest {

  private final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);
  private final NormService normService = mock(NormService.class);
  private final CreateNewVersionOfNormService createNewVersionOfNormService = mock(
    CreateNewVersionOfNormService.class
  );
  private final DeleteNormPort deleteNormPort = mock(DeleteNormPort.class);
  private final SaveReleasePort saveReleasePort = mock(SaveReleasePort.class);
  private final DeleteQueuedReleasesPort deleteQueuedReleasesPort = mock(
    DeleteQueuedReleasesPort.class
  );
  private final LdmlDeValidator ldmlDeValidator = mock(LdmlDeValidator.class);
  private final LoadReleasesByNormExpressionEliPort loadReleasesByNormExpressionEliPort = mock(
    LoadReleasesByNormExpressionEliPort.class
  );
  private final ReleaseService releaseService = new ReleaseService(
    updateOrSaveNormPort,
    normService,
    createNewVersionOfNormService,
    deleteNormPort,
    saveReleasePort,
    deleteQueuedReleasesPort,
    ldmlDeValidator,
    loadReleasesByNormExpressionEliPort
  );

  @Test
  void itShouldReleaseAnVerkuendung() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    var newNewestUnpublishedManifestationOfNorm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15"
    );
    var savedRelease = new Release(Instant.now(), List.of(manifestationOfNormToQueue));

    when(deleteQueuedReleasesPort.deleteQueuedReleases(any())).thenReturn(List.of());
    when(normService.loadNorm(new LoadNormUseCase.EliOptions(norm.getExpressionEli()))).thenReturn(
      norm
    );
    when(createNewVersionOfNormService.createNewManifestation(any())).thenReturn(
      manifestationOfNormToQueue
    );
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    ).thenReturn(newNewestUnpublishedManifestationOfNorm);
    when(saveReleasePort.saveRelease(any())).thenReturn(savedRelease);

    // When
    var returnedRelease = releaseService.releaseNormExpression(
      new ReleaseNormExpressionUseCase.Options(norm.getExpressionEli())
    );

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(
      norm,
      LocalDate.now().plusDays(1)
    );
    verify(deleteQueuedReleasesPort, times(1)).deleteQueuedReleases(
      new DeleteQueuedReleasesPort.Options(norm.getExpressionEli())
    );
    verify(ldmlDeValidator, times(1)).validateXSDSchema(manifestationOfNormToQueue);
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfNormToQueue);
    verify(updateOrSaveNormPort, times(1)).updateOrSave(
      new UpdateOrSaveNormPort.Options(manifestationOfNormToQueue)
    );
    verify(updateOrSaveNormPort, times(1)).updateOrSave(
      new UpdateOrSaveNormPort.Options(newNewestUnpublishedManifestationOfNorm)
    );
    verify(deleteNormPort, times(1)).deleteNorm(
      new DeleteNormPort.Options(norm.getManifestationEli(), NormPublishState.UNPUBLISHED)
    );
    verify(saveReleasePort, times(1)).saveRelease(
      assertArg(command -> {
        assertThat(command.release().getPublishedNorms()).hasSize(1);
        assertThat(command.release().getPublishedNorms()).contains(manifestationOfNormToQueue);
      })
    );

    assertThat(manifestationOfNormToQueue.getPublishState()).isEqualTo(
      NormPublishState.QUEUED_FOR_PUBLISH
    );
    assertThat(newNewestUnpublishedManifestationOfNorm.getPublishState()).isEqualTo(
      NormPublishState.UNPUBLISHED
    );

    assertThat(returnedRelease).isEqualTo(savedRelease);
  }

  @Test
  void itShouldUpdateTheReleasedByDocumentalistAtDate() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    when(normService.loadNorm(any())).thenReturn(norm);
    when(createNewVersionOfNormService.createNewManifestation(any())).thenReturn(norm);

    // When
    var instantBeforeRelease = Instant.now();
    releaseService.releaseNormExpression(
      new ReleaseNormExpressionUseCase.Options(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      )
    );

    // Then
    verify(saveReleasePort, times(1)).saveRelease(
      assertArg(command -> {
        assertThat(command.release().getReleasedAt()).isAfter(instantBeforeRelease);
      })
    );
  }

  @Test
  void itShouldThrowWhenTryingToReleaseXsdInvalidNorm() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    var newNewestUnpublishedManifestationOfNorm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15"
    );

    when(normService.loadNorm(new LoadNormUseCase.EliOptions(norm.getExpressionEli()))).thenReturn(
      norm
    );
    when(createNewVersionOfNormService.createNewManifestation(any())).thenReturn(
      manifestationOfNormToQueue
    );
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    ).thenReturn(newNewestUnpublishedManifestationOfNorm);
    doThrow(new LdmlDeNotValidException(List.of()))
      .when(ldmlDeValidator)
      .validateXSDSchema(manifestationOfNormToQueue);

    var query = new ReleaseNormExpressionUseCase.Options(norm.getExpressionEli());

    // When
    assertThatThrownBy(() -> releaseService.releaseNormExpression(query)).isInstanceOf(
      LdmlDeNotValidException.class
    );

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(ldmlDeValidator, times(1)).validateXSDSchema(manifestationOfNormToQueue);
    verify(ldmlDeValidator, times(0)).validateSchematron(any(Norm.class));
    verify(createNewVersionOfNormService, times(0)).createNewManifestation(
      norm,
      LocalDate.now().plusDays(1)
    );
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(saveReleasePort, times(0)).saveRelease(any());

    assertThat(manifestationOfNormToQueue.getPublishState()).isEqualTo(
      NormPublishState.UNPUBLISHED
    );
  }

  @Test
  void itShouldThrowWhenTryingToReleaseSchematronInvalidNorm() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // these are just arbitrary norm files, it's not important what is in them just that they are all different.
    var manifestationOfNormToQueue = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    var newNewestUnpublishedManifestationOfNorm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15"
    );

    when(normService.loadNorm(new LoadNormUseCase.EliOptions(norm.getExpressionEli()))).thenReturn(
      norm
    );
    when(createNewVersionOfNormService.createNewManifestation(any())).thenReturn(
      manifestationOfNormToQueue
    );
    when(
      createNewVersionOfNormService.createNewManifestation(any(), eq(LocalDate.now().plusDays(1)))
    ).thenReturn(newNewestUnpublishedManifestationOfNorm);
    doThrow(new LdmlDeSchematronException(List.of()))
      .when(ldmlDeValidator)
      .validateSchematron(any(Norm.class));

    var query = new ReleaseNormExpressionUseCase.Options(norm.getExpressionEli());

    // When
    assertThatThrownBy(() -> releaseService.releaseNormExpression(query)).isInstanceOf(
      LdmlDeSchematronException.class
    );

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(norm);
    verify(ldmlDeValidator, times(1)).validateXSDSchema(manifestationOfNormToQueue);
    verify(ldmlDeValidator, times(1)).validateSchematron(manifestationOfNormToQueue);
    verify(createNewVersionOfNormService, times(0)).createNewManifestation(
      norm,
      LocalDate.now().plusDays(1)
    );
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    verify(saveReleasePort, times(0)).saveRelease(any());

    assertThat(manifestationOfNormToQueue.getPublishState()).isEqualTo(
      NormPublishState.UNPUBLISHED
    );
  }

  @Nested
  class loadReleasesByNormExpressionEli {

    @Test
    void itLoadsReleases() {
      // Given
      var release1 = new Release(Instant.now(), List.of());
      var release2 = new Release(Instant.now(), List.of());

      when(loadReleasesByNormExpressionEliPort.loadReleasesByNormExpressionEli(any())).thenReturn(
        List.of(release1, release2)
      );

      // When
      var releases = releaseService.loadReleasesByNormExpressionEli(
        new LoadReleasesByNormExpressionEliUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      assertThat(releases).containsExactlyInAnyOrder(release1, release2);

      verify(loadReleasesByNormExpressionEliPort, times(1)).loadReleasesByNormExpressionEli(
        assertArg(command -> {
          assertThat(command.eli()).isEqualTo(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          );
        })
      );
    }
  }
}
