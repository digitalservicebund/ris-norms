package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryFrameSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietarySingleElementSchema;
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
   * Creates a {@link ProprietaryFrameSchema} from {@link Proprietary} metadata but getting the
   * metadata at specific dates.
   *
   * @param proprietary Input data to be converted
   * @param date the specific date
   * @return Converted data
   */
  public static ProprietaryFrameSchema fromProprietary(
    Proprietary proprietary,
    final LocalDate date
  ) {
    return ProprietaryFrameSchema
      .builder()
      .fna(proprietary.getFna(date).orElse(null))
      .art(proprietary.getArt(date).orElse(null))
      .typ(proprietary.getTyp(date).orElse(null))
      .subtyp(proprietary.getSubtyp(date).orElse(null))
      .bezeichnungInVorlage(proprietary.getBezeichnungInVorlage(date).orElse(null))
      .artDerNorm(proprietary.getArtDerNorm(date).orElse(null))
      .staat(proprietary.getStaat(date).orElse(null))
      .beschliessendesOrgan(proprietary.getBeschliessendesOrgan(date).orElse(null))
      .qualifizierteMehrheit(proprietary.getQualifizierteMehrheit(date).orElse(null))
      .ressort(proprietary.getRessort(date).orElse(null))
      .organisationsEinheit(proprietary.getOrganisationsEinheit(date).orElse(null))
      .build();
  }

  /**
   * Creates a {@link ProprietarySingleElementSchema} from {@link Proprietary} metadata but getting
   * the metadata for a single element at specific dates.
   *
   * @param proprietary Input data to be converted
   * @param eid the eId of the single element
   * @param date the specific date
   * @return Converted data
   */
  public static ProprietarySingleElementSchema fromProprietarySingleElement(
    Proprietary proprietary,
    final String eid,
    final LocalDate date
  ) {
    return ProprietarySingleElementSchema
      .builder()
      .artDerNorm(proprietary.getArtDerNorm(date, eid).orElse(null))
      .build();
  }
}
