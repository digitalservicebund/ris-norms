package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormArticle;
import java.util.Optional;
import javax.annotation.Nullable;

/** Mapper class for converting between {@link NormArticle} and {@link ArticleResponseSchema}. */
public class ArticleResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ArticleResponseMapper() {}

  /**
   * Creates a {@link ArticleResponseSchema} instance from a {@link NormArticle} entity.
   *
   * @param article The input {@link NormArticle} entity to be converted.
   * @param targetLawZf0 The zf0 version of the {@link Norm} this {@link NormArticle} modifies, if
   *     it is an {@link NormArticle} changing another {@link Norm}.
   * @return A new {@link ArticleResponseSchema} instance mapped from the input {@link NormArticle}.
   */
  public static ArticleResponseSchema fromNormArticle(
      final NormArticle article, final @Nullable Norm targetLawZf0) {
    return new ArticleResponseSchema(
        article.getEnumeration().orElse(null),
        article.getEid().orElse(null),
        article.getHeading().orElse(null),
        article.getAffectedDocumentEli().orElse(null),
        Optional.ofNullable(targetLawZf0).flatMap(Norm::getEli).orElse(null));
  }
}
