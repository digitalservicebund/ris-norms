package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class ProprietaryControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Test
  void return404IfNormNotFound() throws Exception {
    // given

    // when
    mockMvc
        .perform(
            get("/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary")
                .accept(MediaType.APPLICATION_JSON_VALUE))
        // then
        .andExpect(status().isNotFound());
  }
}
