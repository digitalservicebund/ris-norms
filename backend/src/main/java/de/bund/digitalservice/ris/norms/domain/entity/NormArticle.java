package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Optional;
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
public class NormArticle {
  private Optional<String> guid;

  private Optional<String> enumeration;

  private Optional<String> eid;

  private Optional<String> title;

  private Optional<Norm> targetLaw;

  private Optional<TargetLaw> targetLawZf0;
}
