package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

/**
 * Schema for an non error response
 *
 * @param status
 */
public record VerkuendungStatusProcessingOrSuccessResponseSchema(String status) implements
  VerkuendungStatusResponseSchema {}
