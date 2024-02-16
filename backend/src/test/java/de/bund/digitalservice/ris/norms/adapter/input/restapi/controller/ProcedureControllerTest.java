package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions.InternalErrorExceptionHandler;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllProceduresUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Therefore, manually
 * setting up the {@code mockMvc} including the ControllerAdvice
 */
@ExtendWith(SpringExtension.class)
class ProcedureControllerTest {

  private MockMvc mockMvc;

  @MockBean private LoadProcedureUseCase loadProcedureUseCase;
  @MockBean private LoadAllProceduresUseCase loadAllProceduresUseCase;

  @BeforeEach
  public void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(
                new ProcedureController(loadProcedureUseCase, loadAllProceduresUseCase))
            .setControllerAdvice(new InternalErrorExceptionHandler())
            .build();
  }

  @Test
  void itCallsProcedureServiceWithUuidFromQuery() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225";
    when(loadProcedureUseCase.loadProcedure(any())).thenReturn(Optional.empty());

    // When
    mockMvc.perform(get("/api/v1/norms/procedures/{eli}", eli));

    // Then
    verify(loadProcedureUseCase, times(1)).loadProcedure(argThat(query -> query.eli().equals(eli)));
  }

  @Test
  void itCallsProcedureServiceAndReturnsProcedure() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225";
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
        .andExpect(jsonPath("eli").value(equalTo("eli/bund/bgbl-1/1953/s225")))
        .andExpect(jsonPath("printAnnouncementGazette").value(equalTo("someGazette")))
        .andExpect(jsonPath("printAnnouncementYear").value(equalTo("2024")))
        .andExpect(jsonPath("printAnnouncementPage").value(equalTo("page123")));
  }

  @Test
  void itCallsProcedureServiceAndReturnsNotFound() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225";
    when(loadProcedureUseCase.loadProcedure(any())).thenReturn(Optional.empty());

    // When // Then
    mockMvc.perform(get("/api/v1/norms/procedures/{eli}", eli)).andExpect(status().isNotFound());
  }

  @Test
  void itCallsProcedureServiceAndReturnsInternalError() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225";
    when(loadProcedureUseCase.loadProcedure(any()))
        .thenThrow(new RuntimeException("simulating internal server error"));

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures/{eli}", eli))
        .andExpect(status().is5xxServerError());
  }

  @Test
  void itLoadsAllProceduresAndReturnsSuccessfully() throws Exception {
    // Given
    List<Procedure> allProcedures =
        List.of(
            Procedure.builder()
                .state(ProcedureState.OPEN)
                .eli("eli1")
                .printAnnouncementGazette("Gazette1")
                .printAnnouncementYear("2021")
                .printAnnouncementPage("1")
                .build(),
            Procedure.builder()
                .state(ProcedureState.OPEN)
                .eli("eli2")
                .printAnnouncementGazette("Gazette2")
                .printAnnouncementYear("2022")
                .printAnnouncementPage("2")
                .build());

    when(loadAllProceduresUseCase.loadAllProcedures()).thenReturn(allProcedures);

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[2]").doesNotExist())
        .andExpect(jsonPath("$[0].state", equalTo("OPEN")))
        .andExpect(jsonPath("$[0].eli", equalTo("eli1")))
        .andExpect(jsonPath("$[1].state", equalTo("OPEN")))
        .andExpect(jsonPath("$[1].eli", equalTo("eli2")));
  }
}
