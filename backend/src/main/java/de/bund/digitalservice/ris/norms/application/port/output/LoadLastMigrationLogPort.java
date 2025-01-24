package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;
import java.util.Optional;

/**
 * Interface representing a port for loading the last {@link MigrationLog}
 */
public interface LoadLastMigrationLogPort {
  /**
   * Loads the last {@link MigrationLog}
   *
   * @return A list of all {@link MigrationLog}s for that date
   */
  Optional<MigrationLog> loadLastMigrationLog();
}
