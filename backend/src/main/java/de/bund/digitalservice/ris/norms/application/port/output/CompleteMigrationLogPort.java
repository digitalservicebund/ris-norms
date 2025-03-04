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
   * @param command Specifies the entry to update
   */
  void completeMigrationLog(final Command command);

  /**
   * Command for setting a {@link MigrationLog} entry to "completed".
   *
   * @param id The ID of the migration log entry to be updated
   */
  record Command(UUID id) {}
}
