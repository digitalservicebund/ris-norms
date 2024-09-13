package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import jakarta.validation.constraints.NotNull;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/** Class representing the controller schema for updating an akn:mod element. */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class UpdateModRequestSchema {

  @NotNull
  private String refersTo;

  @Nullable
  private String timeBoundaryEid;

  @NotNull
  private String destinationHref;

  @Nullable
  private String destinationUpTo;

  @NotNull
  private String newContent;
}
