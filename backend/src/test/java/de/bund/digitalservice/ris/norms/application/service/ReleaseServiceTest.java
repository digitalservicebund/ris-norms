package de.bund.digitalservice.ris.norms.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAmendingLawAndAllRelatedTargetLawsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawsForAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveAmendingLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ReleaseServiceTest {
  private final LoadAmendingLawPort loadAmendingLawPort = mock(LoadAmendingLawPort.class);
  private final SaveAmendingLawPort saveAmendingLawPort = mock(SaveAmendingLawPort.class);
  private final LoadTargetLawsForAmendingLawPort loadTargetLawsForAmendingLawPort =
      mock(LoadTargetLawsForAmendingLawPort.class);
  private final ReleaseService releaseService =
      new ReleaseService(
          loadAmendingLawPort, saveAmendingLawPort, loadTargetLawsForAmendingLawPort);

  @Test
  void releaseAmendingLaw() {
    ReleaseAmendingLawAndAllRelatedTargetLawsUseCase
        releaseAmendingLawAndAllRelatedTargetLawsUseCase =
            mock(ReleaseAmendingLawAndAllRelatedTargetLawsUseCase.class);
    // Given
    final String eli = "someEli";

    final ReleaseAmendingLawAndAllRelatedTargetLawsUseCase.Query query =
        new ReleaseAmendingLawAndAllRelatedTargetLawsUseCase.Query(eli);
    when(loadAmendingLawPort.loadAmendingLawByEli(any()))
        .thenReturn(Optional.of(AmendingLaw.builder().eli(eli).build()));
    when(saveAmendingLawPort.saveAmendingLawByEli(any()))
        .thenReturn(AmendingLaw.builder().eli(eli).build());

    when(releaseAmendingLawAndAllRelatedTargetLawsUseCase.releaseAmendingLaw(any()))
        .thenReturn(List.of(TargetLaw.builder().eli("some").build()));

    // When
    releaseService.releaseAmendingLaw(query);

    // Then
    verify(loadAmendingLawPort, times(1))
        .loadAmendingLawByEli(argThat(argument -> Objects.equals(argument.eli(), eli)));
    verify(loadTargetLawsForAmendingLawPort, times(1))
        .loadTargetsLawByAmendingLawEli(
            argThat(argument -> Objects.equals(argument.amendingLawEli(), eli)));
  }
}
