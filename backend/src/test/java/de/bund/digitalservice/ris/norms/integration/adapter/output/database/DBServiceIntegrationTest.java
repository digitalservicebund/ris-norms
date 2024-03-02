package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired private DBService dbService;

  @Autowired private AmendingLawRepository amendingLawRepository;

  final TargetLaw targetLaw =
      TargetLaw.builder()
          .eli("target law eli")
          .title("target law title")
          .xml("<test></test>")
          .build();

  final Article article1 = new Article("1", "eli1", "title1", targetLaw);
  final Article article2 = new Article("2", "eli2", "title2", targetLaw);
  final Article article3 = new Article("3", "eli3", "title3", targetLaw);
  final Article article4 = new Article("4", "eli4", "title4", targetLaw);

  @AfterEach
  void cleanUp() {
    amendingLawRepository.deleteAll();
  }

  @Test
  void itFindsAmendingLawOnDB() {
    // Given
    final String eli = "eli/bgbl-1/2024/123/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

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
            .xml(xml)
            .build();
    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLaw));

    // When
    final Optional<AmendingLaw> amendingLawOptional =
        dbService.loadAmendingLawByEli(new LoadAmendingLawPort.Command(eli));

    // Then
    assertThat(amendingLawOptional)
        .isPresent()
        .satisfies(
            amendingLawDb -> {
              assertThat(amendingLawDb.get()).isEqualTo(amendingLaw);
              assertThat(amendingLawDb.get().getArticles())
                  .containsExactlyInAnyOrderElementsOf(amendingLaw.getArticles());
            });
  }

  @Test
  void itDoesNotFindAmendingLawOnDb() {
    // Given
    final String eli = "eli/bgbl-1/2024/123/2017-03-15/1/deu/regelungstext-1";

    // When
    final Optional<AmendingLaw> amendingLawLoaded =
        dbService.loadAmendingLawByEli(new LoadAmendingLawPort.Command(eli));

    // Then
    assertThat(amendingLawLoaded).isNotPresent();
  }

  @Test
  void itLoadsAllAmendingLawsFromDB() {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.parse("1953-12-01");
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title1 = "title1";

    final String eli2 = "eli/bund/bgbl-2/2010/s13/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette2 = "someGazette2";
    final LocalDate publicationDate2 = LocalDate.parse("2024-11-23");
    final String printAnnouncementPage2 = "page1232";
    final String digitalAnnouncementMedium2 = "medium1232";
    final String digitalAnnouncementEdition2 = "edition1232";
    final String title2 = "title2";
    final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    final AmendingLaw amendingLaw1 =
        AmendingLaw.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .title(title1)
            .articles(List.of(article1, article2))
            .xml(xml)
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
            .articles(List.of(article3, article4))
            .xml(xml)
            .build();

    amendingLawRepository.saveAll(
        List.of(
            AmendingLawMapper.mapToDto(amendingLaw1), AmendingLawMapper.mapToDto(amendingLaw2)));

    // When
    List<AmendingLaw> amendingLawsLoaded = dbService.loadAllAmendingLaws();

    // Then
    assertThat(amendingLawsLoaded).hasSize(2);
    assertThat(amendingLawsLoaded)
        .extracting(AmendingLaw::getEli)
        .containsExactly(amendingLaw2.getEli(), amendingLaw1.getEli());
  }
}
