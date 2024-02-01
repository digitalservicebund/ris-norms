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
            .eli("eli/bgbl-1/2024/123")
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
        .andExpect(jsonPath("eli").value(equalTo("eli/bgbl-1/2024/123")))
        .andExpect(jsonPath("printAnnouncementGazette").value(equalTo("bgbl-1")))
        .andExpect(jsonPath("printAnnouncementYear").value(equalTo("2024")))
        .andExpect(jsonPath("printAnnouncementPage").value(equalTo("123")));
  }
}
