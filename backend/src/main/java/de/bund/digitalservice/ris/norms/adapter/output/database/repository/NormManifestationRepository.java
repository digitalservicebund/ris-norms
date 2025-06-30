package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
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
   * Finds the {@link NormManifestationDto}s by the norm work ELI (European Legislation Identifier).
   * It takes the newest manifestation of the newest expression if multiple exist.
   * THIS MIGHT NOT BE THE EXPRESSION THAT IS CURRENTLY IN FORCE.
   *
   * @param workEli The ELI to search for.
   * @return A {@link Optional} containing the found {@link NormManifestationDto} or empty if not found.
   */
  Optional<NormManifestationDto> findFirstByWorkEliOrderByManifestationEliDesc(
    final String workEli
  );

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

  /**
   * Retrieves the expression elis of all expressions of the norm.
   * @param normWorkEli the eli of the norm
   * @return a {@link List} of expression elis
   */
  @Query(
    "SELECT DISTINCT n.expressionEli FROM NormManifestationDto n WHERE n.workEli = :normWorkEli"
  )
  List<String> findExpressionElisByWorkEli(@Param("normWorkEli") final String normWorkEli);

  /**
   * Retrieves all manifestations of a given expression
   * @param expressionEli - the expression eli
   * @return  a {@link List} of manifestations
   */
  List<NormManifestationDto> findAllByExpressionEli(final String expressionEli);

  @Modifying
  @Query(
    "UPDATE NormManifestationDto n SET n.publishState = :publishState WHERE n.manifestationEli = :manifestationEli"
  )
  void updatePublishStateByManifestationEli(
    @Param("manifestationEli") String manifestationEli,
    @Param("publishState") NormPublishState publishState
  );

  @NativeQuery(
    value = """
    SELECT
        eli_norm_work,
       (xpath('//*[local-name()="longTitle"]/*/*[local-name()="docTitle"]/text()', xml))[1]::text as title
    FROM (
        SELECT DISTINCT ON (n.eli_norm_work) n.eli_norm_work, d.xml
          FROM norm_manifestation n
              -- To find the title we need the xml for the regelungstext. Every norm must have one.
              LEFT OUTER JOIN dokumente d on d.eli_dokument_manifestation = concat(n.eli_norm_manifestation, '/regelungstext-verkuendung-1.xml')
          ORDER BY
              n.eli_norm_work ASC, -- Order the norms from oldest to newest, for selecting the correct rows using the pagination
              n.eli_norm_manifestation DESC -- Get the latest manifestation of the latest expression for each work
          -- Place the offset and limit into the subquery so the xpath for getting the title is not calculated on the skipped rows
          OFFSET :#{#pageable.getOffset()} ROWS
          FETCH NEXT :#{#pageable.getPageSize()} ROWS ONLY
    ) tmp
    ORDER BY eli_norm_work ASC -- Order the norms from oldest to newest, just because the subquery is ordered does not garantee that the outer query than also is ordered
    """,
    countQuery = "SELECT count(DISTINCT eli_norm_work) FROM norm_manifestation"
  )
  Page<Map<String, Object>> findDistinctOnWorkEliByOrderByWorkEliAsc(
    @Param("pageable") Pageable pageable
  );
}
