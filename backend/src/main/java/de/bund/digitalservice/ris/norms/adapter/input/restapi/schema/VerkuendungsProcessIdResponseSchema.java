package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

/**
 * Response Schema for processing a zip file containing a Verkuendung
 *
 * @param processId that can be used to get the status from the status endpoint
 */
public record VerkuendungsProcessIdResponseSchema(String processId) {}
