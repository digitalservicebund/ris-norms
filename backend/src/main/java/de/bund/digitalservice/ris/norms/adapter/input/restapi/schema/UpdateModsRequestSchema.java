package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/** Class representing the controller schema for updating multiple akn:mod elements. */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class UpdateModsRequestSchema {
  /** Map between the eli's of the mods to update and the new data for them. */
  private Map<String, ModUpdate> mods;

  /** Data for the update of a single akn:mod element. */
  @Data
  @AllArgsConstructor
  @SuperBuilder(toBuilder = true)
  public static class ModUpdate {
    private String timeBoundaryEid;
  }
}
