package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TimeBoundarySchema;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundaryChangeData;
import java.util.List;

/** Mapper class for converting between {@link TimeBoundary} and {@link TimeBoundarySchema}. */
public class TimeBoundaryMapper {

  private TimeBoundaryMapper() {}

  /**
   * Creates a {@link TimeBoundarySchema} instance from a {@link TimeBoundary} entity.
   *
   * @param timeBoundary The input {@link TimeBoundary} entity to be converted.
   * @return A new {@link TimeBoundarySchema} instance mapped from the input {@link TimeBoundary}.
   */
  public static TimeBoundarySchema fromUseCaseData(final TimeBoundary timeBoundary) {
    return TimeBoundarySchema.builder()
        .date(timeBoundary.getDate().orElse(null))
        .eid(timeBoundary.getEventRefEid().orElse(null))
        .build();
  }

  /**
   * Creates a {@link TimeBoundarySchema} instance from a {@link TimeBoundary} entity.
   *
   * @param timeBoundaries The input {@link TimeBoundary} entity to be converted.
   * @return A new {@link TimeBoundarySchema} instance mapped from the input {@link TimeBoundary}.
   */
  public static List<TimeBoundaryChangeData> fromResponseSchema(
      final List<TimeBoundarySchema> timeBoundaries) {
    return timeBoundaries.stream()
        .map(
            timeBoundary ->
                TimeBoundaryChangeData.builder()
                    .date(timeBoundary.getDate())
                    .eid(timeBoundary.getEid())
                    .build())
        .toList();
  }
}
