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
          // TODO Hannes
          //          .andExpect(jsonPath("$[0].title").value("§ 20"))
          .andExpect(jsonPath("$[0].eid").value("hauptteil-1_para-20"))
          .andExpect(jsonPath("$[0].type").value("article"))
          .andExpect(jsonPath("$[1]").exists())
          .andExpect(jsonPath("$[1].title").exists())
          // TODO Hannes
          //          .andExpect(jsonPath("$[0].title").value("§ 20"))
          .andExpect(jsonPath("$[1].eid").value("hauptteil-1_para-1"))
          .andExpect(jsonPath("$[1].type").value("article"));
    }

    @Test
    void itReturnsEntriesWithPrefaceInformation() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements?type=preface"))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0]").exists())
          .andExpect(jsonPath("$[0].title").exists())
          // TODO Hannes
          //          .andExpect(jsonPath("$[0].title").value(">Entwurf eines Zweiten Gesetzes zur
          //                  Änderung des Vereinsgesetzes"))
          .andExpect(jsonPath("$[0].eid").value("einleitung-1"))
          .andExpect(jsonPath("$[0].type").value("preface"))
          .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void itReturnsEntriesWithPrefacePreambleArticleAndConclusionInformation() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      normRepository.save(NormMapper.mapToDto(norm));
      var url =
          "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements"
              + "?type=preface"
              + "&type=preamble"
              + "&type=article"
              + "&type=conclusions";

      // when
      mockMvc
          .perform(get(url))
          // then
          .andExpect(status().isOk())
          // preface
          .andExpect(jsonPath("$[0]").exists())
          .andExpect(jsonPath("$[0].title").exists())
          // TODO Hannes: expect actual title
          .andExpect(jsonPath("$[0].eid").value("einleitung-1"))
          .andExpect(jsonPath("$[0].type").value("preface"))
          // preamble
          .andExpect(jsonPath("$[1].title").exists())
          // TODO Hannes: expect actual title
          .andExpect(jsonPath("$[1].eid").value("preambel-1"))
          .andExpect(jsonPath("$[1].type").value("preamble"))
          // articles
          .andExpect(jsonPath("$[2].title").exists())
          // TODO Hannes: expect actual title
          .andExpect(jsonPath("$[2].eid").value("hauptteil-1_art-1"))
          .andExpect(jsonPath("$[2].type").value("article"))
          .andExpect(jsonPath("$[3]").exists())
          .andExpect(jsonPath("$[3].title").exists())
          // TODO Hannes: expect actual title
          .andExpect(jsonPath("$[3].eid").value("hauptteil-1_art-3"))
          .andExpect(jsonPath("$[3].type").value("article"))
          // conclusion
          .andExpect(jsonPath("$[4].title").exists())
          // TODO Hannes: expect actual title
          .andExpect(jsonPath("$[4].eid").value("schluss-1"))
          .andExpect(jsonPath("$[4].type").value("conclusions"));
    }
  }

  // TODO Hannes
  // @Nested
  // class getElementsWithAmendedByQueryParameter{}
}
