package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.util.Set;
import lombok.Getter;

/** Schema for the request for rendering the preview of a norm xml. */
@Getter
public class PreviewRequestSchema {

  /** The xml of the norm. */
  private String norm;

  /**
   * The xmls of norms which are referenced by the norm (e.g. in passiveModifications) and should
   * not be loaded from the database.
   */
  private Set<String> customNorms = Set.of();
}
