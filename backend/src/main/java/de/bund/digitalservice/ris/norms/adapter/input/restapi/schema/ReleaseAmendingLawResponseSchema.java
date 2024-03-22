package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Class representing the eli of a target law as used by the controller producing json. This class
 * is annotated with Lombok annotations for generating getters, setters, constructors, and builder
 * methods.
 */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ReleaseAmendingLawResponseSchema {
  private Instant releaseAt;
  private String eliAmendingLaw;
  private List<EliOfLaw> eliOfLawTargetLawsZF0;
}
