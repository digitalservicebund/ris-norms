package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeRequestSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import java.util.List;
import java.util.stream.Collectors;

/** Mapper class for converting between {@link Zeitgrenze} and {@link ZeitgrenzeResponseSchema}. */
public class ZeitgrenzeMapper {

  private ZeitgrenzeMapper() {}

  /**
   * Creates a {@link ZeitgrenzeResponseSchema} instance from a {@link Zeitgrenze} entity.
   *
   * @param zeitgrenze The input {@link Zeitgrenze} entity to be converted.
   * @return A new {@link ZeitgrenzeResponseSchema}.
   */
  public static ZeitgrenzeResponseSchema fromUseCaseData(final Zeitgrenze zeitgrenze) {
    return ZeitgrenzeResponseSchema
      .builder()
      .id(zeitgrenze.getId().toString())
      .date(zeitgrenze.getDate())
      .art(zeitgrenze.getArt().name())
      .build();
  }

  /**
   * Creates a {@link Zeitgrenze} instances from a {@link ZeitgrenzeRequestSchema} instances.
   *
   * @param zeitgrenzeRequestSchemas The input {@link ZeitgrenzeRequestSchema} entity to be converted.
   * @return A list of {@link Zeitgrenze}.
   */
  public static List<Zeitgrenze> fromRequestSchema(
    final List<ZeitgrenzeRequestSchema> zeitgrenzeRequestSchemas
  ) {
    return zeitgrenzeRequestSchemas
      .stream()
      .map(zeitgrenzeResponseSchema ->
        Zeitgrenze
          .builder()
          .date(zeitgrenzeResponseSchema.getDate())
          .art(Zeitgrenze.Art.valueOf(zeitgrenzeResponseSchema.getArt().name()))
          .build()
      )
      .collect(Collectors.toList());
  }
}
