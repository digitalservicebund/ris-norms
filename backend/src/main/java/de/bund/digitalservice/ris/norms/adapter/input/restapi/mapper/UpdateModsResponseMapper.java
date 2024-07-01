package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.UpdateModsResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateModsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/** Mapper class for converting to a {@link UpdateModsResponseSchema}. */
public class UpdateModsResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private UpdateModsResponseMapper() {}

  /**
   * Creates a {@link UpdateModsResponseSchema} instance from the {@link Norm} entities.
   *
   * @param result The {@link UpdateModsUseCase.Result} of the update use case.
   * @return A new {@link UpdateModsResponseSchema} instance.
   */
  public static UpdateModsResponseSchema fromResult(final UpdateModsUseCase.Result result) {
    return UpdateModsResponseSchema.builder()
        .amendingNormXml(result.amendingNormXml())
        .targetNormZf0Xml(result.targetNormZf0Xml())
        .build();
  }
}
