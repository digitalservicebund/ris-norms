package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ArticleDto;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmendingLawResponseMapperTest {

  @Test
  void testMapToDomain() {
    // Given
    final LocalDate now = LocalDate.now();
    final List<ArticleDto> articles = new ArrayList<>();
    articles.add(ArticleDto.builder().enumeration("1234").eid("ELI").title("title").build());

    final AmendingLawDto amendingLawDTO = new AmendingLawDto();
    amendingLawDTO.setEli("ELI");
    amendingLawDTO.setPrintAnnouncementGazette("GAZETTE");
    amendingLawDTO.setDigitalAnnouncementMedium("MEDIUM");
    amendingLawDTO.setPublicationDate(now);
    amendingLawDTO.setPrintAnnouncementPage("PAGE");
    amendingLawDTO.setDigitalAnnouncementEdition("EDITION");
    amendingLawDTO.setTitle("TITLE");
    amendingLawDTO.setArticleDtos(articles);

    // When
    final AmendingLaw resultProcedure = AmendingLawMapper.mapToDomainWithArticles(amendingLawDTO);

    // Then
    assertThat(resultProcedure.getEli()).isEqualTo("ELI");
    assertThat(resultProcedure.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultProcedure.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(resultProcedure.getPublicationDate()).isEqualTo(now);
    assertThat(resultProcedure.getPrintAnnouncementPage()).isEqualTo("PAGE");
    assertThat(resultProcedure.getDigitalAnnouncementEdition()).isEqualTo("EDITION");
    assertThat(resultProcedure.getTitle()).isEqualTo("TITLE");
    assertThat(resultProcedure.getArticles().getFirst())
        .isEqualTo(new Article("1234", "ELI", "title", null));
  }

  @Test
  void testMapToDto() {
    // Given
    final LocalDate now = LocalDate.now();
    final List<Article> articles = new ArrayList<>();
    articles.add(
        new de.bund.digitalservice.ris.norms.domain.entity.Article("1234", "ELI", "title", null));

    final AmendingLaw amendingLaw =
        AmendingLaw.builder()
            .eli("ELI")
            .printAnnouncementGazette("GAZETTE")
            .digitalAnnouncementMedium("MEDIUM")
            .publicationDate(now)
            .printAnnouncementPage("PAGE")
            .digitalAnnouncementEdition("EDITION")
            .title("TITLE")
            .articles(articles)
            .build();

    // When
    final AmendingLawDto resultAmendingLawDto = AmendingLawMapper.mapToDto(amendingLaw);

    // Then
    assertThat(resultAmendingLawDto.getEli()).isEqualTo("ELI");
    assertThat(resultAmendingLawDto.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultAmendingLawDto.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(resultAmendingLawDto.getPublicationDate()).isEqualTo(now);
    assertThat(resultAmendingLawDto.getPrintAnnouncementPage()).isEqualTo("PAGE");
    assertThat(resultAmendingLawDto.getTitle()).isEqualTo("TITLE");
    assertThat(resultAmendingLawDto.getArticleDtos().getFirst().getEnumeration()).isEqualTo("1234");
    assertThat(resultAmendingLawDto.getArticleDtos().getFirst().getEid()).isEqualTo("ELI");
    assertThat(resultAmendingLawDto.getArticleDtos().getFirst().getTitle()).isEqualTo("title");
  }
}
