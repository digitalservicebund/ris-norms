package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AmendingLawTest {

  @Test
  void canCreateSimpleAmendingLaw() {

    final String eli = "someEli";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";
    final String title = "Titel vom Gesetz";

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
            .build();

    // Then
    assertThat(amendingLaw.getEli()).isEqualTo(eli);
    assertThat(amendingLaw.getPrintAnnouncementGazette()).isEqualTo(printAnnouncementGazette);
    assertThat(amendingLaw.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLaw.getPrintAnnouncementPage()).isEqualTo(printAnnouncementPage);
    assertThat(amendingLaw.getDigitalAnnouncementMedium()).isEqualTo(digitalAnnouncementMedium);
    assertThat(amendingLaw.getDigitalAnnouncementEdition()).isEqualTo(digitalAnnouncementEdition);
    assertThat(amendingLaw.getTitle()).isEqualTo(title);
  }
}
