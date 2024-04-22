package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Class representing a time boundary. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TimeBoundary {
  private LocalDate date;
  private String eid;
}
