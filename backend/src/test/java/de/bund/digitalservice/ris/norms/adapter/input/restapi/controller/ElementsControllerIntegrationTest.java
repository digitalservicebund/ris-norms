package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class ElementsControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private NormRepository normRepository;

  @Nested
  class getElements {

    @Test
    void itReturnsServerErrorIfTypeParameterIsMissing() throws Exception {
      // given

      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements"))
          // then
          .andExpect(status().is5xxServerError());
    }

    @Test
    void itReturnsServerErrorIfTheTypeIsNotSupported() throws Exception {
      // given

      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements?type=foo"))
          // then
          .andExpect(status().is5xxServerError());
    }

    @Test
    void itReturnsEntriesWithTitleAndEidAndType() throws Exception {
      // given

      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements?type=article"))
          // then
          .andExpect(
              status()
                  .isOk())
          // 200 OK
          .andExpect(
              jsonPath("$[0]")
                  .exists())
          // returning a non-empty list
          .andExpect(jsonPath("$[0].title").exists())
          .andExpect(jsonPath("$[0].eid").exists())
          .andExpect(jsonPath("$[0].type").exists());
    }

    //    @Test
    //    void itReturnsEntriesWithAnArticlesInformation() throws Exception {
    //      // given
    //      // TODO: later we may need a norm of our own as it needs to contain intros and outro
    //      var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
    //      normRepository.save(NormMapper.mapToDto(norm));
    //
    //      // when
    //      mockMvc
    //          .perform(
    //
    // get("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements?type=article"))
    //          // then
    //          .andExpect(
    //              status()
    //                  .isOk()) // TODO Hannnes: Once this test works, we can remove the one on
    // returning
    //          // 200 OK
    //          .andExpect(
    //              jsonPath("$[0]").exists()) // TODO Hannnes: Once this test works, we can remove
    // the one on returning a non-empty list
    //          .andExpect(jsonPath("$[0].title").value("§ 20"))
    //          .andExpect(jsonPath("$[0].eid").value("hauptteil-1_para-20"))
    //          .andExpect(jsonPath("$[0].type").value("article"));
    //    }
    //  also check for intro/outro data
  }

  // @Nested
  // class getListWithAmendedBy{}
}
