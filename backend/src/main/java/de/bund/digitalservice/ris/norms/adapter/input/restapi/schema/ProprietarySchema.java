package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Schema returned by the API when fetching proprietary metadata form a norm. */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ProprietarySchema {
  private SimpleValue fna;
  private SimpleValue art;
  private SimpleValue typ;
  private SimpleValue subtyp;

  /** Nested schema for simple values. */
  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @SuperBuilder(toBuilder = true)
  public static class SimpleValue {
    private String value;
  }
}
