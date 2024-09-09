package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.util.List;
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

  @MockBean private LoadElementFromNormUseCase loadElementFromNormUseCase;
  @MockBean private LoadElementHtmlFromNormUseCase loadElementHtmlFromNormUseCase;
  @MockBean private LoadElementsByTypeFromNormUseCase loadElementsByTypeFromNormUseCase;
  @MockBean private LoadElementHtmlAtDateFromNormUseCase loadElementHtmlAtDateFromNormUseCase;

  @Nested
  class getElementHtmlPreview {

    @Test
    void itThrowsXmlProcessingException() throws Exception {
      when(loadElementHtmlFromNormUseCase.loadElementHtmlFromNorm(any()))
          .thenThrow(new XmlProcessingException("Error message", null));
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1")
                  .accept(MediaType.TEXT_HTML))
          // then
          .andExpect(status().isInternalServerError())
          .andExpect(jsonPath("type").value("/errors/xml-processing-error"))
          .andExpect(jsonPath("title").value("XML processing error"))
          .andExpect(jsonPath("status").value(500))
          .andExpect(jsonPath("detail").value("Error message"))
          .andExpect(
              jsonPath("instance")
                  .value(
                      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1"));
    }

    @Test
    void itThrowsXmlProcessingExceptionWithPassedDate() throws Exception {
      when(loadElementHtmlAtDateFromNormUseCase.loadElementHtmlAtDateFromNorm(any()))
          .thenThrow(new XmlProcessingException("Error message", null));
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1?atIsoDate=2024-04-03T00:00:00.000Z")
                  .accept(MediaType.TEXT_HTML))
          // then
          .andExpect(status().isInternalServerError())
          .andExpect(jsonPath("type").value("/errors/xml-processing-error"))
          .andExpect(jsonPath("title").value("XML processing error"))
          .andExpect(jsonPath("status").value(500))
          .andExpect(jsonPath("detail").value("Error message"))
          .andExpect(
              jsonPath("instance")
                  .value(
                      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1"));
    }

    @Test
    void returnsHtmlRendering() throws Exception {
      // given
      var elementHtml = "<div></div>";
      when(loadElementHtmlFromNormUseCase.loadElementHtmlFromNorm(
              new LoadElementHtmlFromNormUseCase.Query(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
                  "hauptteil-1_art-1")))
          .thenReturn(elementHtml);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1")
                  .accept(MediaType.TEXT_HTML))
          // then
          .andExpect(status().isOk())
          .andExpect(content().string(elementHtml));
    }
  }

  @Nested
  class getElementInfo {
    @Test
    void returnsJsonWithElementEidTitleAndType() throws Exception {
      // given
      var elementNode =
          """
                  <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="hauptteil-1_art-1"
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
          .thenReturn(XmlMapper.toNode(elementNode));

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
  class getELementList {
    @Test
    void itReturnsListOfElements() throws Exception {

      // given
      when(loadElementsByTypeFromNormUseCase.loadElementsByTypeFromNorm(
              new LoadElementsByTypeFromNormUseCase.Query(
                  "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
                  eq(List.of("preface", "preamble", "article", "conclusions")))))
          .thenReturn(List.of());

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
          .andExpect(status().isOk());
    }

    @Test
    void itReturnsOnlyTheElementsMatchingTheGivenAmendingLaw() throws Exception {
      when(loadElementsByTypeFromNormUseCase.loadElementsByTypeFromNorm(
              new LoadElementsByTypeFromNormUseCase.Query(
                  "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
                  eq(List.of("preface", "preamble", "article", "conclusions")),
                  "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1")))
          .thenReturn(List.of());

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
          .andExpect(status().isOk());
    }
  }
}
