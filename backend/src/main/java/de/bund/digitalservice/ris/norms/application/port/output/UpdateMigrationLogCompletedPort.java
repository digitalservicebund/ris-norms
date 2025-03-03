package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;
import java.util.UUID;

/**
 * Port for updating the completion state of a migration log entry.
 */
public interface UpdateMigrationLogCompletedPort {
  /**
   * Updates the completion state of an existing {@link MigrationLog} entry.
   *
   * @param command Specifies the entry to update and the new value
   * @return Number of affected entries
   */
  int updateMigrationLogCompleted(final Command command);

  /**
   * Command for updating the completion state of a migration log entry.
   *
   * @param id The ID of the migration log entry to be updated
   * @param completed The new completion state of the migration log entry
   */
  record Command(UUID id, boolean completed) {}
}
