package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link AnnouncementDto}
 * entities. This interface extends {@link JpaRepository} and focuses on operations related to
 * {@link AnnouncementDto}.
 */
@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementDto, UUID> {
  /**
   * Finds a {@link AnnouncementDto} by its norms ELI (European Legislation Identifier).
   *
   * @param eli The ELI to search for.
   * @return An {@link Optional} containing the found {@link AnnouncementDto} if exists, or empty if
   *     not found.
   */
  Optional<AnnouncementDto> findByNormDtoEli(final String eli);

  /**
   * Deletes an {@link AnnouncementDto} by its norms ELI (European Legislation Identifier).
   *
   * This method deletes the {@link AnnouncementDto} that is associated with the provided ELI.
   * If no matching entity is found, no action is taken.
   *
   * @param eli The ELI of the associated norm to search for and delete.
   */
  void deleteByNormDtoEli(final String eli);
}
