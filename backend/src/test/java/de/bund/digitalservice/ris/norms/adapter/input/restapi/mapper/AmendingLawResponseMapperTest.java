package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AmendingLawResponseMapperTest {

  @Test
  void canMapSimpleResponseSchema() {
    // Given
    final LocalDate now = LocalDate.now();

    final AmendingLaw amendingLaw =
        AmendingLaw.builder()
            .eli("ELI")
            .printAnnouncementGazette("GAZETTE")
            .digitalAnnouncementMedium("MEDIUM")
            .publicationDate(now)
            .printAnnouncementPage("PAGE")
            .digitalAnnouncementEdition("EDITION")
            .title("TITLE")
            .build();

    // When
    final AmendingLawResponseSchema resultAmendingLaw =
        AmendingLawResponseMapper.fromUseCaseData(amendingLaw);

    // Then
    assertThat(resultAmendingLaw.getEli()).isEqualTo("ELI");
    assertThat(resultAmendingLaw.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultAmendingLaw.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(resultAmendingLaw.getPublicationDate()).isEqualTo(now);
    assertThat(resultAmendingLaw.getPrintAnnouncementPage()).isEqualTo("PAGE");
    assertThat(resultAmendingLaw.getDigitalAnnouncementEdition()).isEqualTo("EDITION");
    assertThat(resultAmendingLaw.getTitle()).isEqualTo("TITLE");
  }
}
