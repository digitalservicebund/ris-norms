package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAmendingLawAndAllRelatedTargetLawsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawsForAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAmendingLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ReleaseServiceTest {
  private final LoadAmendingLawPort loadAmendingLawPort = mock(LoadAmendingLawPort.class);
  private final UpdateAmendingLawPort updateAmendingLawPort = mock(UpdateAmendingLawPort.class);
  private final LoadTargetLawsForAmendingLawPort loadTargetLawsForAmendingLawPort =
      mock(LoadTargetLawsForAmendingLawPort.class);
  private final ReleaseService releaseService =
      new ReleaseService(loadAmendingLawPort, updateAmendingLawPort);

  @Test
  void releaseAmendingLaw() {
    // Given
    final String eli = "someEli";

    final ReleaseAmendingLawAndAllRelatedTargetLawsUseCase.Query query =
        new ReleaseAmendingLawAndAllRelatedTargetLawsUseCase.Query(eli);
    when(loadAmendingLawPort.loadAmendingLawByEli(any()))
        .thenReturn(Optional.of(AmendingLaw.builder().eli(eli).build()));
    when(updateAmendingLawPort.updateAmendingLaw(any()))
        .thenReturn(Optional.ofNullable(AmendingLaw.builder().eli(eli).build()));

    // When
    releaseService.releaseAmendingLaw(query);

    // Then
    verify(loadAmendingLawPort, times(1))
        .loadAmendingLawByEli(argThat(argument -> Objects.equals(argument.eli(), eli)));
    verify(updateAmendingLawPort, times(1))
        .updateAmendingLaw(
            argThat(argument -> Objects.equals(argument.amendingLaw().getEli(), eli)));
  }

  @Test
  void canNotFindAmendingLaw() {
    // Given
    final String eli = "someEli";

    final ReleaseAmendingLawAndAllRelatedTargetLawsUseCase.Query query =
        new ReleaseAmendingLawAndAllRelatedTargetLawsUseCase.Query(eli);
    when(loadAmendingLawPort.loadAmendingLawByEli(any())).thenReturn(Optional.empty());

    // When
    Optional<AmendingLaw> result = releaseService.releaseAmendingLaw(query);

    // Then
    verify(loadAmendingLawPort, times(1))
        .loadAmendingLawByEli(argThat(argument -> Objects.equals(argument.eli(), eli)));

    assertThat(result).isEmpty();
  }
}
