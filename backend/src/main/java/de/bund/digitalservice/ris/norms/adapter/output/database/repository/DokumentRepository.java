package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link DokumentDto} entities.
 * This interface extends {@link JpaRepository} and focuses on operations related to {@link
 * DokumentDto}.
 */
@Repository
public interface DokumentRepository extends JpaRepository<DokumentDto, UUID> {}
