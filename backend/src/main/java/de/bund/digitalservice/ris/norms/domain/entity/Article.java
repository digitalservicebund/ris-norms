package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Class representing an article entity. This class is annotated with Lombok annotations for
 * generating getters, setters, constructors, and builder methods.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Data
public class Article {
  private String enumeration;

  private String eli;

  private String title;

  private TargetLaw targetLaw;
}
