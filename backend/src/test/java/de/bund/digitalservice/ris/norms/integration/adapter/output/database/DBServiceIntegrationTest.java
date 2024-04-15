package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.TargetLawMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.XmlMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AmendingLawRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.TargetLawRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired private DBService dbService;

  @Autowired private AmendingLawRepository amendingLawRepository;

  @Autowired private TargetLawRepository targetLawRepository;

  @Autowired private AnnouncementRepository announcementRepository;

  @Autowired private NormRepository normRepository;

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
    announcementRepository.deleteAll();
    normRepository.deleteAll();
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
    final Instant timestampNow2 = Instant.now().plusSeconds(5);

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

    amendingLawRepository.save(AmendingLawMapper.mapToDto(amendingLawOld));

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
  void itFindsNormOnDB() throws XPathExpressionException, TransformerException {
    // Given
    final String xml =
        """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                     http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                       <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01" />
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                 </akn:act>
              </akn:akomaNtoso>
            """;

    // When
    var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
    normRepository.save(NormMapper.mapToDto(norm));

    // When
    final Optional<Norm> normOptional =
        dbService.loadNorm(
            new LoadNormPort.Command("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"));

    // Then
    assertThat(normOptional).isPresent().satisfies(normDb -> assertThat(normDb).contains(norm));
  }

  @Test
  void itFindsAnnouncementOnDB() throws XPathExpressionException, TransformerException {
    // Given
    final String xml =
        """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                     http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                       <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01" />
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                 </akn:act>
              </akn:akomaNtoso>
            """;

    // When
    var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
    var announcement =
        Announcement.builder().releasedByDocumentalistAt(Instant.now()).norm(norm).build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

    // When
    final Optional<Announcement> announcementOptional =
        dbService.loadAnnouncement(
            new LoadAnnouncementPort.Command(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"));

    // Then
    assertThat(announcementOptional)
        .isPresent()
        .satisfies(announcementDb -> assertThat(announcementDb).contains(announcement));
  }

  @Test
  void itLoadsAllAnnouncementsFromDB() throws XPathExpressionException, TransformerException {
    // Given
    final String xml1 =
        """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                     http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                       <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                            <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                            <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                            <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                          </akn:FRBRWork>
                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01" />
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                 </akn:act>
              </akn:akomaNtoso>
            """;

    final String xml2 =
        """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                     http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                       <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                            <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="413" />
                            <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                            <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="2023-12-29" name="verkuendungsfassung" />
                          </akn:FRBRWork>
                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                 </akn:act>
              </akn:akomaNtoso>
            """;

    // When
    var announcement1 =
        Announcement.builder()
            .releasedByDocumentalistAt(Instant.now())
            .norm(Norm.builder().document(XmlMapper.toDocument(xml1)).build())
            .build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement1));
    var announcement2 =
        Announcement.builder()
            .releasedByDocumentalistAt(Instant.now())
            .norm(Norm.builder().document(XmlMapper.toDocument(xml2)).build())
            .build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement2));

    // When
    final List<Announcement> announcements = dbService.loadAllAnnouncements();

    // Then
    assertThat(announcements).containsExactly(announcement2, announcement1);
  }
}
