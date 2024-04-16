package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TimeMachineUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawXmlPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateTargetLawXmlPort;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TargetLawServiceTest {

  final LoadTargetLawPort loadTargetLawAdapter = mock(LoadTargetLawPort.class);
  final LoadTargetLawXmlPort loadTargetLawXmlAdapter = mock(LoadTargetLawXmlPort.class);
  final UpdateTargetLawXmlPort updateTargetLawXmlPort = mock(UpdateTargetLawXmlPort.class);

  final TimeMachineService timeMachineService = mock(TimeMachineService.class);
  final UpdateNormXmlUseCase updateNormXmlUseCase = mock(UpdateNormXmlUseCase.class);

  final TargetLawService service =
      new TargetLawService(
          loadTargetLawAdapter,
          loadTargetLawXmlAdapter,
          timeMachineService,
          updateTargetLawXmlPort,
          updateNormXmlUseCase);

  @Test
  void canLoadTargetLawByEli() {
    // Given
    final String eli = "someEli";
    final LoadTargetLawUseCase.Query query = new LoadTargetLawUseCase.Query(eli);

    final String title = "Target Law Title";
    final String xml = "<targetLaw>Test target law</targetLaw>";
    final TargetLaw targetLaw = TargetLaw.builder().eli(eli).title(title).xml(xml).build();

    when(loadTargetLawAdapter.loadTargetLawByEli(any())).thenReturn(Optional.of(targetLaw));

    // When
    final Optional<TargetLaw> loadedTargetLaw = service.loadTargetLaw(query);

    // Then
    assertThat(loadedTargetLaw).isPresent().contains(targetLaw);
    verify(loadTargetLawAdapter, times(1))
        .loadTargetLawByEli(argThat(command -> command.eli().equals(eli)));
  }

  @Test
  void canLoadTargetLawXmlByEli() {
    // Given
    final String eli = "someEli";
    final LoadTargetLawXmlUseCase.Query query = new LoadTargetLawXmlUseCase.Query(eli);

    final String xmlContent = "<targetLaw>Test content</targetLaw>";
    when(loadTargetLawXmlAdapter.loadTargetLawXmlByEli(any())).thenReturn(Optional.of(xmlContent));

    // When
    final Optional<String> loadedXml = service.loadTargetLawXml(query);

    // Then
    assertThat(loadedXml).isPresent().contains(xmlContent);
    verify(loadTargetLawXmlAdapter, times(1))
        .loadTargetLawXmlByEli(argThat(command -> command.eli().equals(eli)));
  }

  @Test
  void bothRelevantMethodsAreCalled() {
    // Given
    final String targetLawEli = "someEli";
    final String amendingLawXmlAsString = "</amendingLawXml>";

    final TimeMachineUseCase.Query query =
        new TimeMachineUseCase.Query(targetLawEli, amendingLawXmlAsString);

    final String targetLawXmlAsString = "<targetLaw>Test content</targetLaw>";
    when(loadTargetLawXmlAdapter.loadTargetLawXmlByEli(any()))
        .thenReturn(Optional.of(targetLawXmlAsString));
    when(timeMachineService.apply(any(), any())).thenReturn("Success");

    // When
    final Optional<String> appliedXml = service.applyTimeMachine(query);

    // Then
    assertThat(appliedXml).isPresent();
    assertThat(appliedXml.get()).contains("Success");
    verify(loadTargetLawXmlAdapter, times(1))
        .loadTargetLawXmlByEli(argThat(command -> command.eli().equals(targetLawEli)));
    verify(timeMachineService, times(1)).apply(amendingLawXmlAsString, targetLawXmlAsString);
  }

  @Test
  void canUpdateTargetLawXmlByEli() throws UpdateNormXmlUseCase.InvalidUpdateException {
    // Given
    final String eli = "someEli";
    final String targetLawXml = "<targetLaw>Test content</targetLaw>";
    final UpdateTargetLawUseCase.Query query = new UpdateTargetLawUseCase.Query(eli, targetLawXml);

    when(updateTargetLawXmlPort.updateTargetLawXmlByEli(any()))
        .thenReturn(Optional.of(targetLawXml));

    // When
    final Optional<String> updatedXml = service.updateTargetLaw(query);

    // Then
    assertThat(updatedXml).isPresent().contains(targetLawXml);
    verify(updateTargetLawXmlPort, times(1))
        .updateTargetLawXmlByEli(
            argThat(
                command -> command.eli().equals(eli) && command.updatedXml().equals(targetLawXml)));
    verify(updateNormXmlUseCase, times(1))
        .updateNormXml(
            argThat(command -> command.eli().equals(eli) && command.xml().equals(targetLawXml)));
  }
}
