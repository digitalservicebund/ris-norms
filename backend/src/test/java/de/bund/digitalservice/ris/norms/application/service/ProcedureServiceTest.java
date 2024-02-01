package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ProcedureServiceTest {

  @Test
  void itCallsLoadProcedureByUuidUsingInputQueryUuid() {
    // Given
    final String eli = "someEli";
    final LoadProcedurePort loadProcedureAdapter = mock(LoadProcedurePort.class);
    final ProcedureService service = new ProcedureService(loadProcedureAdapter);
    final LoadProcedureUseCase.Query query = new LoadProcedureUseCase.Query(eli);
    when(loadProcedureAdapter.loadProcedureByEli(any())).thenReturn(Optional.empty());

    // When
    service.loadProcedure(query);

    // Then
    verify(loadProcedureAdapter, times(1))
        .loadProcedureByEli(argThat(argument -> Objects.equals(argument.eli(), eli)));
  }

  @Test
  void canLoadProcedureByUuidIfAdapterFindsOne() {
    // Given
    final String eli = "someEli";
    final LoadProcedurePort loadProcedureAdapter = mock(LoadProcedurePort.class);
    final ProcedureService service = new ProcedureService(loadProcedureAdapter);
    final LoadProcedureUseCase.Query query = new LoadProcedureUseCase.Query(eli);
    final Procedure procedure =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("someEli")
            .printAnnouncementGazette("someGazette")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("page123")
            .build();
    when(loadProcedureAdapter.loadProcedureByEli(any())).thenReturn(Optional.of(procedure));

    // When
    final Optional<Procedure> procedureLoaded = service.loadProcedure(query);

    // Then
    assertThat(procedureLoaded).isPresent().contains(procedure);
  }

  @Test
  void canNotLoadProcedureByUuidIfAdapterDoesNotFindOne() {
    // Given
    final String eli = "someEli";
    final LoadProcedurePort loadProcedureAdapter = mock(LoadProcedurePort.class);
    final ProcedureService service = new ProcedureService(loadProcedureAdapter);
    final LoadProcedureUseCase.Query query = new LoadProcedureUseCase.Query(eli);
    when(loadProcedureAdapter.loadProcedureByEli(any())).thenReturn(Optional.empty());

    // When
    final Optional<Procedure> procedureLoaded = service.loadProcedure(query);

    // Then
    assertThat(procedureLoaded).isEmpty();
  }
}
