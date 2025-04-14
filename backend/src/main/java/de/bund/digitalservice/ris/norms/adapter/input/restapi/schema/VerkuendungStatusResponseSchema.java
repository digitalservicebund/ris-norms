package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

/**
 * Response interface of a verkuendung status request
 */
public sealed interface VerkuendungStatusResponseSchema
  permits
    VerkuendungStatusProcessingOrSuccessResponseSchema, VerkuendungStatusErrorResponseSchema {}
