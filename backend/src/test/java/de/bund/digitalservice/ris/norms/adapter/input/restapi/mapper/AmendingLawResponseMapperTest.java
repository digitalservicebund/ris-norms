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
    article.add(Article.builder().enumeration("1234").eli("ELI").build());

    final AmendingLaw amendingLaw = new AmendingLaw();
    amendingLaw.setEli("ELI");
    amendingLaw.setPrintAnnouncementGazette("GAZETTE");
    amendingLaw.setDigitalAnnouncementMedium("MEDIUM");
    amendingLaw.setPublicationDate(now);
    amendingLaw.setPrintedAnnouncementPage("PAGE");
    amendingLaw.setDigitalAnnouncementEdition("EDITION");
    amendingLaw.setArticles(article);

    // When
    final AmendingLawResponseSchema resultProcedure =
        AmendingLawResponseMapper.fromUseCaseData(amendingLaw);

    // Then
    assertThat(resultProcedure.getEli()).isEqualTo("ELI");
    assertThat(resultProcedure.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultProcedure.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(resultProcedure.getPublicationDate()).isEqualTo(now);
    assertThat(resultProcedure.getPrintedAnnouncementPage()).isEqualTo("PAGE");
    assertThat(resultProcedure.getDigitalAnnouncementEdition()).isEqualTo("EDITION");
    assertThat(resultProcedure.getArticles().getFirst())
        .isEqualTo(new ArticleResponseSchema("1234", "ELI"));
  }
}
