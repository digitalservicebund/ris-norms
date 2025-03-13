package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Mapper for creating a {@link VerkuendungResponseSchema} from an announcement and
 * the announced {@link Norm}.
 */
public class VerkuendungResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private VerkuendungResponseMapper() {}

  /**
   * Creates a {@link VerkuendungResponseSchema} instance from an announcement and
   * the announced {@link Norm}.
   *
   * @param norm The input {@link Norm} entity to be converted.
   * @return A new {@link VerkuendungResponseSchema} instance mapped from the input {@link Norm}.
   */
  public static VerkuendungResponseSchema fromUseCaseData(final Norm norm) {
    return VerkuendungResponseSchema
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
      .build();
  }
}
