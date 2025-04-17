package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link VerkuendungDto}
 * entities. This interface extends {@link JpaRepository} and focuses on operations related to
 * {@link VerkuendungDto}.
 */
@Repository
public interface VerkuendungRepository extends JpaRepository<VerkuendungDto, UUID> {
  /**
   * Finds a {@link VerkuendungDto} by its norms ELI (European Legislation Identifier).
   *
   * @param eli The ELI to search for.
   * @return An {@link Optional} containing the found {@link VerkuendungDto} if exists, or empty if
   *     not found.
   */
  Optional<VerkuendungDto> findByEliNormExpression(final String eli);

  /**
   * Deletes an {@link VerkuendungDto} by its norms ELI (European Legislation Identifier).
   *
   * This method deletes the {@link VerkuendungDto} that is associated with the provided ELI.
   * If no matching entity is found, no action is taken.
   *
   * @param eli The ELI of the associated norm to search for and delete.
   */
  void deleteByEliNormExpression(final String eli);
}
