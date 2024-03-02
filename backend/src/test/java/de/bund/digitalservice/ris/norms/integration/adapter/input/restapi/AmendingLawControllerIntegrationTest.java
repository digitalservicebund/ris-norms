package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
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

  @AfterEach
  void cleanUp() {
    amendingLawRepository.deleteAll();
  }

  @Test
  void itCallsAmendingLawServiceAndReturnsAmendingLaw() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<test></test>";

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
            .xml(xml)
            .build();
    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLaw));

    final String encodedEli =
        UriComponentsBuilder.fromPath(amendingLaw.getEli()).build().encode().toUriString();

    // When // Then
    mockMvc
        .perform(get("/api/v1/amending-laws/{eli}", encodedEli))
        .andExpect(jsonPath("eli").value(equalTo(eli)))
        .andExpect(jsonPath("printAnnouncementGazette").value(equalTo(printAnnouncementGazette)))
        .andExpect(jsonPath("publicationDate").value(equalTo(publicationDate.toString())))
        .andExpect(jsonPath("printAnnouncementPage").value(equalTo(printAnnouncementPage)))
        .andExpect(jsonPath("digitalAnnouncementMedium").value(equalTo(digitalAnnouncementMedium)))
        .andExpect(
            jsonPath("digitalAnnouncementEdition").value(equalTo(digitalAnnouncementEdition)))
        .andExpect(jsonPath("title").value(equalTo(title)));
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
    final String title1 = "title1";
    final String xml1 = "<test>1</test>";

    final String eli2 = "eli2";
    final String printAnnouncementGazette2 = "someGazette2";
    final LocalDate publicationDate2 = LocalDate.now();
    final String printAnnouncementPage2 = "page1232";
    final String digitalAnnouncementMedium2 = "medium1232";
    final String digitalAnnouncementEdition2 = "edition1232";
    final String title2 = "title2";
    final String xml2 = "<test>2</test>";

    final AmendingLaw amendingLaw1 =
        AmendingLaw.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .title(title1)
            .xml(xml1)
            .build();

    final AmendingLaw amendingLaw2 =
        AmendingLaw.builder()
            .eli(eli2)
            .printAnnouncementGazette(printAnnouncementGazette2)
            .publicationDate(publicationDate2)
            .printAnnouncementPage(printAnnouncementPage2)
            .digitalAnnouncementMedium(digitalAnnouncementMedium2)
            .digitalAnnouncementEdition(digitalAnnouncementEdition2)
            .title(title2)
            .xml(xml2)
            .build();

    amendingLawRepository.saveAll(
        List.of(
            AmendingLawMapper.mapToDto(amendingLaw1), AmendingLawMapper.mapToDto(amendingLaw2)));

    // When // Then
    mockMvc
        .perform(get("/api/v1/amending-laws"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[2]").doesNotExist())
        .andExpect(jsonPath("$[0].eli", equalTo(amendingLaw1.getEli())))
        .andExpect(jsonPath("$[1].eli", equalTo(amendingLaw2.getEli())));
  }

  @Test
  void itCallsAmendingLawServiceAndReturnsArticles() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<test></test>";

    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("target law eli")
            .title("target law title")
            .xml("<target></target>")
            .build();
    final Article article =
        Article.builder()
            .eid("eid")
            .title("article title")
            .enumeration("1")
            .targetLaw(targetLaw)
            .build();

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
            .xml(xml)
            .articles(List.of(article))
            .build();
    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLaw));

    final String encodedEli =
        UriComponentsBuilder.fromPath(amendingLaw.getEli()).build().encode().toUriString();

    // When // Then
    mockMvc
        .perform(get("/api/v1/amending-laws/{eli}/articles", encodedEli))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[1]").doesNotExist())
        .andExpect(jsonPath("$[0].eid", equalTo(article.getEid())))
        .andExpect(jsonPath("$[0].enumeration", equalTo(article.getEnumeration())))
        .andExpect(jsonPath("$[0].affectedDocumentEli", equalTo(targetLaw.getEli())))
        .andExpect(jsonPath("$[0].title", equalTo(article.getTitle())));
  }

  @Test
  void itCallsAmendingLawServiceAndReturnsOneArticle() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<test></test>";

    final TargetLaw targetLaw1 =
        TargetLaw.builder()
            .eli("target law eli")
            .title("target law title")
            .xml("<target></target>")
            .build();
    final Article article1 =
        Article.builder()
            .eid("eid")
            .title("article title")
            .enumeration("1")
            .targetLaw(targetLaw1)
            .build();

    final TargetLaw targetLaw2 =
        TargetLaw.builder()
            .eli("target law eli 2")
            .title("target law title 2")
            .xml("<target>2</target>")
            .build();
    final Article article2 =
        Article.builder()
            .eid("eid 2")
            .title("article title 2")
            .enumeration("2")
            .targetLaw(targetLaw2)
            .build();

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
            .xml(xml)
            .articles(List.of(article1, article2))
            .build();
    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLaw));

    final String encodedEli =
        UriComponentsBuilder.fromPath(amendingLaw.getEli()).build().encode().toUriString();

    // When // Then
    mockMvc
        .perform(get("/api/v1/amending-laws/{eli}/articles/{eid}", encodedEli, article2.getEid()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("eid", equalTo(article2.getEid())))
        .andExpect(jsonPath("enumeration", equalTo(article2.getEnumeration())))
        .andExpect(jsonPath("affectedDocumentEli", equalTo(targetLaw2.getEli())))
        .andExpect(jsonPath("title", equalTo(article2.getTitle())));
  }
}
