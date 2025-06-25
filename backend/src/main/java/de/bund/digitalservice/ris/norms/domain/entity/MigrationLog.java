package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.Instant;
import java.util.UUID;
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

  private UUID id;

  private Integer xmlSize;

  private Integer binarySize;

  private Instant createdAt;

  private boolean completed;
}
