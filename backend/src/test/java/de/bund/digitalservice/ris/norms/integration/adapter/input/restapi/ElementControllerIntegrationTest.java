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

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Nested
  class getElementHtmlPreview {

    @Test
    void returnsNotFoundIfNormNotFoundByEli() throws Exception {
      // Given
      // Nothing

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3"
          )
            .accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3"
            )
        )
        .andExpect(
          jsonPath("eli")
            .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void returnsNotFoundIfElementNotFoundByEid() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID"
          )
            .accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/element-not-found"))
        .andExpect(jsonPath("title").value("Element not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Element with eid NONEXISTENT_EID does not exist in norm with eli eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("eid").value("NONEXISTENT_EID"));
    }

    @Test
    void returnsElementRenderedAsHtml() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1"
          )
            .accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Änderung des Vereinsgesetzes")));
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
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements/hauptteil-1_para-20?atIsoDate=2017-03-01T00:00:00.000Z"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3")))
        .andExpect(content().string(not(containsString("§ 9 Abs. 1 Satz 2, Abs. 2"))));
    }

    @Test
    void returnsBadRequestIfAtIsoDateIsInvalid() throws Exception {
      // Given
      // Nothing

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements/hauptteil-1_para-20?atIsoDate=INVALID"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/parameter-binding-error"))
        .andExpect(jsonPath("title").value("Parameter Binding Error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("Invalid request parameter: INVALID"))
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements/hauptteil-1_para-20"
            )
        );
    }
  }

  @Nested
  class getElementInfo {

    @Test
    void returnsNotFoundIfNormNotFoundByEli() throws Exception {
      // Given
      // Nothing

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3"
            )
        )
        .andExpect(
          jsonPath("eli")
            .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void returnsNotFoundIfElementNotFoundByEid() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/element-not-found"))
        .andExpect(jsonPath("title").value("Element not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Element with eid NONEXISTENT_EID does not exist in norm with eli eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("eid").value("NONEXISTENT_EID"));
    }

    @Test
    void returnElementEidTitleAndType() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("eid").value("hauptteil-1_art-1"))
        .andExpect(jsonPath("type").value("article"))
        .andExpect(jsonPath("title").value("Artikel 1 Änderung des Vereinsgesetzes"));
    }
  }

  @Nested
  class getListOfElements {

    @Test
    void itReturnsServerErrorIfTypeParameterIsMissing() throws Exception {
      // Given
      // Nothing

      // When
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements")
        )
        // Then
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("type").value("/errors/internal-server-error"))
        .andExpect(jsonPath("status").value(500))
        .andExpect(
          jsonPath("detail")
            .value(
              "Required request parameter 'type' for method parameter type String[] is not present"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements"
            )
        );
    }

    @Test
    void itReturnsBadRequestIfTheTypeIsNotSupported() throws Exception {
      // Given
      // Nothing

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements?type=foo"
          )
        )
        // Then
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/unsupported-element-type"))
        .andExpect(jsonPath("title").value("Unsupported element type"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("foo is not supported"))
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements"
            )
        );
    }

    @Test
    void itReturnsNotFoundIfNormIsNotFound() throws Exception {
      // Given
      // Nothing

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/INVALID_ELI/2023/413/2023-12-29/1/deu/regelungstext-1/elements?type=article"
          )
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/INVALID_ELI/2023/413/2023-12-29/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/INVALID_ELI/2023/413/2023-12-29/1/deu/regelungstext-1/elements"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/INVALID_ELI/2023/413/2023-12-29/1/deu/regelungstext-1")
        );
    }

    @Test
    void itReturnsEmptyListIfNoMatchingElementsAreFound() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements?type=preface"
          )
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void itReturnsEntriesWithPrefacePreambleArticleAndConclusionInformation() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      normRepository.save(NormMapper.mapToDto(norm));
      var url =
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements" +
        "?type=preface" +
        "&type=preamble" +
        "&type=article" +
        "&type=conclusions";

      // When
      mockMvc
        .perform(get(url))
        // Then
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
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithGliederung.xml");
      normRepository.save(NormMapper.mapToDto(norm));
      var url =
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements" +
        "?type=book" +
        "&type=part" +
        "&type=chapter" +
        "&type=title" +
        "&type=subtitle" +
        "&type=section" +
        "&type=subsection";

      // When
      mockMvc
        .perform(get(url))
        // Then
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
          jsonPath("$[4].eid").value("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1")
        )
        .andExpect(jsonPath("$[4].type").value("subsection"))
        // title
        .andExpect(jsonPath("$[5]").exists())
        .andExpect(jsonPath("$[5].title").value("Titel 1 Überschrift Titel"))
        .andExpect(
          jsonPath("$[5].eid")
            .value("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1")
        )
        .andExpect(jsonPath("$[5].type").value("title"))
        // subtitle
        .andExpect(jsonPath("$[6]").exists())
        .andExpect(jsonPath("$[6].title").value("Untertitel 1 Überschrift Untertitel"))
        .andExpect(
          jsonPath("$[6].eid")
            .value("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1")
        )
        .andExpect(jsonPath("$[6].type").value("subtitle"));
    }

    @Test
    void itReturnsAnEmptyListIfNoElementIsAffectedByTheGivenAmendingLaw() throws Exception {
      // Given
      var targetNorm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      normRepository.save(NormMapper.mapToDto(targetNorm));
      var amendingNorm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      normRepository.save(NormMapper.mapToDto(amendingNorm));

      var url =
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements" +
        "?type=preface" +
        "&type=preamble" +
        "&type=article" +
        "&type=conclusions" +
        "&amendedBy=" +
        amendingNorm.getEli(); // amending norm eli

      // When
      mockMvc
        .perform(get(url))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void itReturnsOnlyTheElementsMatchingTheGivenAmendingLaw() throws Exception {
      var targetNorm = NormFixtures.loadFromDisk(
        "NormWithPassiveModificationsInDifferentArticles.xml"
      );
      normRepository.save(NormMapper.mapToDto(targetNorm));

      var url =
        "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements" +
        "?type=preface" +
        "&type=preamble" +
        "&type=article" +
        "&type=conclusions" +
        "&amendedBy=eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"; // second

      // When
      mockMvc
        .perform(get(url))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].eid").exists())
        .andExpect(jsonPath("$[0].title").exists())
        .andExpect(jsonPath("$[0].type").exists())
        .andExpect(jsonPath("$[1]").doesNotExist());
    }
  }
}
