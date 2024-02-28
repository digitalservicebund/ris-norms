package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmendingLawIncludingArticlesArticlesResponseSchemaTest {

  @Test
  void canCreateSimpleAmendingLawIncludingArticlesResponseSchema() {

    final String eli = "someEli";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "Titel vom Gesetz";

    final String enumeration = "1";
    final String articleTitle = "Title vom Artikel";
    final String articleEli = "article Eli";

    final ArticleResponseSchema article =
        ArticleResponseSchema.builder()
            .title(articleTitle)
            .enumeration(enumeration)
            .eli(articleEli)
            .build();

    // When
    final AmendingLawIncludingArticlesResponseSchema amendingLaw =
        AmendingLawIncludingArticlesResponseSchema.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .title(title)
            .articles(List.of(article))
            .build();

    // Then
    assertThat(amendingLaw.getEli()).isEqualTo(eli);
    assertThat(amendingLaw.getPrintAnnouncementGazette()).isEqualTo(printAnnouncementGazette);
    assertThat(amendingLaw.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLaw.getPrintAnnouncementPage()).isEqualTo(printAnnouncementPage);
    assertThat(amendingLaw.getDigitalAnnouncementMedium()).isEqualTo(digitalAnnouncementMedium);
    assertThat(amendingLaw.getDigitalAnnouncementEdition()).isEqualTo(digitalAnnouncementEdition);
    assertThat(amendingLaw.getTitle()).isEqualTo(title);
    assertThat(amendingLaw.getArticles()).hasSize(1);
    assertThat(amendingLaw.getArticles().getFirst().getEnumeration()).isEqualTo(enumeration);
    assertThat(amendingLaw.getArticles().getFirst().getTitle()).isEqualTo(articleTitle);
    assertThat(amendingLaw.getArticles().getFirst().getEli()).isEqualTo(articleEli);
  }
}
