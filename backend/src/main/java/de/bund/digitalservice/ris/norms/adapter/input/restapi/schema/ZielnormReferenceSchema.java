package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import jakarta.validation.constraints.NotEmpty;

/**
 * Schema for a {@link ZielnormReference}
 * @param typ the type of the reference
 * @param geltungszeit the id of the geltungszeit
 * @param eId the eId of the element creating the reference
 * @param zielnorm the work eli of the zielnorm
 */
public record ZielnormReferenceSchema(
  @NotEmpty String typ,
  @NotEmpty String geltungszeit,
  @NotEmpty String eId,
  @NotEmpty String zielnorm
) {}
