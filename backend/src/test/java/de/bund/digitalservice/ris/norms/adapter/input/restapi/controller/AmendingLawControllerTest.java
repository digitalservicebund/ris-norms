package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions.InternalErrorExceptionHandler;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAmendingLawsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAmendingLawUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Therefore, manually
 * setting up the {@code mockMvc} including the ControllerAdvice
 */
@ExtendWith(SpringExtension.class)
class AmendingLawControllerTest {

  private MockMvc mockMvc;

  @MockBean private LoadAmendingLawUseCase loadAmendingLawUseCase;
  @MockBean private LoadAllAmendingLawsUseCase loadAllAmendingLawsUseCase;

  @BeforeEach
  public void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(
                new AmendingLawController(loadAmendingLawUseCase, loadAllAmendingLawsUseCase))
            .setControllerAdvice(new InternalErrorExceptionHandler())
            .build();
  }

  @Test
  void itCallsAmendingLawServiceWithExpressionEliFromQuery() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    when(loadAmendingLawUseCase.loadAmendingLaw(any())).thenReturn(Optional.empty());

    // When
    mockMvc.perform(get("/api/v1/amendinglaw/{eli}", eli));

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

    final Article article1 = new Article("1", "eli1", "title1");
    final Article article2 = new Article("2", "eli2", "title2");

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
            .articles(List.of(article1, article2))
            .build();

    when(loadAmendingLawUseCase.loadAmendingLaw(any())).thenReturn(Optional.of(amendingLaw));

    // When // Then
    mockMvc
        .perform(get("/api/v1/amendinglaw/{eli}", eli))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("eli")
                .value(equalTo("eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1")))
        .andExpect(jsonPath("printAnnouncementGazette").value(equalTo(printAnnouncementGazette)))
        .andExpect(jsonPath("digitalAnnouncementMedium").value(equalTo(digitalAnnouncementMedium)))
        .andExpect(
            jsonPath("digitalAnnouncementEdition").value(equalTo(digitalAnnouncementEdition)))
        .andExpect(jsonPath("title").value(equalTo(title)))
        .andExpect(jsonPath("articles").isArray())
        .andExpect(jsonPath("$..articles[0].eli").value("eli1"));
  }

  @Test
  void itCallsAmendingLawServiceAndReturnsNotFound() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    when(loadAmendingLawUseCase.loadAmendingLaw(any())).thenReturn(Optional.empty());

    // When // Then
    mockMvc.perform(get("/api/v1/amendinglaw/{eli}", eli)).andExpect(status().isNotFound());
  }

  @Test
  void itCallsAmendingLawServiceAndReturnsInternalError() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    when(loadAmendingLawUseCase.loadAmendingLaw(any()))
        .thenThrow(new RuntimeException("simulating internal server error"));

    // When // Then
    mockMvc.perform(get("/api/v1/amendinglaw/{eli}", eli)).andExpect(status().is5xxServerError());
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
        .perform(get("/api/v1/amendinglaw"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[2]").doesNotExist())
        .andExpect(jsonPath("$[0].printAnnouncementGazette", equalTo(printAnnouncementGazette)))
        .andExpect(jsonPath("$[0].eli", equalTo(eli)))
        .andExpect(jsonPath("$[1].printAnnouncementGazette", equalTo(printAnnouncementGazette2)))
        .andExpect(jsonPath("$[1].eli", equalTo(eli2)));
  }
}
