package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Represents a log entry for migrations performed on norms.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Data
public class MigrationLog {

  private Integer size;

  private Instant createdAt;
}
