package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.TargetLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.TargetLawRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired private DBService dbService;

  @Autowired private AmendingLawRepository amendingLawRepository;

  @Autowired private TargetLawRepository targetLawRepository;

  final TargetLaw targetLaw =
      TargetLaw.builder()
          .eli("target law eli")
          .title("target law title")
          .xml("<test></test>")
          .fna("4711")
          .shortTitle("targetlaw")
          .build();

  final TargetLaw targetLawZf0 =
      TargetLaw.builder()
          .eli("target law eli zf0")
          .title("target law title zf0")
          .xml("<test>zf0</test>")
          .fna("4711")
          .shortTitle("targetlawzf0")
          .build();

  final Article article1 = new Article("1", "eli1", "title1", targetLaw, targetLawZf0);
  final Article article2 = new Article("2", "eli2", "title2", targetLaw, targetLawZf0);
  final Article article3 = new Article("3", "eli3", "title3", targetLaw, targetLawZf0);
  final Article article4 = new Article("4", "eli4", "title4", targetLaw, targetLawZf0);

  @AfterEach
  void cleanUp() {
    amendingLawRepository.deleteAll();
    targetLawRepository.deleteAll();
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
              assertThat(amendingLawDb).contains(amendingLaw);
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

  @Test
  void itFindsArticleOnDB() {
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
    final List<Article> articles =
        dbService.loadArticlesByAmendingLaw(new LoadArticlesPort.Command(eli));

    // Then
    assertThat(articles).hasSize(2);
    assertThat(articles)
        .extracting(Article::getTitle)
        .containsExactly(article1.getTitle(), article2.getTitle());
  }

  @Test
  void itDoesNotFindArticlesOfAmendingLaw() {
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
            .xml(xml)
            .build();
    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLaw));

    // When
    final List<Article> articles =
        dbService.loadArticlesByAmendingLaw(new LoadArticlesPort.Command(eli));

    // Then
    assertThat(articles).isEmpty();
  }

  @Test
  void itFindsOneArticleOnDB() {
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
    final Optional<Article> articleOptional =
        dbService.loadArticleByEliAndEid(new LoadArticlePort.Command(eli, article2.getEid()));

    // Then
    assertThat(articleOptional)
        .isPresent()
        .satisfies(article -> assertThat(article).contains(article2));
  }

  @Test
  void itFindsTargetLawOnDB() {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    final String title = "title";
    final String xml = "<target></target>";
    final String fna = "4711";
    final String shortTitle = "TitleGesetz";

    // When
    final TargetLaw targetLaw =
        TargetLaw.builder().eli(eli).title(title).xml(xml).fna(fna).shortTitle(shortTitle).build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    // When
    final Optional<TargetLaw> targetLawOptional =
        dbService.loadTargetLawByEli(new LoadTargetLawPort.Command(eli));

    // Then
    assertThat(targetLawOptional)
        .isPresent()
        .satisfies(targetlawDb -> assertThat(targetlawDb).contains(targetLaw));
  }

  @Test
  void itDoesNotFindTargetLawOnDb() {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

    // When
    final Optional<TargetLaw> targetLawOptional =
        dbService.loadTargetLawByEli(new LoadTargetLawPort.Command(eli));

    // Then
    assertThat(targetLawOptional).isNotPresent();
  }

  @Test
  void loadTargetLawXmlByEli() {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    final String title = "title";
    final String xml = "<target></target>";
    final String fna = "4711";
    final String shortTitle = "TitleGesetz";

    // When
    final TargetLaw targetLaw =
        TargetLaw.builder().eli(eli).title(title).xml(xml).fna(fna).shortTitle(shortTitle).build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    // When
    final Optional<String> targetLawXmlOptional =
        dbService.loadTargetLawXmlByEli(new LoadTargetLawXmlPort.Command(eli));

    // Then
    assertThat(targetLawXmlOptional)
        .isPresent()
        .satisfies(xmlDb -> assertThat(xmlDb).contains(xml));
  }

  @Test
  void loadAmendingLawXmlByEli() {
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
    final Optional<String> xmlOptional =
        dbService.loadAmendingLawXmlByEli(new LoadAmendingLawXmlPort.Command(eli));

    // Then
    assertThat(xmlOptional).isPresent().satisfies(xmlDb -> assertThat(xmlDb).contains(xml));
  }

  @Test
  void itUpdatesXmlOfAmendingLaw() {
    // Given
    final String eli = "eli/bgbl-1/2024/123/2017-03-15/1/deu/regelungstext-1";
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
            .articles(List.of(article1, article2))
            .xml(xml)
            .build();
    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLaw));

    // When
    final String newXml = "<new></new>";
    final Optional<String> updatedXmlOptional =
        dbService.updateAmendingLawXmlByEli(new UpdateAmendingLawXmlPort.Command(eli, newXml));

    // Then
    assertThat(updatedXmlOptional)
        .isPresent()
        .satisfies(updatedXml -> assertThat(updatedXml).contains(newXml));
  }

  @Test
  void itUpdatesXmlOfTargetLaw() {
    // Given
    final String eli = "eli/bgbl-1/2024/123/2017-03-15/1/deu/regelungstext-1";
    final String xml = "<test></test>";

    // When
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli(eli)
            .title("title")
            .shortTitle("shortTitle")
            .fna("fna")
            .xml(xml)
            .build();
    targetLawRepository.save(TargetLawMapper.mapToDto(targetLaw));

    // When
    final String newXml = "<new></new>";
    final Optional<String> updatedXmlOptional =
        dbService.updateTargetLawXmlByEli(new UpdateTargetLawXmlPort.Command(eli, newXml));

    // Then
    assertThat(updatedXmlOptional)
        .isPresent()
        .satisfies(updatedXml -> assertThat(updatedXml).contains(newXml));
  }

  @Test
  void itSetsReleasedAtOfAmendingLaw() {
    // Given
    final String eli = "eli/bgbl-1/2024/123/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<test></test>";

    final Instant timestampNow = Instant.now();
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
            .releasedAt(timestampNow)
            .build();

    // When

    final AmendingLaw amendingLawFromDatabase =
        dbService.saveAmendingLawByEli(new SaveAmendingLawPort.Command(amendingLaw));

    // Then
    assertThat(amendingLawFromDatabase.getReleasedAt()).isEqualTo(timestampNow);
  }

  @Test
  void itUpdatesAmendingLaw() {
    // Given
    // old amending law
    final String eli = "eli/bgbl-1/2024/123/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<test></test>";
    final Instant timestampNow = Instant.now();

    // new amending law
    final String printAnnouncementGazette2 = "someGazette2";
    final LocalDate publicationDate2 = LocalDate.now();
    final String printAnnouncementPage2 = "page1232";
    final String digitalAnnouncementMedium2 = "medium1232";
    final String digitalAnnouncementEdition2 = "edition1232";
    final String title2 = "title2";
    final String xml2 = "<test2></test2>";
    final Instant timestampNow2 = Instant.now();

    final AmendingLaw amendingLawOld =
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
            .releasedAt(timestampNow)
            .build();

    dbService.saveAmendingLawByEli(new SaveAmendingLawPort.Command(amendingLawOld));

    final AmendingLaw amendingLawNew =
        AmendingLaw.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette2)
            .publicationDate(publicationDate2)
            .printAnnouncementPage(printAnnouncementPage2)
            .digitalAnnouncementMedium(digitalAnnouncementMedium2)
            .digitalAnnouncementEdition(digitalAnnouncementEdition2)
            .title(title2)
            .xml(xml2)
            .releasedAt(timestampNow2)
            .build();

    // When

    final AmendingLaw amendingLawFromDatabase =
        dbService.updateAmendingLaw(new UpdateAmendingLawPort.Command(amendingLawNew)).get();

    // Then
    assertThat(amendingLawFromDatabase.getEli()).isEqualTo(eli);
    assertThat(amendingLawFromDatabase.getPrintAnnouncementGazette())
        .isEqualTo(printAnnouncementGazette2);
    assertThat(amendingLawFromDatabase.getPublicationDate()).isEqualTo(publicationDate2);
    assertThat(amendingLawFromDatabase.getPrintAnnouncementPage())
        .isEqualTo(printAnnouncementPage2);
    assertThat(amendingLawFromDatabase.getDigitalAnnouncementMedium())
        .isEqualTo(digitalAnnouncementMedium2);
    assertThat(amendingLawFromDatabase.getDigitalAnnouncementEdition())
        .isEqualTo(digitalAnnouncementEdition2);
    assertThat(amendingLawFromDatabase.getTitle()).isEqualTo(title2);
    assertThat(amendingLawFromDatabase.getXml()).isEqualTo(xml2);
    assertThat(amendingLawFromDatabase.getReleasedAt()).isEqualTo(timestampNow2);
  }

  @Test
  void itGetsTargetLawsOfAmendingLaw() {
    final TargetLaw targetLawZf0article1 =
        TargetLaw.builder()
            .eli("target law eli zf0 article1")
            .title("target law title zf0 article1")
            .xml("<test>zf0 article1</test>")
            .fna("4711")
            .shortTitle("targetlawzf0")
            .build();

    final TargetLaw targetLawZf0article2 =
        TargetLaw.builder()
            .eli("target law eli zf0 article2")
            .title("target law title zf0 article2")
            .xml("<test>zf0 article2</test>")
            .fna("4711")
            .shortTitle("targetlawzf0")
            .build();

    final Article article1 = new Article("1", "eli1", "title1", targetLaw, targetLawZf0article1);
    final Article article2 = new Article("2", "eli2", "title2", targetLaw, targetLawZf0article2);

    // Given
    final String eli = "eli/bgbl-1/2024/123/2017-03-15/1/deu/regelungstext-1";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "title";
    final String xml = "<test></test>";

    final Instant timestampNow = Instant.now();
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
            .releasedAt(timestampNow)
            .build();

    final AmendingLaw amendingLawFromDatabase =
        dbService.saveAmendingLawByEli(new SaveAmendingLawPort.Command(amendingLaw));

    // when
    List<TargetLaw> targetLaws =
        dbService.loadTargetsLawByAmendingLawEli(
            new LoadTargetLawsForAmendingLawPort.Command(amendingLawFromDatabase.getEli()));

    // Then
    assertThat(targetLaws).contains(targetLawZf0article1);
  }
}
