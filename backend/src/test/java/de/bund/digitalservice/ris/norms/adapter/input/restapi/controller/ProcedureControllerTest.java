package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

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
    final String eli = "eli/bgbl-1/1953/s225";
    when(loadProcedureUseCase.loadProcedure(any())).thenReturn(Optional.empty());

    // When
    mockMvc.perform(get("/api/v1/norms/procedures/{eli}", eli));

    // Then
    verify(loadProcedureUseCase, times(1)).loadProcedure(argThat(query -> query.eli().equals(eli)));
  }

  @Test
  void itCallsProcedureServiceAndReturnsProcedure() throws Exception {
    // Given
    final String eli = "eli/bgbl-1/1953/s225";
    final Procedure procedure =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli(eli)
            .printAnnouncementGazette("someGazette")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("page123")
            .build();
    when(loadProcedureUseCase.loadProcedure(any())).thenReturn(Optional.of(procedure));

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures/{eli}", eli))
        .andExpect(status().isOk())
        .andExpect(jsonPath("state").value(equalTo("OPEN")))
        .andExpect(jsonPath("eli").value(equalTo("eli/bgbl-1/1953/s225")))
        .andExpect(jsonPath("printAnnouncementGazette").value(equalTo("someGazette")))
        .andExpect(jsonPath("printAnnouncementYear").value(equalTo("2024")))
        .andExpect(jsonPath("printAnnouncementPage").value(equalTo("page123")));
  }

  @Test
  void itCallsProcedureServiceAndReturnsNotFound() throws Exception {
    // Given
    final String eli = "eli/bgbl-1/1953/s225";
    when(loadProcedureUseCase.loadProcedure(any())).thenReturn(Optional.empty());

    // When // Then
    mockMvc.perform(get("/api/v1/norms/procedures/{eli}", eli)).andExpect(status().isNotFound());
  }

  @Test
  void itCallsProcedureServiceAndReturnsInternalError() throws Exception {
    // Given
    final String eli = "eli/bgbl-1/1953/s225";
    when(loadProcedureUseCase.loadProcedure(any())).thenThrow(new Error());

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures/{eli}", eli))
        .andExpect(status().is5xxServerError());
  }
}
