package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.VerkuendungResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;

/**
 * Mapper for creating a {@link VerkuendungResponseSchema} from an Verkuendung and
 * the announced {@link Norm}.
 */
public class VerkuendungResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private VerkuendungResponseMapper() {}

  /**
   * Creates a {@link VerkuendungResponseSchema} instance from an Verkuendung and
   * the announced {@link Norm}.
   *
   * @param verkuendung The input {@link Verkuendung} entity to be converted.
   * @param norm The input {@link Norm} entity to be converted.
   * @return A new {@link VerkuendungResponseSchema} instance mapped from the input {@link Norm}.
   */
  public static VerkuendungResponseSchema fromAnnouncedNorm(
    final Verkuendung verkuendung,
    final Norm norm
  ) {
    return VerkuendungResponseSchema.builder()
      .eli(norm.getRegelungstext1().getExpressionEli().toString())
      .title(norm.getLongTitle().orElse(null))
      .frbrName(norm.getRegelungstext1().getMeta().getFRBRWork().getFRBRname().orElse(null))
      .frbrNumber(norm.getRegelungstext1().getMeta().getFRBRWork().getFRBRnumber().orElse(null))
      .frbrDateVerkuendung(norm.getRegelungstext1().getMeta().getFRBRWork().getFBRDate())
      .dateAusfertigung(norm.getRegelungstext1().getDateAusfertigung().orElse(null))
      .shortTitle(norm.getShortTitle().orElse(null))
      .fna(
        norm
          .getRechtsetzungsdokument()
          .getMeta()
          .getOrCreateProprietary()
          .getMetadataValue(Metadata.FNA)
          .orElse(null)
      )
      .importedAt(verkuendung.getImportTimestamp())
      .build();
  }
}
