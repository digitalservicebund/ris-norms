package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryFrameSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietarySingleElementSchema;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import java.time.LocalDate;

/**
 * Mapper class for converting between {@link Proprietary} metadata and {@link
 * ProprietaryFrameSchema}.
 */
public class ProprietaryResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ProprietaryResponseMapper() {}

  /**
   * Creates a {@link ProprietaryFrameSchema} from {@link Proprietary} metadata.
   *
   * @param proprietary Input data to be converted
   * @param dateForRessort date of point in time of the expression needed for determining the ressort
   * @return Converted data
   */
  public static ProprietaryFrameSchema fromProprietary(
    final Proprietary proprietary,
    final LocalDate dateForRessort
  ) {
    return ProprietaryFrameSchema
      .builder()
      .fna(proprietary.getMetadataValue(Metadata.FNA).orElse(null))
      .art(proprietary.getMetadataValue(Metadata.ART).orElse(null))
      .typ(proprietary.getMetadataValue(Metadata.TYP).orElse(null))
      .subtyp(proprietary.getMetadataValue(Metadata.SUBTYP).orElse(null))
      .bezeichnungInVorlage(
        proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE).orElse(null)
      )
      .artDerNorm(proprietary.getMetadataValue(Metadata.ART_DER_NORM).orElse(null))
      .staat(proprietary.getMetadataValue(Metadata.STAAT).orElse(null))
      .beschliessendesOrgan(
        proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN).orElse(null)
      )
      .qualifizierteMehrheit(
        proprietary
          .getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)
          .map(Boolean::parseBoolean)
          .orElse(null)
      )
      .ressort(proprietary.getRessort(dateForRessort).orElse(null))
      .organisationsEinheit(
        proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT).orElse(null)
      )
      .build();
  }

  /**
   * Creates a {@link ProprietarySingleElementSchema} from {@link Proprietary} metadata.
   *
   * @param proprietary Input data to be converted
   * @param eid the eId of the single element
   * @return Converted data
   */
  public static ProprietarySingleElementSchema fromProprietarySingleElement(
    final Proprietary proprietary,
    final EId eid
  ) {
    return ProprietarySingleElementSchema
      .builder()
      .artDerNorm(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid).orElse(null))
      .build();
  }
}
