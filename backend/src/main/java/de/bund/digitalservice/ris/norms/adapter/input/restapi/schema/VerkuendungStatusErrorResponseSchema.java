package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

/**
 * Schema for an error response
 *
 * @param status
 * @param detail
 */
public record VerkuendungStatusErrorResponseSchema(String status, String detail) implements
  VerkuendungStatusResponseSchema {}
