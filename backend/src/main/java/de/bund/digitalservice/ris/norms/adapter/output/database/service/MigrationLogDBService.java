package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.MigrationLogDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.MigrationLogMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.MigrationLogRepository;
import de.bund.digitalservice.ris.norms.application.port.output.CompleteMigrationLogPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadLastMigrationLogPort;
import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * DBService for {@link MigrationLog} / {@link MigrationLogDto}
 */
@Service
public class MigrationLogDBService
  extends DBService
  implements LoadLastMigrationLogPort, CompleteMigrationLogPort {

  private final MigrationLogRepository migrationLogRepository;

  public MigrationLogDBService(MigrationLogRepository migrationLogRepository) {
    this.migrationLogRepository = migrationLogRepository;
  }

  @Override
  public Optional<MigrationLog> loadLastMigrationLog() {
    return migrationLogRepository
      .findFirstByOrderByCreatedAtDesc()
      .map(MigrationLogMapper::mapToDomain);
  }

  @Override
  public void completeMigrationLog(CompleteMigrationLogPort.Command command) {
    migrationLogRepository.updateCompletedById(command.id(), true);
  }
}
