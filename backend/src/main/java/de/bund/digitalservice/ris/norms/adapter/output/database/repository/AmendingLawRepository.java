package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link AmendingLawDTO}
 * entities. This interface extends {@link JpaRepository} and focuses on operations related to
 * {@link AmendingLawDTO}.
 */
@Repository
public interface AmendingLawRepository extends JpaRepository<AmendingLawDTO, UUID> {

  /**
   * Finds a {@link AmendingLawDTO} by its ELI (European Legislation Identifier).
   *
   * @param eli The ELI to search for.
   * @return An {@link Optional} containing the found {@link AmendingLawDTO} if exists, or empty if
   *     not found.
   */
  Optional<AmendingLawDTO> findByEli(final String eli);
}
