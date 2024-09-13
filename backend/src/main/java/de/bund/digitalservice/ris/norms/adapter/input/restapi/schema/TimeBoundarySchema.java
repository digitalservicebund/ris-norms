package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Class representing a time boundary (TimeBoundary). This class is annotated with Lombok
 * annotations for generating getters, setters, constructors, and builder methods.
 */
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class TimeBoundarySchema {

  @NotNull(message = "Date must not be null")
  private LocalDate date;

  private String eventRefEid;

  private String temporalGroupEid;
}
