package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchemaTemporary;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.Optional;

/** Mapper class for converting between {@link AmendingLaw} and the different response schemas. */
public class AmendingLawResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private AmendingLawResponseMapper() {}

  /**
   * Creates a {@link AmendingLawResponseSchema} instance from a {@link AmendingLaw} entity.
   *
   * @param amendingLaw The input {@link AmendingLaw} entity to be converted.
   * @return A new {@link AmendingLawResponseSchema} instance mapped from the input {@link
   *     AmendingLaw}.
   */
  public static AmendingLawResponseSchema fromUseCaseData(final AmendingLaw amendingLaw) {
    return AmendingLawResponseSchema.builder()
        .eli(amendingLaw.getEli())
        .printAnnouncementGazette(amendingLaw.getPrintAnnouncementGazette())
        .digitalAnnouncementMedium(amendingLaw.getDigitalAnnouncementMedium())
        .publicationDate(amendingLaw.getPublicationDate())
        .printAnnouncementPage(amendingLaw.getPrintAnnouncementPage())
        .digitalAnnouncementEdition(amendingLaw.getDigitalAnnouncementEdition())
        .title(amendingLaw.getTitle())
        .build();
  }

  /**
   * Just a temporary mapper method till the frontend is also updated with the new API, meaning
   * getting the articles not from the /amending-laws/{eli} endpoint but from
   * /amending-laws/{eli}/articles
   *
   * @param amendingLaw The input {@link AmendingLaw} entity to be converted.
   * @return A new {@link AmendingLawResponseSchemaTemporary} instance mapped from the input {@link
   *     AmendingLaw}.
   */
  public static AmendingLawResponseSchemaTemporary fromUseCaseDataTemporary(
      final AmendingLaw amendingLaw) {
    return AmendingLawResponseSchemaTemporary.builder()
        .eli(amendingLaw.getEli())
        .printAnnouncementGazette(amendingLaw.getPrintAnnouncementGazette())
        .digitalAnnouncementMedium(amendingLaw.getDigitalAnnouncementMedium())
        .publicationDate(amendingLaw.getPublicationDate())
        .printAnnouncementPage(amendingLaw.getPrintAnnouncementPage())
        .digitalAnnouncementEdition(amendingLaw.getDigitalAnnouncementEdition())
        .title(amendingLaw.getTitle())
        .articles(
            Optional.ofNullable(amendingLaw.getArticles())
                .map(
                    articles ->
                        articles.stream().map(ArticleResponseMapper::fromUseCaseData).toList())
                .orElse(null))
        .build();
  }
}
