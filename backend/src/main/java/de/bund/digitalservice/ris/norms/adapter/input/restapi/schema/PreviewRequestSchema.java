package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.util.Set;
import lombok.Getter;

/** Schema for the request for rendering the preview of a regelungstext xml. */
@Getter
public class PreviewRequestSchema {

  /** The xml of the regelungstext. */
  private String regelungstext;

  /**
   * The xmls of regelungstexte which are referenced by the regelungstext (e.g. in passiveModifications) and should
   * not be loaded from the database.
   */
  private Set<String> customRegelungstexte = Set.of();
}
