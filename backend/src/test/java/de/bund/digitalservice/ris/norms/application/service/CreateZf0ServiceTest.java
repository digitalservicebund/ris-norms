package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.CreateZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class CreateZf0ServiceTest {

  final UpdateNormService updateNormService = new UpdateNormService();
  final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);
  final CreateNewVersionOfNormService createNewVersionOfNormService = mock(
    CreateNewVersionOfNormService.class
  );
  final CreateZf0Service loadZf0Service = new CreateZf0Service(
    updateNormService,
    updateOrSaveNormPort,
    createNewVersionOfNormService
  );

  @Test
  void itSuccessfullyCreatesZf0OutOfTargetLaw() {
    // Given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    final Norm targetLawNewManifestation = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModificationsNoNextVersion.xml"
    );

    when(createNewVersionOfNormService.createNewManifestation(any()))
      .thenReturn(targetLawNewManifestation);

    // When
    final Norm zf0Norm = loadZf0Service.createZf0(
      new CreateZf0UseCase.Query(amendingLaw, targetLaw)
    );

    // Then
    verify(createNewVersionOfNormService, times(1)).createNewManifestation(targetLaw);
    assertThat(
      targetLaw
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
    )
      .isEmpty();
    assertThat(
      zf0Norm
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
    )
      .hasSize(1);
    final TextualMod activeMod = amendingLaw
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getFirst();
    final TextualMod passiveMod = zf0Norm
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getPassiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getFirst();
    assertThat(passiveMod.getType()).isEqualTo(activeMod.getType());
    assertThat(passiveMod.getSourceHref().orElseThrow().getExpressionEli().orElseThrow())
      .isEqualTo(amendingLaw.getExpressionEli());
    assertThat(passiveMod.getDestinationHref().orElseThrow().getEId())
      .isEqualTo(activeMod.getDestinationHref().orElseThrow().getEId());
    assertThat(passiveMod.getForcePeriodEid()).isNotEmpty();
  }

  @Test
  void itSuccessfullyCreatesZf0OutOfQuotedStructureWithUpToMods() {
    // Given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithQuotedStructureModsAndUpTo.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModsQuotedStructureAndUpTo.xml"
    );
    final Norm targetLawNewManifestation = NormFixtures.loadFromDisk(
      "NormWithoutPassiveModsQuotedStructureAndUpTo.xml"
    );

    when(createNewVersionOfNormService.createNewManifestation(any()))
      .thenReturn(targetLawNewManifestation);

    // When
    final Norm zf0Norm = loadZf0Service.createZf0(
      new CreateZf0UseCase.Query(amendingLaw, targetLaw)
    );

    // Then

    assertThat(
      targetLaw
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
    )
      .isEmpty();
    assertThat(
      zf0Norm
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
    )
      .hasSize(2);
    final TextualMod firstActiveMod = amendingLaw
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getFirst();
    final TextualMod firstPassiveMod = zf0Norm
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getPassiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getFirst();
    assertThat(firstPassiveMod.getType()).isEqualTo(firstActiveMod.getType());
    assertThat(firstPassiveMod.getSourceHref().orElseThrow().getExpressionEli().orElseThrow())
      .isEqualTo(amendingLaw.getExpressionEli());
    assertThat(firstPassiveMod.getDestinationHref().orElseThrow().getEId())
      .isEqualTo(firstActiveMod.getDestinationHref().orElseThrow().getEId());
    assertThat(firstPassiveMod.getForcePeriodEid()).isNotEmpty();
    assertThat(firstActiveMod.getDestinationHref().orElseThrow().getEId().orElseThrow())
      .isEqualTo(firstPassiveMod.getDestinationHref().orElseThrow().toString().replace("#", ""));
    assertThat(firstActiveMod.getDestinationUpTo().orElseThrow().getEId().orElseThrow())
      .isEqualTo(firstPassiveMod.getDestinationUpTo().orElseThrow().toString().replace("#", ""));

    final TextualMod secondActiveMod = amendingLaw
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getLast();
    final TextualMod secondPassiveMod = zf0Norm
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getPassiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getLast();
    assertThat(secondPassiveMod.getType()).isEqualTo(secondActiveMod.getType());
    assertThat(secondPassiveMod.getSourceHref().orElseThrow().getExpressionEli().orElseThrow())
      .isEqualTo(amendingLaw.getExpressionEli());
    assertThat(secondPassiveMod.getDestinationHref().orElseThrow().getEId())
      .isEqualTo(secondActiveMod.getDestinationHref().orElseThrow().getEId());
    assertThat(secondPassiveMod.getForcePeriodEid()).isNotEmpty();
    assertThat(secondActiveMod.getDestinationHref().orElseThrow().getEId().orElseThrow())
      .isEqualTo(secondPassiveMod.getDestinationHref().orElseThrow().toString().replace("#", ""));
    assertThat(secondPassiveMod.getDestinationUpTo().orElseThrow().getEId().orElseThrow())
      .isEqualTo(secondPassiveMod.getDestinationUpTo().orElseThrow().toString().replace("#", ""));
  }
}
