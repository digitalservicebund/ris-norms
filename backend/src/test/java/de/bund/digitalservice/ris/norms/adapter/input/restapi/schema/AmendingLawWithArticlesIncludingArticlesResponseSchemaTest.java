package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AmendingLawWithArticlesIncludingArticlesResponseSchemaTest {

  @Test
  void canCreateSimpleAmendingLawResponseSchema() {

    final String eli = "someEli";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";

    // When
    final AmendingLawIncludingArticlesResponseSchema amendingLaw =
        AmendingLawIncludingArticlesResponseSchema.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .build();

    // Then
    assertThat(amendingLaw.getEli()).isEqualTo(eli);
    assertThat(amendingLaw.getPrintAnnouncementGazette()).isEqualTo(printAnnouncementGazette);
    assertThat(amendingLaw.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLaw.getPrintAnnouncementPage()).isEqualTo(printAnnouncementPage);
    assertThat(amendingLaw.getDigitalAnnouncementMedium()).isEqualTo(digitalAnnouncementMedium);
    assertThat(amendingLaw.getDigitalAnnouncementEdition()).isEqualTo(digitalAnnouncementEdition);
  }
}
