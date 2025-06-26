package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Represents the response schema for information about a {@link Norm} on work level in the REST API.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Data
public class NormWorkResponseSchema {

  private String eli;

  private String title;
}
