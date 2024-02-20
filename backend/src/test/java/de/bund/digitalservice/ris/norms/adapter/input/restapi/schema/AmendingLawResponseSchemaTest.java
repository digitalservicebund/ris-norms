package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AmendingLawResponseSchemaTest {

  @Test
  void canCreateSimpleAmendingLawResponseSchema() {

    final String eli = "someEli";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";

    // When
    final AmendingLawResponseSchema amendingLawPrinted =
        AmendingLawResponseSchema.builder()
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printedAnnouncementPage(printAnnouncementPage)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .build();

    // Then
    assertThat(amendingLawPrinted.getEli()).isEqualTo(eli);
    assertThat(amendingLawPrinted.getPrintAnnouncementGazette())
        .isEqualTo(printAnnouncementGazette);
    assertThat(amendingLawPrinted.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLawPrinted.getPrintedAnnouncementPage()).isEqualTo(printAnnouncementPage);
    assertThat(amendingLawPrinted.getDigitalAnnouncementMedium())
        .isEqualTo(digitalAnnouncementMedium);
    assertThat(amendingLawPrinted.getDigitalAnnouncementEdition())
        .isEqualTo(digitalAnnouncementEdition);
  }
}
