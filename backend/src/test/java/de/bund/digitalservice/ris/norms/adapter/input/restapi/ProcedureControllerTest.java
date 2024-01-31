package de.bund.digitalservice.ris.norms.adapter.input.restapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProcedureController.class)
class ProcedureControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadProcedureUseCase loadProcedureUseCase;

  @Test
  @Disabled
  void itCallsProcedureServiceWithCorrectQueryToGetProcedureByUuid() throws Exception {
    // Given
    final UUID guid = UUID.randomUUID();
    final Procedure procedure =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("someEli")
            .printAnnouncementGazette("someGazette")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("page123")
            .build();
    when(loadProcedureUseCase.loadProcedure(any())).thenReturn(Optional.of(procedure));

    // When
    mockMvc.perform(get("/api/procedures/{guid}", guid)).andExpect(status().isOk());

    // Then
    verify(loadProcedureUseCase, times(1))
        .loadProcedure(argThat(query -> query.uuid().equals(guid)));
  }
}
