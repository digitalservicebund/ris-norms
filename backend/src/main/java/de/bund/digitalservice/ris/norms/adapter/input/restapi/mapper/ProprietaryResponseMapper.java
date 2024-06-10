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
   * Creates a {@link ProprietarySchema} from {@link Proprietary} metadata.
   *
   * @param proprietary Input data to be converted
   * @return Converted data
   */
  public static ProprietarySchema fromProprietary(Proprietary proprietary) {
    return ProprietarySchema.builder()
        .fna(new ProprietarySchema.SimpleValue(proprietary.getFna().orElse(null)))
        .build();
  }

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
        .fna(new ProprietarySchema.SimpleValue(proprietary.getFna(date).orElse(null)))
        .art(new ProprietarySchema.SimpleValue(null))
        .typ(new ProprietarySchema.SimpleValue(null))
        .subtyp(new ProprietarySchema.SimpleValue(null))
        .build();
  }
}
