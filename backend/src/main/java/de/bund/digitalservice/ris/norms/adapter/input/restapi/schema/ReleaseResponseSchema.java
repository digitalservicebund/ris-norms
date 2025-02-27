package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Information about the release of a norm.
 */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ReleaseResponseSchema {

  private Instant releaseAt;
  private List<String> norms;
}
