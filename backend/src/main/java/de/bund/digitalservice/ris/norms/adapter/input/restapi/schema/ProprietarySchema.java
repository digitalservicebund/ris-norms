package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Schema returned by the API when fetching proprietary metadata form a norm. */
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ProprietarySchema {
  private String fna;
  private String art;
  private String typ;
  private String subtyp;
}
