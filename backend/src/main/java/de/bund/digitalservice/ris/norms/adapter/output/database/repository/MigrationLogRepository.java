package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.MigrationLogDto;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link MigrationLogDto} entities.
 * This interface extends {@link JpaRepository} and focuses on operations related to {@link MigrationLogDto}.
 */
@Repository
public interface MigrationLogRepository extends JpaRepository<MigrationLogDto, UUID> {
  /**
   * Return the migration log entry for a given date. Using Optional as return type allows handling the case
   * where more than 1 log entry exists for the specified date, similar to applying a LIMIT 1 in a query.
   *
   * @param date the date to search for in the migration logs (formatted as YYYY-MM-DD)
   * @return an Optional containing the migration log entry for the specified date if found, or an empty Optional if not
   */
  @Query("SELECT ml FROM MigrationLogDto ml WHERE CAST(ml.createdAt AS date) = :date")
  Optional<MigrationLogDto> findByCreatedAt(final LocalDate date);
}
