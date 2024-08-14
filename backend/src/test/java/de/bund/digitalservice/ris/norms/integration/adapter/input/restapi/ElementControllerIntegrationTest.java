package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class ElementControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Nested
  class GetElement {
    @Test
    void returns404IfNormNotFoundByEli() throws Exception {
      // given no norm
      // when
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3")
                  .accept(MediaType.TEXT_HTML))
          // then
          .andExpect(status().isNotFound());
    }

    @Test
    void returns404IfElementNotFoundByEid() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID")
                  .accept(MediaType.TEXT_HTML))
          // then
          .andExpect(status().isNotFound());
    }

    @Test
    void returnsElementRenderedAsHtml() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1")
                  .accept(MediaType.TEXT_HTML))
          // then
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("Änderung des Vereinsgesetzes")));
    }

    @Test
    void returnElementEidTitleAndType() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1")
                  .accept(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("eid").value("hauptteil-1_art-1"))
          .andExpect(jsonPath("type").value("article"))
          .andExpect(jsonPath("title").value("Artikel 1 Änderung des Vereinsgesetzes"));
    }

    @Test
    void returnsElementAtGivenIsoDateRenderedAsHtml() throws Exception {

      // Given
      var amendingNorm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      var targetNorm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");

      normRepository.save(NormMapper.mapToDto(amendingNorm));
      normRepository.save(NormMapper.mapToDto(targetNorm));

      // When / Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements/hauptteil-1_para-20?atIsoDate=2017-03-01T00:00:00.000Z")
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3")))
          .andExpect(content().string(not(containsString("§ 9 Abs. 1 Satz 2, Abs. 2"))));
    }

    @Test
    void returnsBadRequestIfAtIsoDateIsInvalid() throws Exception {
      // Given

      // When / Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements/hauptteil-1_para-20?atIsoDate=INVALID")
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().is5xxServerError());
    }
  }

  @Nested
  class getListOfElements {

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
    void itReturnsBadRequestIfTheTypeIsNotSupported() throws Exception {
      // given

      // when
      mockMvc
          .perform(
              get(
                  "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements?type=foo"))
          // then
          .andExpect(status().isBadRequest());
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
    void itReturnsEmptyListIfNoMatchingElementsAreFound() throws Exception {
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

    @Test
    void itReturnsEntriesWithBookPartChapterTitleSubtitleSectionAndSubsectionInformation()
        throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithGliederung.xml");
      normRepository.save(NormMapper.mapToDto(norm));
      var url =
          "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements"
              + "?type=book"
              + "&type=part"
              + "&type=chapter"
              + "&type=title"
              + "&type=subtitle"
              + "&type=section"
              + "&type=subsection";

      // when
      mockMvc
          .perform(get(url))
          // then
          .andExpect(status().isOk())
          // book
          .andExpect(jsonPath("$[0]").exists())
          .andExpect(jsonPath("$[0].title").value("Buch 1 Überschrift Buch"))
          .andExpect(jsonPath("$[0].eid").value("hauptteil-1_buch-1"))
          .andExpect(jsonPath("$[0].type").value("book"))
          // part
          .andExpect(jsonPath("$[1]").exists())
          .andExpect(jsonPath("$[1].title").value("Teil 1 Überschrift Teil"))
          .andExpect(jsonPath("$[1].eid").value("hauptteil-1_buch-1_teil-1"))
          .andExpect(jsonPath("$[1].type").value("part"))
          // chapter
          .andExpect(jsonPath("$[2]").exists())
          .andExpect(jsonPath("$[2].title").value("Kapitel 1 Überschrift Kapitel"))
          .andExpect(jsonPath("$[2].eid").value("hauptteil-1_buch-1_teil-1_kapitel-1"))
          .andExpect(jsonPath("$[2].type").value("chapter"))
          // section
          .andExpect(jsonPath("$[3]").exists())
          .andExpect(jsonPath("$[3].title").value("Abschnitt 1 Überschrift Abschnitt"))
          .andExpect(jsonPath("$[3].eid").value("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1"))
          .andExpect(jsonPath("$[3].type").value("section"))
          // subsection
          .andExpect(jsonPath("$[4]").exists())
          .andExpect(jsonPath("$[4].title").value("Unterabschnitt 1 Überschrift Unterabschnitt"))
          .andExpect(
              jsonPath("$[4].eid")
                  .value("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1"))
          .andExpect(jsonPath("$[4].type").value("subsection"))
          // title
          .andExpect(jsonPath("$[5]").exists())
          .andExpect(jsonPath("$[5].title").value("Titel 1 Überschrift Titel"))
          .andExpect(
              jsonPath("$[5].eid")
                  .value("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1"))
          .andExpect(jsonPath("$[5].type").value("title"))
          // subtitle
          .andExpect(jsonPath("$[6]").exists())
          .andExpect(jsonPath("$[6].title").value("Untertitel 1 Überschrift Untertitel"))
          .andExpect(
              jsonPath("$[6].eid")
                  .value(
                      "hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1"))
          .andExpect(jsonPath("$[6].type").value("subtitle"));
    }
  }

  @Nested
  class getElementsWithAmendedByQueryParameter {

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
              + amendingNorm.getEli(); // amending norm eli

      // when
      mockMvc
          .perform(get(url))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void itReturnsOnlyTheElementsMatchingTheGivenAmendingLaw() throws Exception {
      var targetNorm =
          NormFixtures.loadFromDisk("NormWithPassiveModificationsInDifferentArticles.xml");
      normRepository.save(NormMapper.mapToDto(targetNorm));

      var url =
          "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements"
              + "?type=preface"
              + "&type=preamble"
              + "&type=article"
              + "&type=conclusions"
              + "&amendedBy=eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"; // second

      // when
      mockMvc
          .perform(get(url))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].eid").exists())
          .andExpect(jsonPath("$[0].title").exists())
          .andExpect(jsonPath("$[0].type").exists())
          .andExpect(jsonPath("$[1]").doesNotExist());
    }
  }
}
