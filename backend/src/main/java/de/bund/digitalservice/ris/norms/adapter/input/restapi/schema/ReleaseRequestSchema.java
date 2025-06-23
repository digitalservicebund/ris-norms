package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.ReleaseType;

/**
 * Schema for a request to release a norm.
 *
 * @param releaseType
 */

public record ReleaseRequestSchema(ReleaseType releaseType) {}
