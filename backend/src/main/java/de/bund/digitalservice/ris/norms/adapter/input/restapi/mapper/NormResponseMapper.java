package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
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
    return NormResponseSchema
      .builder()
      .eli(norm.getRegelungstext1().getExpressionEli().toString())
      .title(norm.getTitle().orElse(null))
      .frbrName(norm.getRegelungstext1().getMeta().getFRBRWork().getFRBRname().orElse(null))
      .frbrNumber(norm.getRegelungstext1().getMeta().getFRBRWork().getFRBRnumber().orElse(null))
      .frbrDateVerkuendung(norm.getRegelungstext1().getMeta().getFRBRWork().getFBRDate())
      .shortTitle(norm.getShortTitle().orElse(null))
      .fna(
        norm
          .getRegelungstext1()
          .getMeta()
          .getOrCreateProprietary()
          .getMetadataValue(Metadata.FNA)
          .orElse(null)
      )
      .status("status-not-yet-implemented")
      .build();
  }
}
