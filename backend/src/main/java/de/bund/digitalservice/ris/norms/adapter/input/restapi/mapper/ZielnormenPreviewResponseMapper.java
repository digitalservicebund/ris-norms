package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormenPreviewResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Zielnorm;

/**
 * Mapper between {@link Zielnorm} and {@link ZielnormenPreviewResponseSchema}
 */
public class ZielnormenPreviewResponseMapper {

  private ZielnormenPreviewResponseMapper() {}

  /**
   * Creates a {@link ZielnormenPreviewResponseSchema} instance from a {@link Zielnorm} entity.
   *
   * @param zielnorm The input {@link Zielnorm} entity to be converted.
   * @return A new {@link ZielnormenPreviewResponseSchema}.
   */
  public static ZielnormenPreviewResponseSchema fromUseCaseData(final Zielnorm zielnorm) {
    return new ZielnormenPreviewResponseSchema(
      zielnorm.normWorkEli().toString(),
      zielnorm.title(),
      zielnorm.shortTitle(),
      zielnorm.expressions().stream().map(ZielnormenPreviewResponseMapper::fromUseCaseData).toList()
    );
  }

  private static ZielnormenPreviewResponseSchema.Expression fromUseCaseData(
    final Zielnorm.Expression zielnormPreviewExpression
  ) {
    return new ZielnormenPreviewResponseSchema.Expression(
      zielnormPreviewExpression.normExpressionEli().toString(),
      zielnormPreviewExpression.isGegenstandslos(),
      zielnormPreviewExpression.isCreated(),
      fromUseCaseData(zielnormPreviewExpression.createdBy())
    );
  }

  private static String fromUseCaseData(final Zielnorm.CreatedBy createdBy) {
    return switch (createdBy) {
      case SYSTEM -> "System";
      case THIS_VERKUENDUNG -> "diese Verkündung";
      case OTHER_VERKUENDUNG -> "andere Verkündung";
    };
  }
}
