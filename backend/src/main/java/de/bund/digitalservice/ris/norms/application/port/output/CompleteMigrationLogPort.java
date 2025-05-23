package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;
import java.util.UUID;

/**
 * Port for setting a migration log entry to "completed".
 */
public interface CompleteMigrationLogPort {
  /**
   * Sets an existing {@link MigrationLog} entry to "completed".
   *
   * @param options Specifies the entry to update
   */
  void completeMigrationLog(final Options options);

  /**
   * Options for setting a {@link MigrationLog} entry to "completed".
   *
   * @param id The ID of the migration log entry to be updated
   */
  record Options(UUID id) {}
}
