package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
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
    // given no norm
    // when
    mockMvc
        .perform(
            get("/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary")
                .accept(MediaType.APPLICATION_JSON_VALUE))
        // then
        .andExpect(status().isNotFound());
  }

  @Test
  void returnEmptyValuesIfNormHasNoProprietary() throws Exception {
    // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
    var norm = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
    normRepository.save(NormMapper.mapToDto(norm));

    // when
    mockMvc
        .perform(
            get("/api/v1/norms/" + eli + "/proprietary")
                .accept(MediaType.APPLICATION_JSON_VALUE))
        // then
        .andExpect(status().isOk())
            .andExpect(jsonPath("fna").exists())
            .andExpect(jsonPath("fna.value").doesNotExist());
  }
}
