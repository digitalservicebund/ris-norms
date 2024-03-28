package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;

/** Mapper class for converting between {@link AmendingLaw} and the different response schemas. */
public class LegalDocMlMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private LegalDocMlMapper() {}

  /**
   * Creates a {@link AmendingLawResponseSchema} instance from a {@link AmendingLaw} entity.
   *
   * @param amendingLaw The input {@link AmendingLaw} entity to be converted.
   * @return A new {@link AmendingLawResponseSchema} instance mapped from the input {@link
   *     AmendingLaw}.
   */
  public static AmendingLawResponseSchema fromLegalDocMl(final AmendingLaw amendingLaw) {
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
}
