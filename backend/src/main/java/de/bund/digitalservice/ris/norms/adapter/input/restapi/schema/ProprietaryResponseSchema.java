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
  private Art art;
  private Typ typ;
  private Subtyp subtyp;

  /** Nested schema for FNAs. */
  @AllArgsConstructor
  @Data
  @SuperBuilder(toBuilder = true)
  public static class Fna {
    private String value;
  }

  /** Nested schema for art. */
  @AllArgsConstructor
  @Data
  @SuperBuilder(toBuilder = true)
  public static class Art {
    private String value;
  }

  /** Nested schema for typ. */
  @AllArgsConstructor
  @Data
  @SuperBuilder(toBuilder = true)
  public static class Typ {
    private String value;
  }

  /** Nested schema for subtyp. */
  @AllArgsConstructor
  @Data
  @SuperBuilder(toBuilder = true)
  public static class Subtyp {
    private String value;
  }
}
