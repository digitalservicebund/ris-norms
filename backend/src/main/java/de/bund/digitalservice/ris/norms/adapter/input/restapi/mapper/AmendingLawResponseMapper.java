package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawIncludingArticlesResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLawWithArticles;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.List;

/**
 * Mapper class for converting between {@link AmendingLawWithArticles} and {@link
 * AmendingLawIncludingArticlesResponseSchema}.
 */
public class AmendingLawResponseMapper {
  /**
   * Creates a {@link AmendingLawIncludingArticlesResponseSchema} instance from a {@link
   * AmendingLawWithArticles} entity.
   *
   * @param amendingLawWithArticles The input {@link AmendingLawWithArticles} entity to be
   *     converted.
   * @return A new {@link AmendingLawIncludingArticlesResponseSchema} instance mapped from the input
   *     {@link AmendingLawWithArticles}.
   */
  public static AmendingLawIncludingArticlesResponseSchema fromUseCaseData(
      final AmendingLawWithArticles amendingLawWithArticles) {
    return AmendingLawIncludingArticlesResponseSchema.builder()
        .eli(amendingLawWithArticles.getEli())
        .printAnnouncementGazette(amendingLawWithArticles.getPrintAnnouncementGazette())
        .digitalAnnouncementMedium(amendingLawWithArticles.getDigitalAnnouncementMedium())
        .publicationDate(amendingLawWithArticles.getPublicationDate())
        .printAnnouncementPage(amendingLawWithArticles.getPrintAnnouncementPage())
        .digitalAnnouncementEdition(amendingLawWithArticles.getDigitalAnnouncementEdition())
        .articles(fromUseCaseData(amendingLawWithArticles.getArticles()))
        .build();
  }

  /**
   * Creates a {@link AmendingLawIncludingArticlesResponseSchema} instance from a {@link
   * AmendingLawWithArticles} entity.
   *
   * @param amendingLaw The input {@link AmendingLaw} entity to be converted.
   * @return A new {@link AmendingLawResponseSchema} instance mapped from the input {@link
   *     AmendingLaw}.
   */
  public static AmendingLawResponseSchema fromUseCaseDataWithoutArticles(
      final AmendingLaw amendingLaw) {
    return AmendingLawResponseSchema.builder()
        .eli(amendingLaw.getEli())
        .printAnnouncementGazette(amendingLaw.getPrintAnnouncementGazette())
        .digitalAnnouncementMedium(amendingLaw.getDigitalAnnouncementMedium())
        .publicationDate(amendingLaw.getPublicationDate())
        .printAnnouncementPage(amendingLaw.getPrintAnnouncementPage())
        .digitalAnnouncementEdition(amendingLaw.getDigitalAnnouncementEdition())
        .build();
  }

  private static List<ArticleResponseSchema> fromUseCaseData(final List<Article> articles) {
    // TODO: Check what happens when articles are null
    return articles.stream()
        .map(
            article ->
                new ArticleResponseSchema(
                    article.getEnumeration(), article.getEli(), article.getTitle()))
        .toList();
  }
}
