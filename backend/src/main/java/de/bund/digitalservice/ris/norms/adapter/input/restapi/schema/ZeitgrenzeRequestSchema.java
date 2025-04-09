package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Request schema for Zeitgrenzen
 */
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ZeitgrenzeRequestSchema {

  @NotNull(message = "Date must not be null")
  private LocalDate date;

  @NotNull(message = "Art must not be null")
  private Art art;

  /**
   * The possible values for Art
   */
  public enum Art {
    INKRAFT,
    AUSSERKRAFT,
  }
}
