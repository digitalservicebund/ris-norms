package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.TargetLawDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link TargetLawDto}
 * entities. This interface extends {@link JpaRepository} and focuses on operations related to
 * {@link TargetLawDto}.
 */
@Repository
public interface TargetLawRepository extends JpaRepository<TargetLawDto, UUID> {

  /**
   * Finds a {@link TargetLawDto} by its ELI (European Legislation Identifier).
   *
   * @param eli The ELI to search for.
   * @return An {@link Optional} containing the found {@link TargetLawDto} if exists, or empty if
   *     not found.
   */
  Optional<TargetLawDto> findByEli(final String eli);

  @Query(
      "SELECT t FROM AmendingLawDto a JOIN a.articleDtos ar JOIN ar.targetLawZf0Dto t  WHERE a.eli = :amendingLawEli")
  List<TargetLawDto> findByAmendingLawEli(@Param("amendingLawEli") String amendingLawEli);
}
