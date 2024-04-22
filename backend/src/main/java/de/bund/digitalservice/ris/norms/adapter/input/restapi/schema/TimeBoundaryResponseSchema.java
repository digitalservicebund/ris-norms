package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

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
public class TimeBoundaryResponseSchema {
  private LocalDate date;
  private String eid;
}
