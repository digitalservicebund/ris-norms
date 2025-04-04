package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Class representing an article as used by the controller producing json. This class is annotated
 * with Lombok annotations for generating getters, setters, constructors, and builder methods.
 */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ArticleResponseSchema {

  private String enumeration;

  private String eid;

  private String title;
}
