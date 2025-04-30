package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeRequestSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import java.util.List;

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
      .inUse(zeitgrenze.isInUse())
      .build();
  }

  /**
   * Creates a {@link UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData} instances from a {@link ZeitgrenzeRequestSchema} instances.
   *
   * @param zeitgrenzeRequestSchemas The input {@link ZeitgrenzeRequestSchema} entity to be converted.
   * @return A list of {@link UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData}.
   */
  public static List<UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData> fromRequestSchema(
    final List<ZeitgrenzeRequestSchema> zeitgrenzeRequestSchemas
  ) {
    return zeitgrenzeRequestSchemas
      .stream()
      .map(zeitgrenzeResponseSchema ->
        new UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData(
          zeitgrenzeResponseSchema.getDate(),
          Zeitgrenze.Art.valueOf(zeitgrenzeResponseSchema.getArt().name())
        )
      )
      .toList();
  }
}
