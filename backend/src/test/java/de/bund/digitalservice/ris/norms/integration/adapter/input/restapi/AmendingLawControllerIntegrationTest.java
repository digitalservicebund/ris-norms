package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;

class AmendingLawControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private AmendingLawRepository amendingLawRepository;

  TimeMachineService timeMachineService;

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
    final Document xml = timeMachineService.stringToXmlDocument("<test></test>");

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
    final Document xml1 = timeMachineService.stringToXmlDocument("<test>1</test>");

    final String eli2 = "eli2";
    final String printAnnouncementGazette2 = "someGazette2";
    final LocalDate publicationDate2 = LocalDate.now();
    final String printAnnouncementPage2 = "page1232";
    final String digitalAnnouncementMedium2 = "medium1232";
    final String digitalAnnouncementEdition2 = "edition1232";
    final String title2 = "title2";
    final Document xml2 = timeMachineService.stringToXmlDocument("<test>2</test>");

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
    final Document xml = timeMachineService.stringToXmlDocument("<test></test>");

    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("target law eli")
            .title("target law title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final TargetLaw targetLawZf0 =
        TargetLaw.builder()
            .eli("target law zf0 eli")
            .title("target law zf0 title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlawZf0")
            .build();

    final Article article =
        Article.builder()
            .eid("eid")
            .title("article title")
            .enumeration("1")
            .targetLaw(targetLaw)
            .targetLawZf0(targetLawZf0)
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
    final Document xml = timeMachineService.stringToXmlDocument("<test></test>");

    final TargetLaw targetLaw1 =
        TargetLaw.builder()
            .eli("target law eli")
            .title("target law title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final TargetLaw targetLaw1Zf0 =
        TargetLaw.builder()
            .eli("target law zf0 eli")
            .title("target law zf0 title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final Article article1 =
        Article.builder()
            .eid("eid")
            .title("article title")
            .enumeration("1")
            .targetLaw(targetLaw1)
            .targetLawZf0(targetLaw1Zf0)
            .build();

    final TargetLaw targetLaw2 =
        TargetLaw.builder()
            .eli("target law eli 2")
            .title("target law title 2")
            .xml(timeMachineService.stringToXmlDocument("<target>2</target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final TargetLaw targetLaw2Zf0 =
        TargetLaw.builder()
            .eli("target law zf0 eli")
            .title("target law zf0 title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final Article article2 =
        Article.builder()
            .eid("eid 2")
            .title("article title 2")
            .enumeration("2")
            .targetLaw(targetLaw2)
            .targetLawZf0(targetLaw2Zf0)
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
        .andExpect(jsonPath("affectedDocumentZf0Eli", equalTo(targetLaw2Zf0.getEli())))
        .andExpect(jsonPath("title", equalTo(article2.getTitle())));
  }

  @Test
  void itCallsAmendingLawServiceAndReturnsAmendingLawXml() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xmlString = "<test></test>";

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
            .xml(timeMachineService.stringToXmlDocument(xmlString))
            .build();
    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLaw));

    // When // Then
    mockMvc
        .perform(get("/api/v1/amending-laws/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().string(xmlString));
  }

  @Test
  void itCallsUpdateAmendingLawXmlAndReturnsUpdatedXml() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final Document xml = timeMachineService.stringToXmlDocument("<test></test>");

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
    final String updateXml = "<updated></updated>";
    mockMvc
        .perform(
            put("/api/v1/amending-laws/{eli}", encodedEli)
                .contentType(MediaType.APPLICATION_XML)
                .content(updateXml))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
        .andExpect(content().xml(updateXml));
  }

  @Test
  void itReleasesAmendingLawAndReturnsTimestampWithElis() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final Document xml = timeMachineService.stringToXmlDocument("<test></test>");

    final TargetLaw targetLaw1 =
        TargetLaw.builder()
            .eli("target law eli")
            .title("target law title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final TargetLaw targetLaw1Zf0 =
        TargetLaw.builder()
            .eli("target law zf0 eli")
            .title("target law zf0 title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final Article article1 =
        Article.builder()
            .eid("eid")
            .title("article title")
            .enumeration("1")
            .targetLaw(targetLaw1)
            .targetLawZf0(targetLaw1Zf0)
            .build();

    final TargetLaw targetLaw2 =
        TargetLaw.builder()
            .eli("target law eli 2")
            .title("target law title 2")
            .xml(timeMachineService.stringToXmlDocument("<target>2</target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final TargetLaw targetLaw2Zf0 =
        TargetLaw.builder()
            .eli("target law 2 zf0 eli")
            .title("target law zf0 title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final Article article2 =
        Article.builder()
            .eid("eid 2")
            .title("article title 2")
            .enumeration("2")
            .targetLaw(targetLaw2)
            .targetLawZf0(targetLaw2Zf0)
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
        .perform(put("/api/v1/amending-laws/{eli}/release", encodedEli))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath(
                "$.amendingLawEli",
                equalTo("eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1")))
        .andExpect(jsonPath("$.zf0Elis").exists())
        .andExpect(jsonPath("$.zf0Elis[0]").exists())
        .andExpect(jsonPath("$.zf0Elis[0]", equalTo("target law zf0 eli")))
        .andExpect(jsonPath("$.zf0Elis[1]").exists())
        .andExpect(jsonPath("$.zf0Elis[1]", equalTo("target law 2 zf0 eli")))
        .andExpect(jsonPath("$.zf0Elis[2]").doesNotExist());
  }

  @Test
  void itGetsReleasedAmendingLawAndReturnsTimestampWithElis() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final Document xml = timeMachineService.stringToXmlDocument("<test></test>");

    final TargetLaw targetLaw1 =
        TargetLaw.builder()
            .eli("target law eli")
            .title("target law title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final TargetLaw targetLaw1Zf0 =
        TargetLaw.builder()
            .eli("target law zf0 eli")
            .title("target law zf0 title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final Article article1 =
        Article.builder()
            .eid("eid")
            .title("article title")
            .enumeration("1")
            .targetLaw(targetLaw1)
            .targetLawZf0(targetLaw1Zf0)
            .build();

    final TargetLaw targetLaw2 =
        TargetLaw.builder()
            .eli("target law eli 2")
            .title("target law title 2")
            .xml(timeMachineService.stringToXmlDocument("<target>2</target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final TargetLaw targetLaw2Zf0 =
        TargetLaw.builder()
            .eli("target law 2 zf0 eli")
            .title("target law zf0 title")
            .xml(timeMachineService.stringToXmlDocument("<target></target>"))
            .fna("4711")
            .shortTitle("targetlaw")
            .build();

    final Article article2 =
        Article.builder()
            .eid("eid 2")
            .title("article title 2")
            .enumeration("2")
            .targetLaw(targetLaw2)
            .targetLawZf0(targetLaw2Zf0)
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
        .perform(get("/api/v1/amending-laws/{eli}/release", encodedEli))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath(
                "$.amendingLawEli",
                equalTo("eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1")))
        .andExpect(jsonPath("$.zf0Elis").exists())
        .andExpect(jsonPath("$.zf0Elis[0]").exists())
        .andExpect(jsonPath("$.zf0Elis[0]", equalTo("target law zf0 eli")))
        .andExpect(jsonPath("$.zf0Elis[1]").exists())
        .andExpect(jsonPath("$.zf0Elis[1]", equalTo("target law 2 zf0 eli")))
        .andExpect(jsonPath("$.zf0Elis[2]").doesNotExist());
  }
}
