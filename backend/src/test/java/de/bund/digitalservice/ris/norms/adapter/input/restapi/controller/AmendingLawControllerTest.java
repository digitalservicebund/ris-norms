package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.application.service.XmlDocumentService;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.time.LocalDate;
import java.util.List;
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
@WebMvcTest(AmendingLawController.class)
@Import(SecurityConfig.class)
class AmendingLawControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadAmendingLawUseCase loadAmendingLawUseCase;
  @MockBean private LoadAmendingLawXmlUseCase loadAmendingLawXmlUseCase;
  @MockBean private LoadAllAmendingLawsUseCase loadAllAmendingLawsUseCase;
  @MockBean private LoadArticlesUseCase loadArticlesUseCase;
  @MockBean private LoadArticleUseCase loadArticleUseCase;
  @MockBean private UpdateAmendingLawXmlUseCase updateAmendingLawXmlUseCase;
  @MockBean private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  @MockBean
  private ReleaseAmendingLawAndAllRelatedTargetLawsUseCase
      releaseAmendingLawAndAllRelatedTargetLawsUseCase;

  final XmlDocumentService xmlDocumentService = new XmlDocumentService();
  final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);

  @Nested
  class getAllAmendingLaws {

    @Test
    void itLoadsAllAmendingLawsAndReturnsSuccessfully() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String printAnnouncementGazette = "someGazette";
      final LocalDate publicationDate = LocalDate.now();
      final String printAnnouncementPage = "page123";
      final String digitalAnnouncementMedium = "medium123";
      final String digitalAnnouncementEdition = "edition123";

      final String eli2 = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String printAnnouncementGazette2 = "someGazette2";
      final LocalDate publicationDate2 = LocalDate.now();
      final String printAnnouncementPage2 = "page1232";
      final String digitalAnnouncementMedium2 = "medium1232";
      final String digitalAnnouncementEdition2 = "edition1232";

      List<AmendingLaw> allAmendingLawWithArticles =
          List.of(
              AmendingLaw.builder()
                  .eli(eli)
                  .printAnnouncementGazette(printAnnouncementGazette)
                  .publicationDate(publicationDate)
                  .printAnnouncementPage(printAnnouncementPage)
                  .digitalAnnouncementMedium(digitalAnnouncementMedium)
                  .digitalAnnouncementEdition(digitalAnnouncementEdition)
                  .build(),
              AmendingLaw.builder()
                  .eli(eli2)
                  .printAnnouncementGazette(printAnnouncementGazette2)
                  .publicationDate(publicationDate2)
                  .printAnnouncementPage(printAnnouncementPage2)
                  .digitalAnnouncementMedium(digitalAnnouncementMedium2)
                  .digitalAnnouncementEdition(digitalAnnouncementEdition2)
                  .build());

      when(loadAllAmendingLawsUseCase.loadAllAmendingLaws()).thenReturn(allAmendingLawWithArticles);

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[1]").exists())
          .andExpect(jsonPath("$[2]").doesNotExist())
          .andExpect(jsonPath("$[0].printAnnouncementGazette", equalTo(printAnnouncementGazette)))
          .andExpect(jsonPath("$[0].eli", equalTo(eli)))
          .andExpect(jsonPath("$[1].printAnnouncementGazette", equalTo(printAnnouncementGazette2)))
          .andExpect(jsonPath("$[1].eli", equalTo(eli2)));
    }
  }

  @Nested
  class getAmendingLaw {

    @Test
    void itCallsloadAmendingLawWithExpressionEliFromQuery() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      when(loadAmendingLawUseCase.loadAmendingLaw(any())).thenReturn(Optional.empty());

      // When
      mockMvc.perform(get("/api/v1/amending-laws/{eli}", eli));

      // Then
      verify(loadAmendingLawUseCase, times(1))
          .loadAmendingLaw(argThat(query -> query.eli().equals(eli)));
    }

    @Test
    void itCallsAmendingServiceAndReturnsAmendingLaw() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String printAnnouncementGazette = "someGazette";
      final LocalDate publicationDate = LocalDate.now();
      final String printAnnouncementPage = "page123";
      final String digitalAnnouncementMedium = "medium123";
      final String digitalAnnouncementEdition = "edition123";
      final String title = "Title vom Gesetz";

      // When
      final AmendingLaw amendingLaw =
          AmendingLaw.builder()
              .eli(eli)
              .printAnnouncementGazette(printAnnouncementGazette)
              .publicationDate(publicationDate)
              .printAnnouncementPage(printAnnouncementPage)
              .digitalAnnouncementMedium(digitalAnnouncementMedium)
              .digitalAnnouncementEdition(digitalAnnouncementEdition)
              .title(title)
              .build();

      when(loadAmendingLawUseCase.loadAmendingLaw(any())).thenReturn(Optional.of(amendingLaw));

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws/{eli}", eli))
          .andExpect(status().isOk())
          .andExpect(
              jsonPath("eli")
                  .value(equalTo("eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1")))
          .andExpect(jsonPath("printAnnouncementGazette").value(equalTo(printAnnouncementGazette)))
          .andExpect(
              jsonPath("digitalAnnouncementMedium").value(equalTo(digitalAnnouncementMedium)))
          .andExpect(
              jsonPath("digitalAnnouncementEdition").value(equalTo(digitalAnnouncementEdition)))
          .andExpect(jsonPath("title").value(equalTo(title)));
    }

    @Test
    void itCallsAmendingLawServiceAndReturnsNotFound() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      when(loadAmendingLawUseCase.loadAmendingLaw(any())).thenReturn(Optional.empty());

      // When // Then
      mockMvc.perform(get("/api/v1/amending-laws/{eli}", eli)).andExpect(status().isNotFound());
    }

    @Test
    void itCallsloadAmendingLawAndReturnsInternalError() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      when(loadAmendingLawUseCase.loadAmendingLaw(any()))
          .thenThrow(new RuntimeException("simulating internal server error"));

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws/{eli}", eli))
          .andExpect(status().is5xxServerError());
    }
  }

  @Nested
  class getAmendingLawXml {

    @Test
    void itCallsLoadAmendingLawXmlAndReturnsAmendingLawXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<target></target>";

      // When
      when(loadAmendingLawXmlUseCase.loadAmendingLawXml(any())).thenReturn(Optional.of(xml));

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws/{eli}", eli).accept(MediaType.APPLICATION_XML))
          .andExpect(status().isOk())
          .andExpect(content().string(xml));
    }
  }

  @Nested
  class getAmendingLawRenderedHtml {

    @Test
    void itCallsAmendingLawServiceAndReturnsLawHtml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(loadAmendingLawXmlUseCase.loadAmendingLawXml(any())).thenReturn(Optional.of(xml));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws/{eli}", eli).accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string(html));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
          .transformLegalDocMlToHtml(argThat(query -> query.xml().equals(xml)));
    }
  }

  @Nested
  class updateAmendingLaw {

    @Test
    void itCallsUpdateAmendingLawXmlWithExpressionEliAndXmlFromQuery() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String xml = "<toUpdate></toUpdate>";
      when(updateAmendingLawXmlUseCase.updateAmendingLawXml(any())).thenReturn(Optional.empty());

      // When
      mockMvc.perform(
          put("/api/v1/amending-laws/{eli}", eli)
              .contentType(MediaType.APPLICATION_XML)
              .content(xml));

      // Then
      verify(updateAmendingLawXmlUseCase, times(1))
          .updateAmendingLawXml(
              argThat(query -> query.eli().equals(eli) && query.xml().equals(xml)));
    }

    @Test
    void itCallsUpdateAmendingLawXmlAndReturnsNotFound() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String xml = "<toUpdate></toUpdate>";
      when(updateAmendingLawXmlUseCase.updateAmendingLawXml(any())).thenReturn(Optional.empty());

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/amending-laws/{eli}", eli)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().isNotFound());
    }

    @Test
    void itCallsUpdateAmendingLawXmlAndReturnsInternalError() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String xml = "<toUpdate></toUpdate>";

      when(updateAmendingLawXmlUseCase.updateAmendingLawXml(any()))
          .thenThrow(new RuntimeException("simulating internal server error"));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/amending-laws/{eli}", eli)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().is5xxServerError());
    }

    @Test
    void itCallsUpdateAmendingLawXmlWithoutXmlAndReturnsInternalError() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";

      when(updateAmendingLawXmlUseCase.updateAmendingLawXml(any())).thenReturn(Optional.empty());

      // When // Then
      mockMvc
          .perform(put("/api/v1/amending-laws/{eli}", eli).contentType(MediaType.APPLICATION_XML))
          .andExpect(status().isBadRequest());
    }

    @Test
    void itLoadsUpdateAmendingLawXmlAndReturnsTheOneSuccessfully() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String xml = "<toUpdate>foo</toUpdate>";
      when(updateAmendingLawXmlUseCase.updateAmendingLawXml(any())).thenReturn(Optional.of(xml));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/amending-laws/{eli}", eli)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
          .andExpect(content().xml(xml));
    }
  }

  @Nested
  class getArticlesOfAmendingLaw {

    @Test
    void itLoadsAllArticlesAndReturnsSuccessfully() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final List<Article> articles =
          List.of(
              Article.builder()
                  .eid("eid 1")
                  .title("article title 1")
                  .enumeration("1")
                  .targetLaw(
                      TargetLaw.builder()
                          .eli("target law eli 1")
                          .title("title1")
                          .xml(timeMachineService.stringToXmlDocument("xml1"))
                          .build())
                  .targetLawZf0(
                      TargetLaw.builder()
                          .eli("target law zf0 eli 1")
                          .title("title zf0 1")
                          .xml(timeMachineService.stringToXmlDocument("xml zf0 1"))
                          .build())
                  .build(),
              Article.builder()
                  .eid("eid 2")
                  .title("article title 2")
                  .enumeration("2")
                  .targetLaw(
                      TargetLaw.builder()
                          .eli("target law eli 2")
                          .title("title2")
                          .xml(timeMachineService.stringToXmlDocument("xml2"))
                          .build())
                  .targetLawZf0(
                      TargetLaw.builder()
                          .eli("target law zf0 eli 2")
                          .title("title zf0 2")
                          .xml(timeMachineService.stringToXmlDocument("xml zf0 2"))
                          .build())
                  .build());

      when(loadArticlesUseCase.loadArticlesOfAmendingLaw(any())).thenReturn(articles);

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws/{eli}/articles", eli))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[1]").exists())
          .andExpect(jsonPath("$[2]").doesNotExist())
          .andExpect(jsonPath("$[0].eid", equalTo("eid 1")))
          .andExpect(jsonPath("$[0].title", equalTo("article title 1")))
          .andExpect(jsonPath("$[0].enumeration", equalTo("1")))
          .andExpect(jsonPath("$[0].affectedDocumentEli", equalTo("target law eli 1")))
          .andExpect(jsonPath("$[0].affectedDocumentZf0Eli", equalTo("target law zf0 eli 1")))
          .andExpect(jsonPath("$[1].eid", equalTo("eid 2")))
          .andExpect(jsonPath("$[1].title", equalTo("article title 2")))
          .andExpect(jsonPath("$[1].enumeration", equalTo("2")))
          .andExpect(jsonPath("$[1].affectedDocumentEli", equalTo("target law eli 2")))
          .andExpect(jsonPath("$[1].affectedDocumentZf0Eli", equalTo("target law zf0 eli 2")));
    }

    @Test
    void itCallsloadArticlesOfAmendingLawWithExpressionEliFromQuery() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      when(loadArticlesUseCase.loadArticlesOfAmendingLaw(any())).thenReturn(List.of());

      // When
      mockMvc.perform(get("/api/v1/amending-laws/{eli}/articles", eli));

      // Then
      verify(loadArticlesUseCase, times(1))
          .loadArticlesOfAmendingLaw(argThat(query -> query.eli().equals(eli)));
    }

    @Test
    void itCallsloadArticlesOfAmendingLawAndReturnsInternalError() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      when(loadArticlesUseCase.loadArticlesOfAmendingLaw(any()))
          .thenThrow(new RuntimeException("simulating internal server error"));

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws/{eli}/articles", eli))
          .andExpect(status().is5xxServerError());
    }
  }

  @Nested
  class getArticleOfAmendingLawByEid {

    @Test
    void itLoadsOneArticleAndReturnsTheOneSuccessfully() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";

      final Article article2 =
          Article.builder()
              .eid("eid 2")
              .title("article title 2")
              .enumeration("2")
              .targetLaw(
                  TargetLaw.builder()
                    .eli("target law eli 2")
                    .title("title2")
                    .xml(timeMachineService.stringToXmlDocument("xml2"))
                    .build())
              .targetLawZf0(
                  TargetLaw.builder()
                      .eli("target law zf0 eli")
                      .title("title zf0")
                      .xml(timeMachineService.stringToXmlDocument("xml zf0"))
                      .build())
              .build();

      when(loadArticleUseCase.loadArticle(any())).thenReturn(Optional.of(article2));

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws/{eli}/articles/{eid}", eli, article2.getEid()))
          .andExpect(status().isOk())
          .andExpect(jsonPath("enumeration", equalTo("2")))
          .andExpect(jsonPath("eid", equalTo("eid 2")))
          .andExpect(jsonPath("title", equalTo("article title 2")))
          .andExpect(jsonPath("affectedDocumentEli", equalTo("target law eli 2")))
          .andExpect(jsonPath("affectedDocumentZf0Eli", equalTo("target law zf0 eli")));
    }
  }

  @Nested
  class releaseAmendingLaw {

    @Test
    void itCallsReleaseAmendingLawAndReturnsNotFound() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";

      when(releaseAmendingLawAndAllRelatedTargetLawsUseCase.releaseAmendingLaw(any()))
          .thenReturn(Optional.empty());

      // When // Then
      mockMvc
          .perform(put("/api/v1/amending-laws/{eli}/release", eli))
          .andExpect(status().isNotFound());
    }

    @Test
    void itCallsReleaseAmendingLawAndReturnsElisOfTargetLaws() throws Exception {
      // Given
      final TargetLaw targetLaw =
          TargetLaw.builder()
              .eli("target law eli")
              .title("target law title")
              .xml(timeMachineService.stringToXmlDocument("<test></test>"))
              .fna("4711")
              .shortTitle("targetlaw")
              .build();

      final TargetLaw targetLawZf0 =
          TargetLaw.builder()
              .eli("target law eli zf0")
              .title("target law title zf0")
              .xml(timeMachineService.stringToXmlDocument("<test>zf0</test>"))
              .fna("4711")
              .shortTitle("targetlawzf0")
              .build();

      final Article article1 = new Article("1", "eli1", "title1", targetLaw, targetLawZf0);

      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String printAnnouncementGazette = "someGazette";
      final LocalDate publicationDate = LocalDate.now();
      final String printAnnouncementPage = "page123";
      final String digitalAnnouncementMedium = "medium123";
      final String digitalAnnouncementEdition = "edition123";
      final String title = "Title vom Gesetz";

      final AmendingLaw amendingLaw =
          AmendingLaw.builder()
              .eli(eli)
              .printAnnouncementGazette(printAnnouncementGazette)
              .publicationDate(publicationDate)
              .printAnnouncementPage(printAnnouncementPage)
              .digitalAnnouncementMedium(digitalAnnouncementMedium)
              .digitalAnnouncementEdition(digitalAnnouncementEdition)
              .title(title)
              .articles(List.of(article1))
              .build();

      when(releaseAmendingLawAndAllRelatedTargetLawsUseCase.releaseAmendingLaw(any()))
          .thenReturn(Optional.of(amendingLaw));

      // When // Then
      mockMvc
          .perform(put("/api/v1/amending-laws/{eli}/release", eli))
          .andExpect(status().isOk())
          .andExpect(
              jsonPath(
                  "$.amendingLawEli",
                  equalTo("eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1")))
          .andExpect(jsonPath("$.zf0Elis").exists())
          .andExpect(jsonPath("$.zf0Elis[0]").exists())
          .andExpect(jsonPath("$.zf0Elis[0]", equalTo("target law eli zf0")))
          .andExpect(jsonPath("$.zf0Elis[1]").doesNotExist());
    }
  }

  @Nested
  class getReleasedAmendingLaw {

    @Test
    void itCallsGetReleasedAmendingLawAndReturnsElisOfTargetLaws() throws Exception {
      // Given
      final TargetLaw targetLaw =
          TargetLaw.builder()
              .eli("target law eli")
              .title("target law title")
              .xml(timeMachineService.stringToXmlDocument("<test></test>"))
              .fna("4711")
              .shortTitle("targetlaw")
              .build();

      final TargetLaw targetLawZf0 =
          TargetLaw.builder()
              .eli("target law eli zf0")
              .title("target law title zf0")
              .xml(timeMachineService.stringToXmlDocument("<test>zf0</test>"))
              .fna("4711")
              .shortTitle("targetlawzf0")
              .build();

      final Article article1 = new Article("1", "eli1", "title1", targetLaw, targetLawZf0);

      final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
      final String printAnnouncementGazette = "someGazette";
      final LocalDate publicationDate = LocalDate.now();
      final String printAnnouncementPage = "page123";
      final String digitalAnnouncementMedium = "medium123";
      final String digitalAnnouncementEdition = "edition123";
      final String title = "Title vom Gesetz";

      final AmendingLaw amendingLaw =
          AmendingLaw.builder()
              .eli(eli)
              .printAnnouncementGazette(printAnnouncementGazette)
              .publicationDate(publicationDate)
              .printAnnouncementPage(printAnnouncementPage)
              .digitalAnnouncementMedium(digitalAnnouncementMedium)
              .digitalAnnouncementEdition(digitalAnnouncementEdition)
              .title(title)
              .articles(List.of(article1))
              .build();

      when(loadAmendingLawUseCase.loadAmendingLaw(any())).thenReturn(Optional.of(amendingLaw));

      // When // Then
      mockMvc
          .perform(get("/api/v1/amending-laws/{eli}/release", eli))
          .andExpect(status().isOk())
          .andExpect(
              jsonPath(
                  "$.amendingLawEli",
                  equalTo("eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1")))
          .andExpect(jsonPath("$.zf0Elis").exists())
          .andExpect(jsonPath("$.zf0Elis[0]").exists())
          .andExpect(jsonPath("$.zf0Elis[0]", equalTo("target law eli zf0")))
          .andExpect(jsonPath("$.zf0Elis[1]").doesNotExist());
    }
  }
}
