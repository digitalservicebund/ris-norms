package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryFrameSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietarySingleElementSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RahmenMetadata;

/**
 * Mapper class for converting between {@link Proprietary} metadata and {@link
 * ProprietaryFrameSchema}.
 */
public class ProprietaryResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private ProprietaryResponseMapper() {}

  /**
   * Creates a {@link ProprietaryFrameSchema} from {@link RahmenMetadata}.
   *
   * @param rahmenMetadata Input data to be converted
   * @return Converted data
   */
  public static ProprietaryFrameSchema fromRahmenMetadata(final RahmenMetadata rahmenMetadata) {
    return ProprietaryFrameSchema.builder()
      .fna(rahmenMetadata.getFna().orElse(null))
      .typ(rahmenMetadata.getTyp().orElse(null))
      .subtyp(rahmenMetadata.getSubtyp().orElse(null))
      .bezeichnungInVorlage(rahmenMetadata.getBezeichnungInVorlage().orElse(null))
      .artDerNorm(rahmenMetadata.getArtDerNorm().orElse(null))
      .staat(rahmenMetadata.getStaat().orElse(null))
      .beschliessendesOrgan(rahmenMetadata.getBeschliessendesOrgan().orElse(null))
      .qualifizierteMehrheit(rahmenMetadata.getQualifizierteMehrheit().orElse(null))
      .ressort(rahmenMetadata.getRessort().orElse(null))
      .organisationsEinheit(rahmenMetadata.getOrganisationsEinheit().orElse(null))
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
    return ProprietarySingleElementSchema.builder()
      .artDerNorm(proprietary.getMetadataValue(Metadata.ART_DER_NORM, eid).orElse(null))
      .build();
  }
}
