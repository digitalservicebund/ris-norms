package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

/** Class representing the controller schema for updating multiple akn:mod elements. */
public class UpdateModsRequestSchema {

  // Private constructor to hide the implicit public one and prevent instantiation
  private UpdateModsRequestSchema() {}

  /**
   * Data for the update of a single akn:mod element.
   *
   * @param timeBoundaryEid EId of the new time boundary
   */
  public record ModUpdate(String timeBoundaryEid) {}
}
