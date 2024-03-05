package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchemaTemporary;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.time.LocalDate;
import java.util.List;
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

  @Test
  void canMapResponseSchemaWithArticles() {
    // Given
    final LocalDate now = LocalDate.now();

    final TargetLaw targetLaw1 =
        TargetLaw.builder()
            .eli("target-law-eli-1")
            .title("target law article 1")
            .xml("<target>1</target>")
            .build();
    final TargetLaw targetLaw2 =
        TargetLaw.builder()
            .eli("target-law-eli-2")
            .title("target law article 2")
            .xml("<target>2</target>")
            .build();

    final Article article1 =
        Article.builder()
            .enumeration("1")
            .eid("eid-article-1")
            .title("article title 1")
            .targetLaw(targetLaw1)
            .build();
    final Article article2 =
        Article.builder()
            .enumeration("2")
            .eid("eid-article-2")
            .title("article title 2")
            .targetLaw(targetLaw2)
            .build();

    final AmendingLaw amendingLaw =
        AmendingLaw.builder()
            .eli("ELI")
            .printAnnouncementGazette("GAZETTE")
            .digitalAnnouncementMedium("MEDIUM")
            .publicationDate(now)
            .printAnnouncementPage("PAGE")
            .digitalAnnouncementEdition("EDITION")
            .title("TITLE")
            .articles(List.of(article1, article2))
            .build();

    // When
    final AmendingLawResponseSchemaTemporary resultAmendingLaw =
        AmendingLawResponseMapper.fromUseCaseDataTemporary(amendingLaw);

    // Then
    assertThat(resultAmendingLaw.getEli()).isEqualTo("ELI");
    assertThat(resultAmendingLaw.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultAmendingLaw.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(resultAmendingLaw.getPublicationDate()).isEqualTo(now);
    assertThat(resultAmendingLaw.getPrintAnnouncementPage()).isEqualTo("PAGE");
    assertThat(resultAmendingLaw.getDigitalAnnouncementEdition()).isEqualTo("EDITION");
    assertThat(resultAmendingLaw.getTitle()).isEqualTo("TITLE");

    assertThat(resultAmendingLaw.getArticles().getFirst().getEnumeration()).isEqualTo("1");
    assertThat(resultAmendingLaw.getArticles().getFirst().getTitle()).isEqualTo("article title 1");
    assertThat(resultAmendingLaw.getArticles().getFirst().getEid()).isEqualTo("eid-article-1");
    assertThat(resultAmendingLaw.getArticles().getFirst().getAffectedDocumentEli())
        .isEqualTo("target-law-eli-1");

    assertThat(resultAmendingLaw.getArticles().getLast().getEnumeration()).isEqualTo("2");
    assertThat(resultAmendingLaw.getArticles().getLast().getTitle()).isEqualTo("article title 2");
    assertThat(resultAmendingLaw.getArticles().getLast().getEid()).isEqualTo("eid-article-2");
    assertThat(resultAmendingLaw.getArticles().getLast().getAffectedDocumentEli())
        .isEqualTo("target-law-eli-2");
  }
}
