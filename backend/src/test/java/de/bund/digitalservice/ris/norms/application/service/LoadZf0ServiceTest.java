package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class LoadZf0ServiceTest {

  final UpdateNormService updateNormService = new UpdateNormService();
  final LoadNormByGuidPort loadNormByGuidPort = mock(LoadNormByGuidPort.class);
  final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadZf0Service loadZf0Service =
      new LoadZf0Service(updateNormService, loadNormByGuidPort, updateOrSaveNormPort, loadNormPort);

  @Test
  void itLoadsZf0FromDB() {

    // Given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    final Norm zf0Law = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    when(loadNormByGuidPort.loadNormByGuid(any())).thenReturn(Optional.of(zf0Law));

    // When
    final Norm zf0NormLoaded =
        loadZf0Service.loadOrCreateZf0(new LoadZf0UseCase.Query(amendingLaw, targetLaw));

    // Then
    assertThat(zf0Law).isEqualTo(zf0NormLoaded);
  }

  @Test
  void itSuccessfullyCreatesZf0OutOfTargetLaw() {

    // Given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

    // When
    final Norm zf0Norm =
        loadZf0Service.loadOrCreateZf0(new LoadZf0UseCase.Query(amendingLaw, targetLaw));

    // Then
    final FRBRExpression frbrExpressionTargetLaw = targetLaw.getMeta().getFRBRExpression();
    final FRBRExpression frbrExpressionZf0Law = zf0Norm.getMeta().getFRBRExpression();
    assertThat(frbrExpressionZf0Law.getFRBRaliasPreviousVersionId())
        .isNotEmpty()
        .contains(frbrExpressionTargetLaw.getFRBRaliasCurrentVersionId());
    assertThat(frbrExpressionZf0Law.getFRBRaliasCurrentVersionId())
        .isEqualTo(frbrExpressionTargetLaw.getFRBRaliasNextVersionId());
    assertThat(frbrExpressionZf0Law.getFRBRaliasNextVersionId()).isNotNull();
    assertThat(frbrExpressionZf0Law.getEli())
        .contains(amendingLaw.getMeta().getFRBRWork().getFBRDate());
    assertThat(frbrExpressionZf0Law.getFBRDate())
        .isEqualTo(amendingLaw.getMeta().getFRBRWork().getFBRDate());

    final FRBRManifestation frbrManifestationZf0Law = zf0Norm.getMeta().getFRBRManifestation();
    assertThat(frbrManifestationZf0Law.getEli()).contains(frbrExpressionZf0Law.getEli());
    assertThat(frbrManifestationZf0Law.getFBRDate()).isEqualTo(LocalDate.now().toString());

    assertThat(
            targetLaw
                .getMeta()
                .getAnalysis()
                .map(analysis -> analysis.getPassiveModifications().stream())
                .orElse(Stream.empty()))
        .isEmpty();
    assertThat(
            zf0Norm
                .getMeta()
                .getAnalysis()
                .map(analysis -> analysis.getPassiveModifications().stream())
                .orElse(Stream.empty()))
        .hasSize(1);
    final TextualMod activeMod =
        amendingLaw
            .getMeta()
            .getAnalysis()
            .map(analysis -> analysis.getActiveModifications().stream())
            .orElse(Stream.empty())
            .toList()
            .getFirst();
    final TextualMod passiveMod =
        zf0Norm
            .getMeta()
            .getAnalysis()
            .map(analysis -> analysis.getPassiveModifications().stream())
            .orElse(Stream.empty())
            .toList()
            .getFirst();
    assertThat(passiveMod.getType()).isEqualTo(activeMod.getType());
    assertThat(passiveMod.getSourceHref().orElseThrow().getEli().orElseThrow())
        .isEqualTo(amendingLaw.getEli());
    assertThat(passiveMod.getDestinationHref().orElseThrow().getEId())
        .isEqualTo(activeMod.getDestinationHref().orElseThrow().getEId());
    assertThat(passiveMod.getForcePeriodEid()).isNotEmpty();
  }

  @Test
  void itLoadsTargetLawFromDB() {

    // Given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    final Norm zf0Law = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    when(loadNormByGuidPort.loadNormByGuid(any())).thenReturn(Optional.of(zf0Law));
    when(loadNormPort.loadNorm((any()))).thenReturn(Optional.of(targetLaw));
    Mod mod = amendingLaw.getMods().getFirst();

    // When
    final Norm zf0NormLoaded = loadZf0Service.loadZf0(amendingLaw, mod);

    // Then
    assertThat(zf0Law).isEqualTo(zf0NormLoaded);
    verify(loadNormPort, times(1)).loadNorm(any(LoadNormPort.Command.class));
  }

  @Test
  void throwExceptionWhenEliIsEmpty() {

    // Given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    final Norm zf0Law = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    when(loadNormByGuidPort.loadNormByGuid(any())).thenReturn(Optional.of(zf0Law));
    when(loadNormPort.loadNorm((any()))).thenReturn(Optional.of(targetLaw));
    Mod mod = amendingLaw.getMods().getFirst();
    mod.setTargetRefHref("");

    // When
    final Throwable thrown = catchThrowable(() -> loadZf0Service.loadZf0(amendingLaw, mod));

    // then
    assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
            "Cannot read target norm eli from mod Optional[hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1]");
  }
}
