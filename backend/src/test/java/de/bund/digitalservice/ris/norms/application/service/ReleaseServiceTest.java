package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAllNormExpressionsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormExpressionElisPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReleaseServiceTest {

  @Mock
  private LoadNormExpressionElisPort loadNormExpressionElisPort;

  @Mock
  private NormService normService;

  @Mock
  private LdmlDeElementSorter ldmlDeElementSorter;

  @Mock
  private LdmlDeValidator ldmlDeValidator;

  @Mock
  private UpdateOrSaveNormPort updateOrSaveNormPort;

  @Mock
  private DeleteNormPort deleteNormPort;

  @Mock
  private PretextCleanupService pretextCleanupService;

  @InjectMocks
  private ReleaseService releaseService;

  @Test
  void shouldSuccessfullyReleaseUnpublishedNorm() {
    // Given
    // the data doesn't matter here since we only check the method calls
    Norm unpublishedNorm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    Norm updatedNorm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    ReleaseAllNormExpressionsUseCase.Options options = createReleaseOptions();
    List<NormExpressionEli> expressionElis = List.of(unpublishedNorm.getExpressionEli());

    when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(expressionElis);
    when(normService.loadNorm(any())).thenReturn(unpublishedNorm);
    when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(updatedNorm);

    // when
    List<Norm> result = releaseService.release(options);

    // than
    assertThat(result).hasSize(1);

    verify(loadNormExpressionElisPort).loadNormExpressionElis(any());
    verify(normService).loadNorm(any());
    verify(ldmlDeElementSorter, times(1)).sortElements(any());
    verify(ldmlDeValidator, times(1)).validateXSDSchema((Norm) any());
    verify(ldmlDeValidator, times(1)).validateSchematron((Norm) any());
    verify(updateOrSaveNormPort, times(1)).updateOrSave(any());
    verify(pretextCleanupService, times(1)).clean(any());
    verifyNoInteractions(deleteNormPort);
  }

  @Test
  void shouldHandleEmptyNormExpressionList() {
    // Arrange
    ReleaseAllNormExpressionsUseCase.Options options = createReleaseOptions();
    when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
      Collections.emptyList()
    );

    // Act
    List<Norm> result = releaseService.release(options);

    // Assert
    assertThat(result).isEmpty();

    verify(loadNormExpressionElisPort).loadNormExpressionElis(any());
    verifyNoInteractions(
      normService,
      ldmlDeElementSorter,
      ldmlDeValidator,
      updateOrSaveNormPort,
      deleteNormPort
    );
  }

  @Test
  void shouldFilterOutAlreadyPublishedNorms() {
    // Arrange
    ReleaseAllNormExpressionsUseCase.Options options = createReleaseOptions();
    List<NormExpressionEli> expressionElis = List.of(
      NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
    );
    Norm publishedNorm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    publishedNorm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

    when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(expressionElis);
    when(normService.loadNorm(any())).thenReturn(publishedNorm);

    // Act
    List<Norm> result = releaseService.release(options);

    // Assert
    assertThat(result).isEmpty();

    verify(normService).loadNorm(any());
    verifyNoInteractions(
      ldmlDeElementSorter,
      ldmlDeValidator,
      updateOrSaveNormPort,
      deleteNormPort
    );
  }

  private static ReleaseAllNormExpressionsUseCase.@NotNull Options createReleaseOptions() {
    return new ReleaseAllNormExpressionsUseCase.Options(
      NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
      ReleaseType.PRAETEXT_RELEASED
    );
  }
}
