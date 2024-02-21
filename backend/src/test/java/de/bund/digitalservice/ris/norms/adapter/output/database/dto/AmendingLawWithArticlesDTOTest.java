package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AmendingLawWithArticlesDTOTest {

  @Test
  void testPrintLawDTO() {
    // Given
    final UUID id = UUID.randomUUID();
    final String eli = "someEli";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";

    // When
    final AmendingLawDTO amendingLawPrintDTO =
        AmendingLawDTO.builder()
            .id(id)
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printAnnouncementPage(printAnnouncementPage)
            .build();

    // Then
    assertThat(amendingLawPrintDTO.getId()).isEqualTo(id);
    assertThat(amendingLawPrintDTO.getEli()).isEqualTo(eli);
    assertThat(amendingLawPrintDTO.getPrintAnnouncementGazette())
        .isEqualTo(printAnnouncementGazette);
    assertThat(amendingLawPrintDTO.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLawPrintDTO.getPrintAnnouncementPage()).isEqualTo(printAnnouncementPage);
  }

  @Test
  void testDigitalLawDTO() {
    // Given
    final UUID id = UUID.randomUUID();
    final String eli = "someEli";
    final LocalDate publicationDate = LocalDate.now();
    final String digitalAnnouncementMedium = "medium123";
    final String digitalAnnouncementEdition = "edition123";

    // When
    final AmendingLawDTO amendingLawPrintDTO =
        AmendingLawDTO.builder()
            .id(id)
            .eli(eli)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .publicationDate(publicationDate)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .build();

    // Then
    assertThat(amendingLawPrintDTO.getId()).isEqualTo(id);
    assertThat(amendingLawPrintDTO.getEli()).isEqualTo(eli);
    assertThat(amendingLawPrintDTO.getDigitalAnnouncementMedium())
        .isEqualTo(digitalAnnouncementMedium);
    assertThat(amendingLawPrintDTO.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLawPrintDTO.getDigitalAnnouncementEdition())
        .isEqualTo(digitalAnnouncementEdition);
  }
}
