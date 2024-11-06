package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Interface representing a port for loading a  {@link MigrationLog}s by a specific date.
 */
public interface LoadMigrationLogByDatePort {
  /**
   * Loads a {@link MigrationLog}s based on the specified date.
   *
   * @param command The command specifying the date
   * @return A list of all {@link MigrationLog}s for that date
   */
  Optional<MigrationLog> loadMigrationLogByDate(final Command command);

  /**
   * A record representing the command for loading migration logs by date.
   *
   * @param date the date for which to load migration logs
   */
  record Command(LocalDate date) {}
}
