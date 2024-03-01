package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link AmendingLawDto}
 * entities. This interface extends {@link JpaRepository} and focuses on operations related to
 * {@link AmendingLawDto}.
 */
@Repository
public interface AmendingLawRepository extends JpaRepository<AmendingLawDto, UUID> {

  /**
   * Finds a {@link AmendingLawDto} by its ELI (European Legislation Identifier).
   *
   * @param eli The ELI to search for.
   * @return An {@link Optional} containing the found {@link AmendingLawDto} if exists, or empty if
   *     not found.
   */
  Optional<AmendingLawDto> findByEli(final String eli);

  /**
   * Retrieves a list of {@link AmendingLawDto} instances, sorted in descending order based on their
   * publication dates.
   *
   * @return A list of {@link AmendingLawDto} instances sorted by publication date in descending
   *     order.
   */
  List<AmendingLawDto> findAllByOrderByPublicationDateDesc();
}
