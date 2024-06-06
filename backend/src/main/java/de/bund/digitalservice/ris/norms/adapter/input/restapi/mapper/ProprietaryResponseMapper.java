package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import java.time.LocalDate;

/**
 * Mapper class for converting between {@link Proprietary} metadata and {@link
 * ProprietaryResponseSchema}.
 */
public class ProprietaryResponseMapper {
  // Private constructor to hide the implicit public one and prevent instantiation
  private ProprietaryResponseMapper() {}

  /**
   * Creates a {@link ProprietaryResponseSchema} from {@link Proprietary} metadata.
   *
   * @param proprietary Input data to be converted
   * @return Converted data
   */
  public static ProprietaryResponseSchema fromProprietary(Proprietary proprietary) {
    return ProprietaryResponseSchema.builder()
        .fna(new ProprietaryResponseSchema.Fna(proprietary.getFna().orElse(null)))
        .build();
  }

  /**
   * Creates a {@link ProprietaryResponseSchema} from {@link Proprietary} metadata but getting the
   * metadata at specific dates.
   *
   * @param proprietary Input data to be converted
   * @param date the specific date
   * @return Converted data
   */
  public static ProprietaryResponseSchema fromProprietary(
      Proprietary proprietary, final LocalDate date) {
    return ProprietaryResponseSchema.builder()
        .fna(new ProprietaryResponseSchema.Fna(proprietary.getFna(date).orElse(null)))
        .build();
  }
}
