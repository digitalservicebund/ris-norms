package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

/**
 * Schema for an non error response
 *
 * @param verkuendungStatus
 */
public record VerkuendungStatusProcessingOrSuccessResponseSchema(
  String verkuendungStatus
) implements VerkuendungStatusResponseSchema {}
