package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmendingLawResponseMapperTest {

  @Test
  void canMapSimpleResponseSchema() {
    // Given
    final LocalDate now = LocalDate.now();
    final List<Article> article = new ArrayList<>();
    article.add(Article.builder().enumeration("1234").eli("ELI").title("title").build());

    final AmendingLaw amendingLaw = new AmendingLaw();
    amendingLaw.setEli("ELI");
    amendingLaw.setPrintAnnouncementGazette("GAZETTE");
    amendingLaw.setDigitalAnnouncementMedium("MEDIUM");
    amendingLaw.setPublicationDate(now);
    amendingLaw.setPrintAnnouncementPage("PAGE");
    amendingLaw.setDigitalAnnouncementEdition("EDITION");
    amendingLaw.setArticles(article);

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
    assertThat(resultAmendingLaw.getArticles().getFirst())
        .isEqualTo(new ArticleResponseSchema("1234", "ELI", "title"));
  }
}
