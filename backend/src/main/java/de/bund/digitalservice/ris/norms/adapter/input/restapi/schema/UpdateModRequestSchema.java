package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import de.bund.digitalservice.ris.norms.domain.entity.Href;
import jakarta.validation.constraints.NotNull;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Class representing the controller schema for updating an akn:mod element. */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UpdateModRequestSchema {

  @NotNull
  private String refersTo;

  @Nullable
  private String timeBoundaryEid;

  @NotNull
  private Href destinationHref;

  @Nullable
  private Href destinationUpTo;

  @NotNull
  private String newContent;
}
