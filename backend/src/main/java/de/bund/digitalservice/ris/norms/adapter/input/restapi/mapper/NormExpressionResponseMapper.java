package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormExpressionResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadExpressionsOfNormWorkUseCase;

/** Mapper class for converting between {@link LoadExpressionsOfNormWorkUseCase.Result} and {@link NormExpressionResponseSchema}. */
public class NormExpressionResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private NormExpressionResponseMapper() {}

  /**
   * Creates a {@link NormExpressionResponseSchema} instance from a {@link LoadExpressionsOfNormWorkUseCase.Result}.
   *
   * @param result The use-case result to be converted.
   * @return A new {@link NormExpressionResponseSchema} instance mapped from the input.
   */
  public static NormExpressionResponseSchema fromUseCaseData(
    final LoadExpressionsOfNormWorkUseCase.Result result
  ) {
    return NormExpressionResponseSchema.builder()
      .eli(result.eli().toString())
      .gegenstandslos(result.gegenstandslos())
      .build();
  }
}
