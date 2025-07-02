package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormExpressionListResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadExpressionsOfNormWorkUseCase;

/** Mapper class for converting between {@link LoadExpressionsOfNormWorkUseCase.Result} and {@link NormExpressionListResponseSchema}. */
public class NormExpressionListResponseMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private NormExpressionListResponseMapper() {}

  /**
   * Creates a {@link NormExpressionListResponseSchema} instance from a {@link LoadExpressionsOfNormWorkUseCase.Result}.
   *
   * @param result The use-case result to be converted.
   * @return A new {@link NormExpressionListResponseSchema} instance mapped from the input.
   */
  public static NormExpressionListResponseSchema fromUseCaseData(
    final LoadExpressionsOfNormWorkUseCase.Result result
  ) {
    return NormExpressionListResponseSchema.builder()
      .eli(result.eli().toString())
      .gegenstandslos(result.gegenstandslos())
      .build();
  }
}
