package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;
import javax.annotation.Nullable;

/** Mapper class for converting between {@link Article} and {@link ArticleResponseSchema}. */
public class ArticleResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ArticleResponseMapper() {}

  /**
   * Creates a {@link ArticleResponseSchema} instance from a {@link Article} entity.
   *
   * @param article The input {@link Article} entity to be converted.
   * @param targetLawZf0 The zf0 version of the {@link Norm} this {@link Article} modifies, if it is
   *     an {@link Article} changing another {@link Norm}.
   * @return A new {@link ArticleResponseSchema} instance mapped from the input {@link Article}.
   */
  public static ArticleResponseSchema fromNormArticle(
    final Article article,
    final @Nullable Norm targetLawZf0
  ) {
    return new ArticleResponseSchema(
      article.getEnumeration().orElse(null),
      article.getEid().orElse(null),
      article.getHeading().orElse(null),
      article.getAffectedDocumentEli().orElse(null),
      Optional.ofNullable(targetLawZf0).map(Norm::getEli).orElse(null)
    );
  }
}
