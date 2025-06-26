package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormWorkResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/** Mapper class for converting between {@link Norm} and {@link NormWorkResponseSchema}. */
public class NormWorkResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private NormWorkResponseMapper() {}

  /**
   * Creates a {@link NormWorkResponseSchema} instance from a {@link Norm} entity.
   *
   * @param norm The input {@link Norm} entity to be converted.
   * @return A new {@link NormWorkResponseSchema} instance mapped from the input {@link Norm}.
   */
  public static NormWorkResponseSchema fromUseCaseData(final Norm norm) {
    return NormWorkResponseSchema.builder()
      .eli(norm.getWorkEli().toString())
      .title(norm.getTitle().orElse(null))
      .build();
  }
}
