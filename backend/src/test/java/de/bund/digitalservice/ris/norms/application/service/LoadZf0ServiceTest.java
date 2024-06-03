package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.domain.entity.FRBRExpression;
import de.bund.digitalservice.ris.norms.domain.entity.FRBRManifestation;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class LoadZf0ServiceTest {

  final UpdateNormService updateNormService = new UpdateNormService();
  final LoadNormByGuidPort loadNormByGuidPort = mock(LoadNormByGuidPort.class);
  final LoadZf0Service createZf0Service = new LoadZf0Service(updateNormService, loadNormByGuidPort);

  @Test
  void itLoadsZf0FromDB() {

    // Given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    final Norm zf0Law = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    when(loadNormByGuidPort.loadNormByGuid(any())).thenReturn(Optional.of(zf0Law));

    // When
    final Norm zf0NormLoaded =
        createZf0Service.loadZf0(new LoadZf0UseCase.Query(amendingLaw, targetLaw));

    // Then
    assertThat(zf0Law).isEqualTo(zf0NormLoaded);
  }

  @Test
  void itSuccessfullyCreatesZf0OutOfTargetLaw() {

    // Given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

    // When
    final Norm zf0Norm = createZf0Service.loadZf0(new LoadZf0UseCase.Query(amendingLaw, targetLaw));

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
        .contains(amendingLaw.getFBRDateVerkuendung().orElseThrow().toString());
    assertThat(frbrExpressionZf0Law.getFBRDate())
        .isEqualTo(amendingLaw.getFBRDateVerkuendung().map(LocalDate::toString).orElseThrow());

    final FRBRManifestation frbrManifestationZf0Law = zf0Norm.getMeta().getFRBRManifestation();
    assertThat(frbrManifestationZf0Law.getEli()).contains(frbrExpressionZf0Law.getEli());
    assertThat(frbrManifestationZf0Law.getFBRDate()).isEqualTo(LocalDate.now().toString());

    assertThat(targetLaw.getPassiveModifications()).isEmpty();
    assertThat(zf0Norm.getPassiveModifications()).hasSize(1);
    final TextualMod activeMod = amendingLaw.getActiveModifications().getFirst();
    final TextualMod passiveMod = zf0Norm.getPassiveModifications().getFirst();
    assertThat(passiveMod.getType()).isEqualTo(activeMod.getType());
    assertThat(passiveMod.getSourceHref().orElseThrow().getEli().orElseThrow())
        .isEqualTo(amendingLaw.getEli());
    assertThat(passiveMod.getDestinationHref().orElseThrow().getEId())
        .isEqualTo(activeMod.getDestinationHref().orElseThrow().getEId());
    assertThat(passiveMod.getForcePeriodEid()).isNotEmpty();
  }
}
