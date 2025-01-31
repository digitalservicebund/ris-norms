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
    final Norm amendingLaw = Fixtures.loadNormFromDisk("NormWithMods.xml");
    final Norm targetLaw = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
    final Norm targetLawNewManifestation = Fixtures.loadNormFromDisk(
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
        .getRegelungstext1()
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
    )
      .isEmpty();
    assertThat(
      zf0Norm
        .getRegelungstext1()
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
    )
      .hasSize(1);
    final TextualMod activeMod = amendingLaw
      .getRegelungstext1()
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getFirst();
    final TextualMod passiveMod = zf0Norm
      .getRegelungstext1()
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getPassiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getFirst();
    assertThat(passiveMod.getType()).isEqualTo(activeMod.getType());
    assertThat(passiveMod.getSourceHref().orElseThrow().getExpressionEli().orElseThrow())
      .isEqualTo(amendingLaw.getRegelungstext1().getExpressionEli());
    assertThat(passiveMod.getDestinationHref().orElseThrow().getEId())
      .isEqualTo(activeMod.getDestinationHref().orElseThrow().getEId());
    assertThat(passiveMod.getForcePeriodEid()).isNotEmpty();
  }

  @Test
  void itSuccessfullyCreatesZf0OutOfQuotedStructureWithUpToMods() {
    // Given
    final Norm amendingLaw = Fixtures.loadNormFromDisk("NormWithQuotedStructureModsAndUpTo.xml");
    final Norm targetLaw = Fixtures.loadNormFromDisk(
      "NormWithoutPassiveModsQuotedStructureAndUpTo.xml"
    );
    final Norm targetLawNewManifestation = Fixtures.loadNormFromDisk(
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
        .getRegelungstext1()
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
    )
      .isEmpty();
    assertThat(
      zf0Norm
        .getRegelungstext1()
        .getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
    )
      .hasSize(2);
    final TextualMod firstActiveMod = amendingLaw
      .getRegelungstext1()
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getFirst();
    final TextualMod firstPassiveMod = zf0Norm
      .getRegelungstext1()
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getPassiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getFirst();
    assertThat(firstPassiveMod.getType()).isEqualTo(firstActiveMod.getType());
    assertThat(firstPassiveMod.getSourceHref().orElseThrow().getExpressionEli().orElseThrow())
      .isEqualTo(amendingLaw.getRegelungstext1().getExpressionEli());
    assertThat(firstPassiveMod.getDestinationHref().orElseThrow().getEId())
      .isEqualTo(firstActiveMod.getDestinationHref().orElseThrow().getEId());
    assertThat(firstPassiveMod.getForcePeriodEid()).isNotEmpty();
    assertThat(firstActiveMod.getDestinationHref().orElseThrow().getEId().orElseThrow())
      .isEqualTo(firstPassiveMod.getDestinationHref().orElseThrow().toString().replace("#", ""));
    assertThat(firstActiveMod.getDestinationUpTo().orElseThrow().getEId().orElseThrow())
      .isEqualTo(firstPassiveMod.getDestinationUpTo().orElseThrow().toString().replace("#", ""));

    final TextualMod secondActiveMod = amendingLaw
      .getRegelungstext1()
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getActiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getLast();
    final TextualMod secondPassiveMod = zf0Norm
      .getRegelungstext1()
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getPassiveModifications().stream())
      .orElse(Stream.empty())
      .toList()
      .getLast();
    assertThat(secondPassiveMod.getType()).isEqualTo(secondActiveMod.getType());
    assertThat(secondPassiveMod.getSourceHref().orElseThrow().getExpressionEli().orElseThrow())
      .isEqualTo(amendingLaw.getRegelungstext1().getExpressionEli());
    assertThat(secondPassiveMod.getDestinationHref().orElseThrow().getEId())
      .isEqualTo(secondActiveMod.getDestinationHref().orElseThrow().getEId());
    assertThat(secondPassiveMod.getForcePeriodEid()).isNotEmpty();
    assertThat(secondActiveMod.getDestinationHref().orElseThrow().getEId().orElseThrow())
      .isEqualTo(secondPassiveMod.getDestinationHref().orElseThrow().toString().replace("#", ""));
    assertThat(secondPassiveMod.getDestinationUpTo().orElseThrow().getEId().orElseThrow())
      .isEqualTo(secondPassiveMod.getDestinationUpTo().orElseThrow().toString().replace("#", ""));
  }
}
