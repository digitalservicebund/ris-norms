package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/** Mapper class for converting between {@link Norm} and {@link NormResponseSchema}. */
public class NormResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private NormResponseMapper() {}

  /**
   * Creates a {@link NormResponseSchema} instance from a {@link Norm} entity.
   *
   * @param norm The input {@link Norm} entity to be converted.
   * @return A new {@link NormResponseSchema} instance mapped from the input {@link Norm}.
   */
  public static NormResponseSchema fromUseCaseData(final Norm norm) {
    boolean isDigitallyAnnounced = norm.getFRBRnumber().map(s -> !s.startsWith("s")).orElse(false);

    return NormResponseSchema.builder()
        .eli(norm.getEli().orElse(null))
        .title(norm.getTitle().orElse(null))
        .frbrName(norm.getFRBRname().orElse(null))
        .frbrNumber(norm.getFRBRnumber().orElse(null))
        .printAnnouncementGazette(isDigitallyAnnounced ? null : norm.getFRBRname().orElse(null))
        .digitalAnnouncementMedium(isDigitallyAnnounced ? norm.getFRBRname().orElse(null) : null)
        .frbrDateVerkuendung(norm.getFBRDateVerkuendung().orElse(null))
        .printAnnouncementPage(
            isDigitallyAnnounced
                ? null
                : norm.getFRBRnumber().map(s -> s.substring(1)).orElse(null))
        .digitalAnnouncementEdition(isDigitallyAnnounced ? norm.getFRBRnumber().orElse(null) : null)
        .shortTitle(norm.getShortTitle().orElse(null))
        .fna(norm.getFna().orElse(null))
        .build();
  }
}
