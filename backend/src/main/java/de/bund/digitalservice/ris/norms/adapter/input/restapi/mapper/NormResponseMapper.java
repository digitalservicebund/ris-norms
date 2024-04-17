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
    var isDigitallyAnnounced = norm.getFRBRnumber().map(s -> !s.startsWith("s")).orElse(false);

    return new NormResponseSchema(
        norm.getEli().orElse(null),
        norm.getTitle().orElse(null),
        norm.getFRBRname().orElse(null),
        norm.getFRBRnumber().orElse(null),
        isDigitallyAnnounced ? null : norm.getFRBRname().orElse(null),
        isDigitallyAnnounced ? norm.getFRBRname().orElse(null) : null,
        norm.getPublicationDate().orElse(null),
        isDigitallyAnnounced ? null : norm.getFRBRnumber().map(s -> s.substring(1)).orElse(null),
        isDigitallyAnnounced ? norm.getFRBRnumber().orElse(null) : null);
  }
}
