package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ProcedureMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ProcedureRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

class ProcedureControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ProcedureRepository procedureRepository;

  @AfterEach
  void cleanUp() {
    procedureRepository.deleteAll();
  }

  @Test
  void itCallsProcedureServiceAndReturnsProcedure() throws Exception {
    // Given
    final Procedure procedure =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("eli/bund/bgbl-1/2024/123")
            .printAnnouncementGazette("bgbl-1")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("123")
            .build();
    procedureRepository.save(ProcedureMapper.mapToDto(procedure));

    final String encodedEli =
        UriComponentsBuilder.fromPath(procedure.getEli()).build().encode().toUriString();

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures/{eli}", encodedEli))
        .andExpect(status().isOk())
        .andExpect(jsonPath("state").value(equalTo("OPEN")))
        .andExpect(jsonPath("eli").value(equalTo("eli/bund/bgbl-1/2024/123")))
        .andExpect(jsonPath("printAnnouncementGazette").value(equalTo("bgbl-1")))
        .andExpect(jsonPath("printAnnouncementYear").value(equalTo("2024")))
        .andExpect(jsonPath("printAnnouncementPage").value(equalTo("123")));
  }

  @Test
  void itLoadsAllProceduresAndReturnsSuccessfully() throws Exception {
    // Given
    final Procedure procedure1 =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("eli/bund/bgbl-1/2024/123")
            .printAnnouncementGazette("bgbl-1")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("123")
            .build();
    final Procedure procedure2 =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("eli/bund/bgbl-2/2025/456")
            .printAnnouncementGazette("bgbl-2")
            .printAnnouncementYear("2025")
            .printAnnouncementPage("456")
            .build();
    procedureRepository.saveAll(
        List.of(ProcedureMapper.mapToDto(procedure1), ProcedureMapper.mapToDto(procedure2)));

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[2]").doesNotExist())
        .andExpect(jsonPath("$[0].eli", equalTo(procedure1.getEli())))
        .andExpect(jsonPath("$[1].eli", equalTo(procedure2.getEli())));
  }
}
