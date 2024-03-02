package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ArticleDto;
import de.bund.digitalservice.ris.norms.domain.entity.Article;

/** Mapper class for converting between {@link ArticleDto} and {@link Article}. */
public class ArticleMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ArticleMapper() {}

  /**
   * Maps a {@link ArticleDto} to the domain {@link Article}.
   *
   * @param articleDto The input {@link ArticleDto} to be mapped.
   * @return A new {@link Article} mapped from the input {@link ArticleDto}.
   */
  public static Article mapToDomain(final ArticleDto articleDto) {
    return Article.builder()
        .enumeration(articleDto.getEnumeration())
        .title(articleDto.getTitle())
        .eid(articleDto.getEid())
        .targetLaw(TargetLawMapper.mapToDomain(articleDto.getTargetLawDto()))
        .build();
  }

  /**
   * Maps a domain {@link Article} to {@link ArticleDto}.
   *
   * @param article The input {@link Article} to be mapped.
   * @return A new {@link ArticleDto} mapped from the input {@link Article}.
   */
  public static ArticleDto mapToDto(final Article article) {
    return ArticleDto.builder()
        .enumeration(article.getEnumeration())
        .eid(article.getEid())
        .title(article.getTitle())
        .targetLawDto(TargetLawMapper.mapToDto(article.getTargetLaw()))
        .build();
  }
}
