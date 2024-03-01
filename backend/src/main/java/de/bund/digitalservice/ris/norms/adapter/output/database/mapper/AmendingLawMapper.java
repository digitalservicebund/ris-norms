package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ArticleDto;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.List;

/** Mapper class for converting between {@link AmendingLawDto} and {@link AmendingLaw}. */
public class AmendingLawMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private AmendingLawMapper() {}

  /**
   * Maps a {@link AmendingLawDto} to a {@link AmendingLaw} entity.
   *
   * @param amendingLawDTO The input {@link AmendingLawDto} to be mapped.
   * @return A new {@link AmendingLaw} entity mapped from the input {@link AmendingLawDto}.
   */
  public static AmendingLaw mapToDomainWithArticles(final AmendingLawDto amendingLawDTO) {

    return new AmendingLaw(
        amendingLawDTO.getEli(),
        amendingLawDTO.getPrintAnnouncementGazette(),
        amendingLawDTO.getDigitalAnnouncementMedium(),
        amendingLawDTO.getPublicationDate(),
        amendingLawDTO.getPrintAnnouncementPage(),
        amendingLawDTO.getDigitalAnnouncementEdition(),
        amendingLawDTO.getTitle(),
        amendingLawDTO.getXml(),
        mapToDomainWithArticles(amendingLawDTO.getArticleDtos()));
  }

  /**
   * Maps a {@link AmendingLawDto} to a {@link AmendingLaw} entity.
   *
   * @param amendingLawDTO The input {@link AmendingLawDto} to be mapped.
   * @return A new {@link AmendingLaw} entity mapped from the input {@link AmendingLawDto}.
   */
  public static AmendingLaw mapToDomain(final AmendingLawDto amendingLawDTO) {

    return new AmendingLaw(
        amendingLawDTO.getEli(),
        amendingLawDTO.getPrintAnnouncementGazette(),
        amendingLawDTO.getDigitalAnnouncementMedium(),
        amendingLawDTO.getPublicationDate(),
        amendingLawDTO.getPrintAnnouncementPage(),
        amendingLawDTO.getDigitalAnnouncementEdition(),
        amendingLawDTO.getTitle(),
        amendingLawDTO.getXml(),
        null);
  }

  private static List<Article> mapToDomainWithArticles(final List<ArticleDto> articlesDTO) {
    return articlesDTO.stream()
        .map(dto -> new Article(dto.getEnumeration(), dto.getEid(), dto.getTitle(), null))
        .toList();
  }

  /**
   * Maps a {@link AmendingLaw} entity to a {@link AmendingLawDto}.
   *
   * @param amendingLaw The input {@link AmendingLaw} entity to be mapped.
   * @return A new {@link AmendingLawDto} mapped from the input {@link AmendingLaw} entity.
   */
  public static AmendingLawDto mapToDto(final AmendingLaw amendingLaw) {
    return AmendingLawDto.builder()
        .eli(amendingLaw.getEli())
        .printAnnouncementGazette(amendingLaw.getPrintAnnouncementGazette())
        .digitalAnnouncementMedium(amendingLaw.getDigitalAnnouncementMedium())
        .publicationDate(amendingLaw.getPublicationDate())
        .printAnnouncementPage(amendingLaw.getPrintAnnouncementPage())
        .digitalAnnouncementEdition(amendingLaw.getDigitalAnnouncementEdition())
        .title(amendingLaw.getTitle())
        .articleDtos(mapToDtos(amendingLaw.getArticles()))
        .xml(amendingLaw.getXml())
        .build();
  }

  private static List<ArticleDto> mapToDtos(
      final List<de.bund.digitalservice.ris.norms.domain.entity.Article> articles) {
    return articles.stream()
        .map(
            article ->
                ArticleDto.builder()
                    .enumeration(article.getEnumeration())
                    .eid(article.getEid())
                    .title(article.getTitle())
                    .build())
        .toList();
  }
}
