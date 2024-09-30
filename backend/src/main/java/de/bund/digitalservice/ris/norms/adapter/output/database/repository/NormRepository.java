package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormDto;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
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
   * Deletes a {@link NormDto} by its manifestation ELI (European Legislation Identifier).
   *
   * @param manifestationEli The ELI to search for.
   */
  void deleteByEliManifestation(final String manifestationEli);
}
