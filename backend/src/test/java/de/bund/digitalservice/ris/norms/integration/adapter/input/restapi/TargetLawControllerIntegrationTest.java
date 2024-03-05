package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.TargetLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.TargetLawRepository;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

class TargetLawControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private TargetLawRepository targetLawRepository;

  @AfterEach
  void cleanUp() {
    targetLawRepository.deleteAll();
  }

  @Test
  void itCallsTargetLawServiceAndReturnsTargetLaw() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    final String title = "Title vom Gesetz";
    final String xml = "<target></target>";

    // When
    final TargetLaw targetLaw = TargetLaw.builder().eli(eli).title(title).xml(xml).build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    final String encodedEli =
        UriComponentsBuilder.fromPath(targetLaw.getEli()).build().encode().toUriString();

    // When // Then
    mockMvc
        .perform(get("/api/v1/target-laws/{eli}", encodedEli))
        .andExpect(jsonPath("eli").value(equalTo(eli)))
        .andExpect(jsonPath("title").value(equalTo(title)))
        .andExpect(jsonPath("xml").doesNotExist());
  }

  @Test
  void itCallsTargetLawServiceAndReturnsTargetLawXml() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    final String title = "Title vom Gesetz";
    final String xml = "<target></target>";

    // When
    final TargetLaw targetLaw = TargetLaw.builder().eli(eli).title(title).xml(xml).build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    // When // Then
    mockMvc
        .perform(get("/api/v1/target-laws/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().string(xml));
  }
}
