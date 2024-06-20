package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietarySchema;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import java.time.LocalDate;

/**
 * Mapper class for converting between {@link Proprietary} metadata and {@link ProprietarySchema}.
 */
public class ProprietaryResponseMapper {
  // Private constructor to hide the implicit public one and prevent instantiation
  private ProprietaryResponseMapper() {}

  /**
   * Creates a {@link ProprietarySchema} from {@link Proprietary} metadata but getting the metadata
   * at specific dates.
   *
   * @param proprietary Input data to be converted
   * @param date the specific date
   * @return Converted data
   */
  public static ProprietarySchema fromProprietary(Proprietary proprietary, final LocalDate date) {
    return ProprietarySchema.builder()
        .fna(proprietary.getFna(date).orElse(null))
        .art(proprietary.getArt(date).orElse(null))
        .typ(proprietary.getTyp(date).orElse(null))
        .subtyp(proprietary.getSubtyp(date).orElse(null))
        .bezeichnungInVorlage(proprietary.getBezeichnungInVorlage(date).orElse(null))
        .artDerNorm(proprietary.getArtDerNorm(date).orElse(null))
        .normgeber(proprietary.getNormgeber(date).orElse(null))
        .beschliessendesOrgan(proprietary.getBeschliessendesOrgan(date).orElse(null))
        .qualifizierteMehrheit(proprietary.getQualifizierteMehrheit(date).orElse(null))
        .federfuehrung(proprietary.getFederfuehrung(date).orElse(null))
        .organisationsEinheit(proprietary.getOrganisationsEinheit(date).orElse(null))
        .build();
  }
}
