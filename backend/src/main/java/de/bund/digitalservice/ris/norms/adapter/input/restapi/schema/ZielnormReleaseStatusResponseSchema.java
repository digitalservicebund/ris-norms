package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.util.List;

/**
 * Schema for the zielnormen preview.
 *
 * @param normWorkEli the work eli of the zielnorm
 * @param title the title of the zielnorm
 * @param shortTitle the short title of the zielnorm
 * @param expressions the list of expressions that will either be set to gegenstandslos or be newly created when applying the zielnorm-references
 */
public record ZielnormReleaseStatusResponseSchema(
  String normWorkEli,
  String title,
  String shortTitle,
  List<Expression> expressions
) {
  /**
   * Schema for information on one expression of the zielnorm
   * @param normExpressionEli the eli of the expression
   * @param isGegenstandslos will the expression be gegenstandslos once the zielnorm-references are applied
   * @param currentStatus the release status of the expression
   */
  public record Expression(
    String normExpressionEli,
    boolean isGegenstandslos,
    Status currentStatus
  ) {
    /**
     * The release status of the expression.
     */
    public enum Status {
      NOT_RELEASED,
      PRAETEXT_RELEASED,
      VOLLDOKUMENTATION_RELEASED,
    }
  }
}
