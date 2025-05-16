package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormenPreviewResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZielnormenUseCase;

/**
 * Mapper between {@link LoadZielnormenUseCase.ZielnormPreview} and {@link ZielnormenPreviewResponseSchema}
 */
public class ZielnormenPreviewResponseMapper {

  private ZielnormenPreviewResponseMapper() {}

  /**
   * Creates a {@link ZielnormenPreviewResponseSchema} instance from a {@link LoadZielnormenUseCase.ZielnormPreview} entity.
   *
   * @param zielnormPreview The input {@link LoadZielnormenUseCase.ZielnormPreview} entity to be converted.
   * @return A new {@link ZielnormenPreviewResponseSchema}.
   */
  public static ZielnormenPreviewResponseSchema fromUseCaseData(
    final LoadZielnormenUseCase.ZielnormPreview zielnormPreview
  ) {
    return new ZielnormenPreviewResponseSchema(
      zielnormPreview.normWorkEli().toString(),
      zielnormPreview.title(),
      zielnormPreview.shortTitle(),
      zielnormPreview
        .expressions()
        .stream()
        .map(ZielnormenPreviewResponseMapper::fromUseCaseData)
        .toList()
    );
  }

  private static ZielnormenPreviewResponseSchema.Expression fromUseCaseData(
    final LoadZielnormenUseCase.ZielnormPreview.Expression zielnormPreviewExpression
  ) {
    return new ZielnormenPreviewResponseSchema.Expression(
      zielnormPreviewExpression.normExpressionEli().toString(),
      zielnormPreviewExpression.isGegenstandslos(),
      zielnormPreviewExpression.isCreated(),
      fromUseCaseData(zielnormPreviewExpression.createdBy())
    );
  }

  private static String fromUseCaseData(
    final LoadZielnormenUseCase.ZielnormPreview.CreatedBy createdBy
  ) {
    return switch (createdBy) {
      case SYSTEM -> "System";
      case THIS_VERKUENDUNG -> "diese Verkündung";
      case OTHER_VERKUENDUNG -> "andere Verkündung";
    };
  }
}
