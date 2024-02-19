package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ProcedureDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link ProcedureDTO}
 * entities. This interface extends {@link JpaRepository} and focuses on operations related to
 * {@link ProcedureDTO}.
 */
@Repository
public interface ProcedureRepository extends JpaRepository<ProcedureDTO, UUID> {

  /**
   * Finds a {@link ProcedureDTO} by its ELI (European Legislation Identifier).
   *
   * @param eli The ELI to search for.
   * @return An {@link Optional} containing the found {@link ProcedureDTO} if exists, or empty if
   *     not found.
   */
  Optional<ProcedureDTO> findByEli(final String eli);
}
