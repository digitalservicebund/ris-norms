package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AmendingLawDTOTest {

  @Test
  void testPrintedLawDTO() {
    // Given
    final UUID id = UUID.randomUUID();
    final String eli = "someEli";
    final String printAnnouncementGazette = "someGazette";
    final LocalDate publicationDate = LocalDate.now();
    final String printAnnouncementPage = "page123";

    // When
    final AmendingLawDTO amendingLawPrintedDTO =
        AmendingLawDTO.builder()
            .id(id)
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .publicationDate(publicationDate)
            .printedAnnouncementPage(printAnnouncementPage)
            .build();

    // Then
    assertThat(amendingLawPrintedDTO.getId()).isEqualTo(id);
    assertThat(amendingLawPrintedDTO.getEli()).isEqualTo(eli);
    assertThat(amendingLawPrintedDTO.getPrintAnnouncementGazette())
        .isEqualTo(printAnnouncementGazette);
    assertThat(amendingLawPrintedDTO.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLawPrintedDTO.getPrintedAnnouncementPage()).isEqualTo(printAnnouncementPage);
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
    final AmendingLawDTO amendingLawPrintedDTO =
        AmendingLawDTO.builder()
            .id(id)
            .eli(eli)
            .digitalAnnouncementMedium(digitalAnnouncementMedium)
            .publicationDate(publicationDate)
            .digitalAnnouncementEdition(digitalAnnouncementEdition)
            .build();

    // Then
    assertThat(amendingLawPrintedDTO.getId()).isEqualTo(id);
    assertThat(amendingLawPrintedDTO.getEli()).isEqualTo(eli);
    assertThat(amendingLawPrintedDTO.getDigitalAnnouncementMedium())
        .isEqualTo(digitalAnnouncementMedium);
    assertThat(amendingLawPrintedDTO.getPublicationDate()).isEqualTo(publicationDate);
    assertThat(amendingLawPrintedDTO.getDigitalAnnouncementEdition())
        .isEqualTo(digitalAnnouncementEdition);
  }
}
