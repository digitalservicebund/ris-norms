package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/** Schema returned by the API when fetching proprietary metadata form a norm. */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ProprietaryResponseSchema {
  private Fna fna;

  /** Nested schema for FNAs. */
  @AllArgsConstructor
  @Data
  @SuperBuilder(toBuilder = true)
  public static class Fna {
    private String value;
  }
}
