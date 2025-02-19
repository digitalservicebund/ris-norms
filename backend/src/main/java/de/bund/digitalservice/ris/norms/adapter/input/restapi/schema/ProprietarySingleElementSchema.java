package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Schema for the API when fetching/updating proprietary metadata form a norm. */
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ProprietarySingleElementSchema {

  private String artDerNorm;
}
