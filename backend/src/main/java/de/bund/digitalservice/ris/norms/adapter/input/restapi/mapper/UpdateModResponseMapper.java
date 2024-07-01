package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.UpdateModResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateModsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/** Mapper class for converting to a {@link UpdateModResponseSchema}. */
public class UpdateModResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private UpdateModResponseMapper() {}

  /**
   * Creates a {@link UpdateModResponseSchema} instance from the {@link Norm} entities.
   *
   * @param result The {@link UpdateModsUseCase.Result} of the update use case.
   * @return A new {@link UpdateModResponseSchema} instance.
   */
  public static UpdateModResponseSchema fromResult(final UpdateModsUseCase.Result result) {
    return UpdateModResponseSchema.builder()
        .amendingNormXml(result.amendingNormXml())
        .targetNormZf0Xml(result.targetNormZf0Xml())
        .build();
  }
}
