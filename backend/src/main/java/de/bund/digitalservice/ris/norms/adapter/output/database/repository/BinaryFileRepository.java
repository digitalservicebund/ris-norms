package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.BinaryFileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link BinaryFileDto}
 * entities.
 */
@Repository
public interface BinaryFileRepository extends JpaRepository<BinaryFileDto, String> {}
