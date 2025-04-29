package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Response schema for Zeitgrenzen
 */
@Getter
@SuperBuilder(toBuilder = true)
public class ZeitgrenzeResponseSchema {

  private String id;

  private LocalDate date;

  private String art;

  private boolean inUse;
}
