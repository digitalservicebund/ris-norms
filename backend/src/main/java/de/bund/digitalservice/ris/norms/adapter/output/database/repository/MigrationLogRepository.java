package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.MigrationLogDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link MigrationLogDto} entities.
 * This interface extends {@link JpaRepository} and focuses on operations related to {@link MigrationLogDto}.
 */
@Repository
public interface MigrationLogRepository extends JpaRepository<MigrationLogDto, UUID> {
  Optional<MigrationLogDto> findFirstByOrderByCreatedAtDesc();
}
