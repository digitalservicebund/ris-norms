package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
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

  final Article article1 = new Article("1", "eli1", "title1");
  final Article article2 = new Article("2", "eli2", "title2");

  final Article article3 = new Article("3", "eli3", "title3");

  final Article article4 = new Article("4", "eli4", "title4");

  @AfterEach
  void cleanUp() {
    amendingLawRepository.deleteAll();
  }

  @Test
  void itFindsAmendingLawOnDB() {
    // Given
    final String eli = "eli/bgbl-1/2024/123";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
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
  void itDoesNotFindAmendinglawOnDb() {
    // Given
    final String eli = "eli/bgbl-1/2024/123";

    // When
    final Optional<AmendingLaw> amendingLawLoaded =
        dbService.loadAmendingLawByEli(new LoadAmendingLawPort.Command(eli));

    // Then
    assertThat(amendingLawLoaded).isNotPresent();
  }

  @Test
  void itLoadsAllAmendinglawsFromDB() {
    // Given
    final String eli = "eli/bund/bgbl-1/1953/s225";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";

    final String eli2 = "eli/bund/bgbl-2/2010/s13";
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
            .articles(List.of(article3, article4))
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
        .containsExactlyInAnyOrder(amendingLaw1.getEli(), amendingLaw2.getEli());

    for (AmendingLaw amendingLaw : amendingLawsLoaded) {
      if (amendingLaw.getEli().equals(eli2)) {
        assertThat(amendingLaw.getArticles())
            .containsExactlyInAnyOrderElementsOf(List.of(article3, article4));
      } else {
        assertThat(amendingLaw.getArticles())
            .containsExactlyInAnyOrderElementsOf(List.of(article1, article2));
      }
    }
  }
}
