package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/** Class representing the controller schema for updating an akn:mod element. */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class UpdateModRequestSchema {

  @NotNull private String refersTo;
  private String timeBoundaryEid;
  @NotNull private String destinationHref;
  @NotNull private String oldText;
  @NotNull private String newText;
}
