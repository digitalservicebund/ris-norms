package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class ElementsControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

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
    void itReturnsEmptyListIfNoElementsAreFound() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));
      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements?type=preface"))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0]").doesNotExist());
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
          .andExpect(jsonPath("$[0].title").value("Dokumentenkopf"))
          .andExpect(jsonPath("$[0].eid").value("einleitung-1"))
          .andExpect(jsonPath("$[0].type").value("preface"))
          // preamble
          .andExpect(jsonPath("$[1].title").value("Eingangsformel"))
          .andExpect(jsonPath("$[1].eid").value("preambel-1"))
          .andExpect(jsonPath("$[1].type").value("preamble"))
          // articles
          .andExpect(jsonPath("$[2].title").value("Artikel 1 Änderung des Vereinsgesetzes"))
          .andExpect(jsonPath("$[2].eid").value("hauptteil-1_art-1"))
          .andExpect(jsonPath("$[2].type").value("article"))
          .andExpect(jsonPath("$[3].title").value("Artikel 3 Inkrafttreten"))
          .andExpect(jsonPath("$[3].eid").value("hauptteil-1_art-3"))
          .andExpect(jsonPath("$[3].type").value("article"))
          // conclusion
          .andExpect(jsonPath("$[4].title").value("Schlussteil"))
          .andExpect(jsonPath("$[4].eid").value("schluss-1"))
          .andExpect(jsonPath("$[4].type").value("conclusions"));
    }
  }

  // TODO Hannes:
  @Nested
  class getElementsWithAmendedByQueryParameter {
    @Test
    void itReturnsBadRequestIfAmendingLawIsNotFound() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      normRepository.save(NormMapper.mapToDto(norm));
      var url =
          "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements"
              + "?type=preface"
              + "&type=preamble"
              + "&type=article"
              + "&type=conclusions"
              + "&amendedBy=eli/bund/UNKNOWN_ELI/2017/s419/2017-03-15/1/deu/regelungstext-1";

      // when
      mockMvc
          .perform(get(url))
          // then
          .andExpect(status().isBadRequest());
    }

    @Disabled
    @Test
    void itReturnsAnEmptyListIfNoElementIsAffectedByTheGivenAmendingLaw() throws Exception {
      // given
      var targetNorm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      normRepository.save(NormMapper.mapToDto(targetNorm));
      var amendingNorm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      normRepository.save(NormMapper.mapToDto(amendingNorm));

      var url =
          "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements"
              + "?type=preface"
              + "&type=preamble"
              + "&type=article"
              + "&type=conclusions"
              + "&amendedBy="
              + amendingNorm.getEli().orElseThrow(); // amending norm eli

      // when
      mockMvc
          .perform(get(url))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0]").doesNotExist());
    }
  }

  // TODO Hannes
  // read passive mods
  // filter by amending law
  // take the destinations of the remaining passive mods and fiter our elements
}
