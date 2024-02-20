package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

class AmendingLawControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private AmendingLawRepository amendingLawRepository;

  final Article article1 = new Article("1", "eli1");
  final Article article2 = new Article("2", "eli2");

  @AfterEach
  void cleanUp() {
    amendingLawRepository.deleteAll();
  }

  @Test
  void itCallsAmendinglawServiceAndReturnsProcedure() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.parse("2024-02-20");
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";

    // When
    final AmendingLaw amendingLaw =
        AmendingLaw.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printedAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .articles(List.of(article1, article2))
            .build();
    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLaw));

    final String encodedEli =
        UriComponentsBuilder.fromPath(amendingLaw.getEli()).build().encode().toUriString();

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures/{eli}", encodedEli))
        .andExpect(jsonPath("eli").value(equalTo(eli)))
        .andExpect(jsonPath("printAnnouncementGazette").value(equalTo(printAnnouncementGazette)))
        .andExpect(jsonPath("publicationDate").value(equalTo("2024-02-20")))
        .andExpect(jsonPath("printedAnnouncementPage").value(equalTo(printAnnouncementPage)))
        .andExpect(jsonPath("digitalAnnouncementMedium").value(equalTo(digitalAnnouncementMedium)))
        .andExpect(
            jsonPath("digitalAnnouncementEdition").value(equalTo(digitalAnnouncementEdition)));
  }

  @Test
  void itLoadsAllAmendingLawsAndReturnsSuccessfully() throws Exception {
    // Given
    final String eli = "eli1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";

    final String eli2 = "eli2";
    final String printAnnouncementGazette2 = "someGazette2";
    final LocalDate publicationDate2 = LocalDate.now();
    final String printAnnouncementPage2 = "page1232";
    final String digitalAnnouncementMedium2 = "medium1232";
    final String digitalAnnouncementEdition2 = "edition1232";

    final AmendingLaw amendingLaw1 =
        AmendingLaw.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printedAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .articles(List.of(article1, article2))
            .build();

    final AmendingLaw amendingLaw2 =
        AmendingLaw.builder()
            .eli(eli2)
            .printAnnouncementGazette(printAnnouncementGazette2)
            .publicationDate(publicationDate2)
            .printedAnnouncementPage(printAnnouncementPage2)
            .digitalAnnouncementMedium(digitalAnnouncementMedium2)
            .digitalAnnouncementEdition(digitalAnnouncementEdition2)
            .articles(List.of(article1, article2))
            .build();

    amendingLawRepository.saveAll(
        List.of(
            AmendingLawMapper.mapToDto(amendingLaw1), AmendingLawMapper.mapToDto(amendingLaw2)));

    // When // Then
    mockMvc
        .perform(get("/api/v1/norms/procedures"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[2]").doesNotExist())
        .andExpect(jsonPath("$[0].eli", equalTo(amendingLaw1.getEli())))
        .andExpect(jsonPath("$[1].eli", equalTo(amendingLaw2.getEli())));
  }
}
