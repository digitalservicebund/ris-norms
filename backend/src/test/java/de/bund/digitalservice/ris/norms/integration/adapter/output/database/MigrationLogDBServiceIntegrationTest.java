package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.MigrationLogDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.MigrationLogMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.MigrationLogRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.MigrationLogDBService;
import de.bund.digitalservice.ris.norms.application.port.output.CompleteMigrationLogPort;
import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MigrationLogDBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MigrationLogDBService migrationLogDBService;

  @Autowired
  private MigrationLogRepository migrationLogRepository;

  @AfterEach
  void cleanUp() {
    migrationLogRepository.deleteAll();
  }

  @Nested
  class loadsMigrationLog {

    @Test
    void itLoadLastMigrationLogWithTwoDates() {
      // Given
      var date1 = LocalDate.parse("2024-11-06");
      var migrationLog1 = MigrationLog.builder()
        .createdAt(date1.atStartOfDay().toInstant(ZoneOffset.UTC))
        .size(5)
        .completed(false)
        .build();

      var date2 = LocalDate.parse("2024-11-05");
      var migrationLog2 = MigrationLog.builder()
        .createdAt(date2.atStartOfDay().toInstant(ZoneOffset.UTC))
        .size(5)
        .completed(false)
        .build();

      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog1));
      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog2));

      // When
      final Optional<MigrationLog> migrationLogOptional =
        migrationLogDBService.loadLastMigrationLog();

      // Then
      assertThat(migrationLogOptional)
        .get()
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(migrationLog1);
    }

    @Test
    void itLoadLastMigrationLogWithSameDate() {
      // Given
      var date1 = LocalDate.parse("2024-11-06");
      var migrationLog1 = MigrationLog.builder()
        .createdAt(date1.atTime(9, 30).toInstant(ZoneOffset.UTC))
        .size(5)
        .completed(false)
        .build();

      var migrationLog2 = MigrationLog.builder()
        .createdAt(date1.atTime(11, 45).toInstant(ZoneOffset.UTC))
        .size(12)
        .completed(false)
        .build();

      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog1));
      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog2));

      // When
      final Optional<MigrationLog> migrationLogOptional =
        migrationLogDBService.loadLastMigrationLog();

      // Then
      assertThat(migrationLogOptional)
        .get()
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(migrationLog2);
    }
  }

  @Nested
  class updateMigrationLogCompleted {

    @Test
    void itSetsAnExistingMigrationLogToCompleted() {
      // Given
      var savedMigrationLog = migrationLogRepository.save(
        MigrationLogDto.builder()
          .size(5)
          .createdAt(Instant.parse("2025-03-03T15:00:00.0Z"))
          .completed(false)
          .build()
      );

      // When
      migrationLogDBService.completeMigrationLog(
        new CompleteMigrationLogPort.Options(savedMigrationLog.getId())
      );
      var updatedMigrationLog = migrationLogRepository.findAll().getFirst();

      // Then
      assertThat(migrationLogRepository.findAll()).hasSize(1);
      assertThat(updatedMigrationLog.isCompleted()).isTrue();
    }
  }
}
