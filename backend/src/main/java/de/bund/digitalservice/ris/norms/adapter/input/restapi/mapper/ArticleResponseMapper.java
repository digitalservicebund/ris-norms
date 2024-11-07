package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;

/** Mapper class for converting between {@link Article} and {@link ArticleResponseSchema}. */
public class ArticleResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ArticleResponseMapper() {}

  /**
   * Creates a {@link ArticleResponseSchema} instance from a {@link Article} entity.
   *
   * @param article The input {@link Article} entity to be converted.
   * @return A new {@link ArticleResponseSchema} instance mapped from the input {@link Article}.
   */
  public static ArticleResponseSchema fromNormArticle(final Article article) {
    return new ArticleResponseSchema(
      article.getEnumeration().orElse(null),
      article.getEid(),
      article.getHeading().orElse(null),
      article.getAffectedDocumentEli().map(ExpressionEli::toString).orElse(null)
    );
  }
}
