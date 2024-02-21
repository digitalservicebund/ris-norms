package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDTO;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ArticleDto;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLawWithArticles;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.List;

/**
 * Mapper class for converting between {@link AmendingLawDTO} and {@link AmendingLawWithArticles}.
 */
public class AmendingLawMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private AmendingLawMapper() {}

  /**
   * Maps a {@link AmendingLawDTO} to a {@link AmendingLawWithArticles} entity.
   *
   * @param amendingLawDTO The input {@link AmendingLawDTO} to be mapped.
   * @return A new {@link AmendingLawWithArticles} entity mapped from the input {@link
   *     AmendingLawDTO}.
   */
  public static AmendingLawWithArticles mapToDomainWithArticles(
      final AmendingLawDTO amendingLawDTO) {

    return new AmendingLawWithArticles(
        amendingLawDTO.getEli(),
        amendingLawDTO.getPrintAnnouncementGazette(),
        amendingLawDTO.getDigitalAnnouncementMedium(),
        amendingLawDTO.getPublicationDate(),
        amendingLawDTO.getPrintAnnouncementPage(),
        amendingLawDTO.getDigitalAnnouncementEdition(),
        mapToDomainWithArticles(amendingLawDTO.getArticleDtos()));
  }

  /**
   * Maps a {@link AmendingLawDTO} to a {@link AmendingLawWithArticles} entity.
   *
   * @param amendingLawDTO The input {@link AmendingLawDTO} to be mapped.
   * @return A new {@link AmendingLaw} entity mapped from the input {@link AmendingLawDTO}.
   */
  public static AmendingLaw mapToDomain(final AmendingLawDTO amendingLawDTO) {

    return new AmendingLaw(
        amendingLawDTO.getEli(),
        amendingLawDTO.getPrintAnnouncementGazette(),
        amendingLawDTO.getDigitalAnnouncementMedium(),
        amendingLawDTO.getPublicationDate(),
        amendingLawDTO.getPrintAnnouncementPage(),
        amendingLawDTO.getDigitalAnnouncementEdition());
  }

  private static List<Article> mapToDomainWithArticles(final List<ArticleDto> articlesDTO) {
    return articlesDTO.stream()
        .map(dto -> new Article(dto.getEnumeration(), dto.getEli(), dto.getTitle()))
        .toList();
  }

  /**
   * Maps a {@link AmendingLawWithArticles} entity to a {@link AmendingLawDTO}.
   *
   * @param amendingLawWithArticles The input {@link AmendingLawWithArticles} entity to be mapped.
   * @return A new {@link AmendingLawDTO} mapped from the input {@link AmendingLawWithArticles}
   *     entity.
   */
  public static AmendingLawDTO mapToDto(final AmendingLawWithArticles amendingLawWithArticles) {
    return AmendingLawDTO.builder()
        .eli(amendingLawWithArticles.getEli())
        .printAnnouncementGazette(amendingLawWithArticles.getPrintAnnouncementGazette())
        .digitalAnnouncementMedium(amendingLawWithArticles.getDigitalAnnouncementMedium())
        .publicationDate(amendingLawWithArticles.getPublicationDate())
        .printAnnouncementPage(amendingLawWithArticles.getPrintAnnouncementPage())
        .digitalAnnouncementEdition(amendingLawWithArticles.getDigitalAnnouncementEdition())
        .articleDtos(mapToDtos(amendingLawWithArticles.getArticles()))
        .build();
  }

  private static List<ArticleDto> mapToDtos(
      final List<de.bund.digitalservice.ris.norms.domain.entity.Article> articles) {
    return articles.stream()
        .map(
            article ->
                ArticleDto.builder()
                    .enumeration(article.getEnumeration())
                    .eli(article.getEli())
                    .title(article.getTitle())
                    .build())
        .toList();
  }
}
