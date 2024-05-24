package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadElementFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Using @Import to load
 * the {@link SecurityConfig} in order to avoid http 401 Unauthorised
 */
@WebMvcTest(ElementController.class)
@Import(SecurityConfig.class)
class ElementControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadNormUseCase loadNormUseCase;
  @MockBean private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  @MockBean private ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase;
  @MockBean private LoadElementFromNormUseCase loadElementFromNormUseCase;

  @Nested
  class GetSingleElement {

    @Nested
    class ErrorCases {

      @Test
      void returns404IfNormNotFoundByEli() throws Exception {
        // given
        when(loadNormUseCase.loadNorm(
                new LoadNormUseCase.Query(
                    "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")))
            .thenReturn(Optional.empty());
        // when
        mockMvc
            .perform(
                get("/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3")
                    .accept(MediaType.TEXT_HTML))
            .andExpect(status().isNotFound());
        // then
      }

      @Test
      void returns404IfElementNotFoundByEid() throws Exception {
        // given
        var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
        when(loadNormUseCase.loadNorm(
                new LoadNormUseCase.Query(
                    "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")))
            .thenReturn(Optional.of(norm));

        // when
        mockMvc
            .perform(
                get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID")
                    .accept(MediaType.TEXT_HTML))
            // then
            .andExpect(status().isNotFound());
      }

      @Test
      void returnsBadRequestIfAtIsoDateIsInvalid() throws Exception {
        // Given

        // When / Then
        mockMvc
            .perform(
                get("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements/hauptteil-1_para-20?atIsoDate=INVALID")
                    .accept(MediaType.TEXT_HTML))
            .andExpect(status().isBadRequest());
      }
    }

    @Test
    void returnsHtmlRendering() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      when(loadNormUseCase.loadNorm(
              new LoadNormUseCase.Query(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")))
          .thenReturn(Optional.of(norm));

      var renderedHtml = "renderedHtml";
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
          .thenReturn(renderedHtml);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1")
                  .accept(MediaType.TEXT_HTML))
          // then
          .andExpect(status().isOk())
          .andExpect(content().string(renderedHtml));
    }

    @Test
    void returnsJsonWithElementEidTitleAndType() throws Exception {
      // given
      var elementNode =
          """
              <akn:article eId="hauptteil-1_art-1"
                              GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                              period="#geltungszeitgr-1"
                              refersTo="hauptaenderung">
                              <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                                  GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                                  <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1"
                                      GUID="81c9c481-9427-4f03-9f51-099aa9b2201e"
                                      name="1" />Artikel 1 </akn:num>
                              <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                                  GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                              </akn:heading>
                          </akn:article>
              """;
      when(loadElementFromNormUseCase.loadElementFromNorm(
              new LoadElementFromNormUseCase.Query(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
                  "hauptteil-1_art-1")))
          .thenReturn(Optional.of(XmlMapper.toNode(elementNode)));

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
  }

  @Nested
  class GetListOfElements {

    @Nested
    class ErrorCases {

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
                    "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/elements?type=NOT_SUPPORTED"))
            // then
            .andExpect(status().is5xxServerError());
      }

      @Test
      void itReturnsNotFoundIfNormIsNotFound() throws Exception {
        // given
        when(loadNormUseCase.loadNorm(
                new LoadNormUseCase.Query(
                    "eli/bund/INVALID_ELI/2023/413/2023-12-29/1/deu/regelungstext-1")))
            .thenReturn(Optional.empty());
        // when
        mockMvc
            .perform(
                get(
                    "/api/v1/norms/eli/bund/INVALID_ELI/2023/413/2023-12-29/1/deu/regelungstext-1/elements?type=article"))
            // then
            .andExpect(status().isNotFound());
      }
    }

    @Test
    void itReturnsEmptyListIfNoMatchingElementsAreFound() throws Exception {
      // given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      when(loadNormUseCase.loadNorm(
              new LoadNormUseCase.Query(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")))
          .thenReturn(Optional.of(norm));

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
    void itReturnsPrefacePreambleArticleAndConclusionDataInElementsResponseEntrySchema()
        throws Exception {

      // given
      var norm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      when(loadNormUseCase.loadNorm(
              new LoadNormUseCase.Query(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")))
          .thenReturn(Optional.of(norm));

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

    @Nested
    class GivenAnAmendingLaw {

      @Test
      void itReturnsAnEmptyListIfNoElementIsAffectedByTheGivenAmendingLaw() throws Exception {
        // given
        var targetNorm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
        when(loadNormUseCase.loadNorm(
                new LoadNormUseCase.Query(
                    "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")))
            .thenReturn(Optional.of(targetNorm));

        var amendingNorm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
        when(loadNormUseCase.loadNorm(
                new LoadNormUseCase.Query(amendingNorm.getEli().orElseThrow())))
            .thenReturn(Optional.of(amendingNorm));

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

      @Test
      void itReturnsOnlyTheElementsMatchingTheGivenAmendingLaw() throws Exception {
        var targetNorm =
            NormFixtures.loadFromDisk("NormWithPassiveModificationsInDifferentArticles.xml");
        when(loadNormUseCase.loadNorm(
                new LoadNormUseCase.Query(
                    "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")))
            .thenReturn(Optional.of(targetNorm));
        var amendingNorm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
        when(loadNormUseCase.loadNorm(
                new LoadNormUseCase.Query(
                    "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1")))
            .thenReturn(Optional.of(amendingNorm));

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
}
