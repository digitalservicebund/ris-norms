package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AmendingLawWithArticlesTest {

  @Test
  void canCreateSimpleAmendingLaw() {

    final String eli = "someEli";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";

    // When
    final AmendingLawWithArticles amendingLawWithArticles =
        AmendingLawWithArticles.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .build();

    // Then
    assertThat(amendingLawWithArticles.getEli()).isEqualTo(eli);
    assertThat(amendingLawWithArticles.getPrintAnnouncementGazette())
        .isEqualTo(printAnnouncementGazette);
    assertThat(amendingLawWithArticles.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLawWithArticles.getPrintAnnouncementPage()).isEqualTo(printAnnouncementPage);
    assertThat(amendingLawWithArticles.getDigitalAnnouncementMedium())
        .isEqualTo(digitalAnnouncementMedium);
    assertThat(amendingLawWithArticles.getDigitalAnnouncementEdition())
        .isEqualTo(digitalAnnouncementEdition);
  }
}
