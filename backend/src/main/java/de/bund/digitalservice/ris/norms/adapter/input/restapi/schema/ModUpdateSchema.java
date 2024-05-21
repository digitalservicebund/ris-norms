package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Class representing the controller schema of an amending command. All properties required but the
 * time boundary because the flow for updating a mod is at the end bound to also applying the time
 * machine.
 */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ModUpdateSchema {

  @NotNull private String refersTo;
  private String timeBoundaryEid;
  @NotNull private String destinationHref;
  @NotNull private String oldText;
  @NotNull private String newText;
}
