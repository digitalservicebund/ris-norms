package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDTO;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ArticleDto;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.List;

/** Mapper class for converting between {@link AmendingLawDTO} and {@link AmendingLaw}. */
public class AmendingLawMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private AmendingLawMapper() {}

  /**
   * Maps a {@link AmendingLawDTO} to a {@link AmendingLaw} entity.
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
        amendingLawDTO.getPrintedAnnouncementPage(),
        amendingLawDTO.getDigitalAnnouncementEdition(),
        mapToDomain(amendingLawDTO.getArticleDtos()));
  }

  private static List<Article> mapToDomain(final List<ArticleDto> articlesDTO) {
    return articlesDTO.stream()
        .map(dto -> new Article(dto.getEnumeration(), dto.getEli()))
        .toList();
  }

  /**
   * Maps a {@link AmendingLaw} entity to a {@link AmendingLawDTO}.
   *
   * @param amendingLaw The input {@link AmendingLaw} entity to be mapped.
   * @return A new {@link AmendingLawDTO} mapped from the input {@link AmendingLaw} entity.
   */
  public static AmendingLawDTO mapToDto(final AmendingLaw amendingLaw) {
    return AmendingLawDTO.builder()
        .eli(amendingLaw.getEli())
        .printAnnouncementGazette(amendingLaw.getPrintAnnouncementGazette())
        .digitalAnnouncementMedium(amendingLaw.getDigitalAnnouncementMedium())
        .publicationDate(amendingLaw.getPublicationDate())
        .printedAnnouncementPage(amendingLaw.getPrintedAnnouncementPage())
        .digitalAnnouncementEdition(amendingLaw.getDigitalAnnouncementEdition())
        .articleDtos(mapToDtos(amendingLaw.getArticles()))
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
                    .build())
        .toList();
  }
}
