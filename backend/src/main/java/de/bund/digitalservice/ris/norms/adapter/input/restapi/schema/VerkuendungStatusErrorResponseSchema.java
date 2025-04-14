package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

/**
 * Schema for an error response
 *
 * @param verkuendungStatus
 * @param type
 * @param title
 * @param detail
 */

public record VerkuendungStatusErrorResponseSchema(
  String verkuendungStatus,
  String type,
  String title,
  String detail
)
  implements VerkuendungStatusResponseSchema {}
