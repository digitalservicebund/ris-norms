package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
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

    // TODO Hannes: This can be removed as a duplicate of "returnMultipleArticles"
    @Test
    void itReturnsEntriesWithTitleAndEidAndType() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      normRepository.save(NormMapper.mapToDto(norm));
      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements?type=article"))
          // then
          .andExpect(status().isOk())
          // 200 OK
          .andExpect(jsonPath("$[0]").exists())
          // returning a non-empty list
          .andExpect(jsonPath("$[0].title").exists())
          .andExpect(jsonPath("$[0].eid").exists())
          .andExpect(jsonPath("$[0].type").exists());
    }

    @Test
    void itReturnsNotFoundIfNormIsNotFound() throws Exception {
      // given

      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/INVALID_ELI/2023/413/2023-12-29/1/deu/regelungstext-1/elements?type=article"))
          // then
          .andExpect(status().isNotFound());
    }

    @Test
    void itReturnsEntriesWithArticleInformation() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements?type=article"))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0]").exists())
          .andExpect(jsonPath("$[0].title").exists())
          //          .andExpect(jsonPath("$[0].title").value("§ 20"))
          .andExpect(jsonPath("$[0].eid").value("hauptteil-1_para-20"))
          .andExpect(jsonPath("$[0].type").value("article"))
          .andExpect(jsonPath("$[1]").exists())
          .andExpect(jsonPath("$[1].title").exists())
          //          .andExpect(jsonPath("$[0].title").value("§ 20"))
          .andExpect(jsonPath("$[1].eid").value("hauptteil-1_para-1"))
          .andExpect(jsonPath("$[1].type").value("article"));
    }

    //  check for intro/outro data
  }

  // @Nested
  // class getListWithAmendedBy{}
}
