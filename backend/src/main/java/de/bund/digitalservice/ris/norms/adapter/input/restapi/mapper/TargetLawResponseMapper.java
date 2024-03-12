package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TargetLawResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;

/** Mapper class for converting between {@link TargetLaw} and {@link TargetLawResponseSchema}. */
public class TargetLawResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private TargetLawResponseMapper() {}

  /**
   * Creates a {@link TargetLawResponseSchema} instance from a {@link TargetLaw} entity.
   *
   * @param targetLaw The input {@link TargetLaw} entity to be converted.
   * @return A new {@link TargetLawResponseSchema} instance mapped from the input {@link TargetLaw}.
   */
  public static TargetLawResponseSchema fromUseCaseData(final TargetLaw targetLaw) {
    return TargetLawResponseSchema.builder()
        .eli(targetLaw.getEli())
        .title(targetLaw.getTitle())
        .fna(targetLaw.getFna())
        .shortTitle(targetLaw.getShortTitle())
        .build();
  }
}
