package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class ListControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Nested
  class getList {
    @Test
    void itReturns200Ok() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      // when
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/list"))
          // then
          .andExpect(status().isOk());
    }

    @Test
    void itReturnsJson() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      // when
      mockMvc
              .perform(
                      get("/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/list"))
              // then
              .andExpect(status().isOk()) // TODO Hannnes: Once this test works, we can remove the one on returning 200 OK
              .andExpect(jsonPath("$[0]").exists());
    }
  }

  // @Nested
  // class getListWithAmendedBy{}
}
