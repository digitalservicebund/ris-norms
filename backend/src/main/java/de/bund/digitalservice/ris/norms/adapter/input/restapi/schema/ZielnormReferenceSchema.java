package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;

/**
 * Schema for a {@link ZielnormReference}
 * @param typ the type of the reference
 * @param geltungszeit the id of the geltungszeit
 * @param eId the eId of the element creating the reference
 * @param zielnorm the work eli of the zielnorm
 */
public record ZielnormReferenceSchema(
  String typ,
  String geltungszeit,
  String eId,
  String zielnorm
) {}
