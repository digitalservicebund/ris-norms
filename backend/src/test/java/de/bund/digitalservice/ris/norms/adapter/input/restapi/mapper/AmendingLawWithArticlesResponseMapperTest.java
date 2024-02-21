package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawIncludingArticlesResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLawWithArticles;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmendingLawWithArticlesResponseMapperTest {

  @Test
  void canMapSimpleResponseSchema() {
    // Given
    final LocalDate now = LocalDate.now();
    final List<Article> article = new ArrayList<>();
    article.add(Article.builder().enumeration("1234").eli("ELI").title("title").build());

    final AmendingLawWithArticles amendingLawWithArticles = new AmendingLawWithArticles();
    amendingLawWithArticles.setEli("ELI");
    amendingLawWithArticles.setPrintAnnouncementGazette("GAZETTE");
    amendingLawWithArticles.setDigitalAnnouncementMedium("MEDIUM");
    amendingLawWithArticles.setPublicationDate(now);
    amendingLawWithArticles.setPrintAnnouncementPage("PAGE");
    amendingLawWithArticles.setDigitalAnnouncementEdition("EDITION");
    amendingLawWithArticles.setArticles(article);

    // When
    final AmendingLawIncludingArticlesResponseSchema resultAmendingLaw =
        AmendingLawResponseMapper.fromUseCaseData(amendingLawWithArticles);

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
