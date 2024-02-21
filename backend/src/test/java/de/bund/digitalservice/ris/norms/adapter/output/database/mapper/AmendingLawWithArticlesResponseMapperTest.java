package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDTO;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ArticleDto;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLawWithArticles;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmendingLawWithArticlesResponseMapperTest {

  @Test
  void testMapToDomain() {
    // Given
    final LocalDate now = LocalDate.now();
    final List<ArticleDto> articles = new ArrayList<>();
    articles.add(ArticleDto.builder().enumeration("1234").eli("ELI").title("title").build());

    final AmendingLawDTO amendingLawDTO = new AmendingLawDTO();
    amendingLawDTO.setEli("ELI");
    amendingLawDTO.setPrintAnnouncementGazette("GAZETTE");
    amendingLawDTO.setDigitalAnnouncementMedium("MEDIUM");
    amendingLawDTO.setPublicationDate(now);
    amendingLawDTO.setPrintAnnouncementPage("PAGE");
    amendingLawDTO.setDigitalAnnouncementEdition("EDITION");
    amendingLawDTO.setArticleDtos(articles);

    // When
    final AmendingLawWithArticles resultProcedure =
        AmendingLawMapper.mapToDomainWithArticles(amendingLawDTO);

    // Then
    assertThat(resultProcedure.getEli()).isEqualTo("ELI");
    assertThat(resultProcedure.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultProcedure.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(resultProcedure.getPublicationDate()).isEqualTo(now);
    assertThat(resultProcedure.getPrintAnnouncementPage()).isEqualTo("PAGE");
    assertThat(resultProcedure.getDigitalAnnouncementEdition()).isEqualTo("EDITION");
    assertThat(resultProcedure.getArticles().getFirst())
        .isEqualTo(new Article("1234", "ELI", "title"));
  }

  @Test
  void testMapToDto() {
    // Given
    final LocalDate now = LocalDate.now();
    final List<Article> articles = new ArrayList<>();
    articles.add(
        new de.bund.digitalservice.ris.norms.domain.entity.Article("1234", "ELI", "title"));

    final AmendingLawWithArticles amendingLawWithArticles =
        AmendingLawWithArticles.builder()
            .eli("ELI")
            .printAnnouncementGazette("GAZETTE")
            .digitalAnnouncementMedium("MEDIUM")
            .publicationDate(now)
            .printAnnouncementPage("PAGE")
            .digitalAnnouncementEdition("EDITION")
            .articles(articles)
            .build();

    // When
    final AmendingLawDTO resultAmendingLawDTO = AmendingLawMapper.mapToDto(amendingLawWithArticles);

    // Then
    assertThat(resultAmendingLawDTO.getEli()).isEqualTo("ELI");
    assertThat(resultAmendingLawDTO.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultAmendingLawDTO.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(resultAmendingLawDTO.getPublicationDate()).isEqualTo(now);
    assertThat(resultAmendingLawDTO.getPrintAnnouncementPage()).isEqualTo("PAGE");
    assertThat(resultAmendingLawDTO.getArticleDtos().getFirst().getEnumeration()).isEqualTo("1234");
    assertThat(resultAmendingLawDTO.getArticleDtos().getFirst().getEli()).isEqualTo("ELI");
    assertThat(resultAmendingLawDTO.getArticleDtos().getFirst().getTitle()).isEqualTo("title");
  }
}
