package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.util.List;

/**
 * Schema for the zielnormen preview.
 * @param normWorkEli the work eli of the zielnorm
 * @param title the title of the zielnorm
 * @param shortTitle the short title of the zielnorm
 * @param expressions the list of expressions that will either be set to gegenstandslos or be newly created when applying the zielnorm-references
 */
public record ZielnormenPreviewResponseSchema(
  String normWorkEli,
  String title,
  String shortTitle,
  List<Expression> expressions
) {
  /**
   * Schema for information on one expression of the zielnorm
   * @param normExpressionEli the eli of the expression
   * @param isGegenstandslos will the expression be gegenstandslos once the zielnorm-references are applied
   * @param isCreated does this expression already exist in the system
   * @param createdBy the thing that initially created this expression ("System", "diese Verkündung" or "andere Verkündung")
   */
  public record Expression(
    String normExpressionEli,
    boolean isGegenstandslos,
    boolean isCreated,
    String createdBy
  ) {}
}
