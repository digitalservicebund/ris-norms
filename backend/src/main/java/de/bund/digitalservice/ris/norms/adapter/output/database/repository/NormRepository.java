package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormDto;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link NormDto} entities.
 * This interface extends {@link JpaRepository} and focuses on operations related to {@link
 * NormDto}.
 */
@Repository
public interface NormRepository extends JpaRepository<NormDto, UUID> {
  /**
   * Finds a {@link NormDto} by its expression ELI (European Legislation Identifier).
   * It takes the newest manifestation if multiple exist.
   *
   * @param expressionEli The ELI to search for.
   * @return An {@link Optional} containing the found {@link NormDto} if exists, or empty if not found.
   */
  Optional<NormDto> findFirstByEliExpressionOrderByEliManifestationDesc(final String expressionEli);

  /**
   * Finds a {@link NormDto} by its manifestation ELI (European Legislation Identifier).
   *
   * @param manifestationEli The ELI to search for.
   * @return An {@link Optional} containing the found {@link NormDto} if exists, or empty if not found.
   */
  Optional<NormDto> findByEliManifestation(final String manifestationEli);

  /**
   * Finds a {@link NormDto} by its GUID.
   * It takes the newest manifestation if multiple norms with the same guid exist.
   *
   * @param guid The GUID to search for.
   * @return An {@link Optional} containing the found {@link NormDto} if exists, or empty if not found.
   */
  Optional<NormDto> findFirstByGuidOrderByEliManifestation(final UUID guid);

  /**
   * Deletes all {@link NormDto} with the given GUID
   *
   * @param guid The GUID to search for.
   */
  @Transactional
  void deleteAllByGuid(final UUID guid);

  /**
   * Deletes a {@link NormDto} by its manifestation ELI (European Legislation Identifier) if it is in the given publish state.
   *
   * @param manifestationEli The ELI to search for.
   * @param publishState The publishState to search for.
   */
  void deleteByEliManifestationAndPublishState(
    final String manifestationEli,
    final NormPublishState publishState
  );

  /**
   * Deletes all {@link NormDto} of the given work ELI that are in the given publish state.
   * @param workEli The ELI to search for.
   * @param publishState The publishState to search for.
   */
  void deleteAllByEliWorkAndPublishState(final String workEli, final NormPublishState publishState);

  /**
   * Retrieves the ids of all {@link NormDto} with a specific {@link NormPublishState}.
   *
   * @param normPublishState the publish state to filter the norms by (e.g., {@link NormPublishState#QUEUED_FOR_PUBLISH})
   * @return a {@link List} of {@link UUID}s of the {@link NormDto}s matching the specified publish state
   */

  @Query("SELECT n.id FROM NormDto n WHERE n.publishState = :publishState")
  List<UUID> findNormIdsByPublishState(
    @Param("publishState") final NormPublishState normPublishState
  );
}
