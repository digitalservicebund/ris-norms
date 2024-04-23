package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TimeBoundaryResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;

/**
 * Mapper class for converting between {@link TimeBoundary} and {@link TimeBoundaryResponseSchema}.
 */
public class TimeBoundaryResponseMapper {

  private TimeBoundaryResponseMapper() {}

  /**
   * Creates a {@link TimeBoundaryResponseSchema} instance from a {@link TimeBoundary} entity.
   *
   * @param timeBoundary The input {@link TimeBoundary} entity to be converted.
   * @return A new {@link TimeBoundaryResponseSchema} instance mapped from the input {@link
   *     TimeBoundary}.
   */
  public static TimeBoundaryResponseSchema fromUseCaseData(final TimeBoundary timeBoundary) {
    return TimeBoundaryResponseSchema.builder()
        .date(timeBoundary.getDate().orElse(null))
        .eid(timeBoundary.getEid().orElse(null))
        .build();
  }
}
