package de.bund.digitalservice.ris.norms.adapter.input.restapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProcedureControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadProcedureUseCase loadProcedureUseCase;

  @Test
  void itCallsProcedureServiceWithUuidFromQuery() throws Exception {
    // Given
    final UUID guid = UUID.randomUUID();
    when(loadProcedureUseCase.loadProcedure(any())).thenReturn(Optional.empty());

    // When
    mockMvc.perform(get("/api/v1/norms/procedures/{guid}", guid)).andExpect(status().isOk());

    // Then
    verify(loadProcedureUseCase, times(1))
        .loadProcedure(argThat(query -> query.uuid().equals(guid)));
  }

  @Test
  void itCallsProcedureServiceAndReturnsProcedure() throws Exception {
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

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures/{guid}", guid))
        .andExpect(status().isOk())
        .andExpect(jsonPath("state").value(equalTo("OPEN")))
        .andExpect(jsonPath("eli").value(equalTo("someEli")))
        .andExpect(jsonPath("printAnnouncementGazette").value(equalTo("someGazette")))
        .andExpect(jsonPath("printAnnouncementYear").value(equalTo("2024")))
        .andExpect(jsonPath("printAnnouncementPage").value(equalTo("page123")));
  }
}
