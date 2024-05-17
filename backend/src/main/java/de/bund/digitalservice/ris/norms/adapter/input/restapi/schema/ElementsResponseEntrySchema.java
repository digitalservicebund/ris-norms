package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Class representing a list entry as used by the controller producing json. This class is annotated
 * with Lombok annotations for generating getters, setters, constructors, and builder methods.
 */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ElementsResponseEntrySchema {
  private String title;
  private String eid;
  private String type;
}
