package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link NormManifestationDto} entities.
 */
@Repository
public interface NormManifestationRepository extends JpaRepository<NormManifestationDto, String> {
  /**
   * Finds the {@link NormManifestationDto}s by the norm expression ELI (European Legislation Identifier).
   * It takes the newest manifestation if multiple exist.
   *
   * @param expressionEli The ELI to search for.
   * @return A {@link Optional} containing the found {@link NormManifestationDto} or empty if not found.
   */
  Optional<NormManifestationDto> findFirstByExpressionEliOrderByManifestationEliDesc(
    final String expressionEli
  );

  /**
   * Finds the {@link NormManifestationDto}s by the norm manifestation ELI (European Legislation Identifier).
   *
   * @param manifestationEli The ELI to search for.
   * @return A {@link Optional} containing the found {@link NormManifestationDto} or empty if not found.
   */
  Optional<NormManifestationDto> findByManifestationEli(final String manifestationEli);

  /**
   * Finds a {@link NormManifestationDto} by its aktuelle-version-id.
   * It takes the newest manifestation if multiple norms with the same version-id exist.
   *
   * @param versionId The GUID to search for.
   * @return An {@link Optional} containing the found {@link NormManifestationDto} if exists, or empty if not found.
   */
  Optional<NormManifestationDto> findFirstByExpressionAktuelleVersionIdOrderByManifestationEli(
    final UUID versionId
  );

  /**
   * Retrieves the manifestation elis of all {@link NormManifestationDto} with a specific {@link NormPublishState}.
   *
   * @param normPublishState the publish state to filter the norms by (e.g., {@link NormPublishState#QUEUED_FOR_PUBLISH})
   * @return a {@link List} of manifestation elis of the {@link NormManifestationDto}s matching the specified publish state
   */
  @Query(
    "SELECT n.manifestationEli FROM NormManifestationDto n WHERE n.publishState = :publishState"
  )
  List<String> findManifestationElisByPublishState(
    @Param("publishState") final NormPublishState normPublishState
  );
}
