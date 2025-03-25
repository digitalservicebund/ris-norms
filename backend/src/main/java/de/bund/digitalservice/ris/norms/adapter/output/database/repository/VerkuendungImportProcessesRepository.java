package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with progress information about the
 * background processing of a new Verkündung.
 */
@Repository
public interface VerkuendungImportProcessesRepository
  extends JpaRepository<VerkuendungImportProcessDto, UUID> {}
