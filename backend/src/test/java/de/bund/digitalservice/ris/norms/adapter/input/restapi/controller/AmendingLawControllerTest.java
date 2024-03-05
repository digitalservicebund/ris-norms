package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
  void itCallsloadArticlesOfAmendingLawWithExpressionEliFromQuery() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    when(loadArticlesUseCase.loadArticlesOfAmendingLaw(any())).thenReturn(Collections.emptyList());

    // When
    mockMvc.perform(get("/api/v1/amending-laws/{eli}/articles", eli));

    // Then
    verify(loadArticlesUseCase, times(1))
        .loadArticlesOfAmendingLaw(argThat(query -> query.eli().equals(eli)));
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
        .andExpect(jsonPath("digitalAnnouncementMedium").value(equalTo(digitalAnnouncementMedium)))
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
    mockMvc.perform(get("/api/v1/amending-laws/{eli}", eli)).andExpect(status().is5xxServerError());
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
                    TargetLaw.builder().eli("target law eli 1").title("title1").xml("xml1").build())
                .build(),
            Article.builder()
                .eid("eid 2")
                .title("article title 2")
                .enumeration("2")
                .targetLaw(
                    TargetLaw.builder().eli("target law eli 2").title("title2").xml("xml2").build())
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
        .andExpect(jsonPath("$[1].eid", equalTo("eid 2")))
        .andExpect(jsonPath("$[1].title", equalTo("article title 2")))
        .andExpect(jsonPath("$[1].enumeration", equalTo("2")))
        .andExpect(jsonPath("$[1].affectedDocumentEli", equalTo("target law eli 2")));
  }

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
                TargetLaw.builder().eli("target law eli 2").title("title2").xml("xml2").build())
            .build();

    when(loadArticleUseCase.loadArticle(any())).thenReturn(Optional.of(article2));

    // When // Then
    mockMvc
        .perform(get("/api/v1/amending-laws/{eli}/articles/{eid}", eli, article2.getEid()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("enumeration", equalTo("2")))
        .andExpect(jsonPath("eid", equalTo("eid 2")))
        .andExpect(jsonPath("title", equalTo("article title 2")))
        .andExpect(jsonPath("affectedDocumentEli", equalTo("target law eli 2")));
  }

  @Test
  void itCallsTargetServiceAndReturnsTargetLawXml() throws Exception {
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
