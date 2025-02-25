package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link DokumentDto} entities.
 * This interface extends {@link JpaRepository} and focuses on operations related to {@link
 * DokumentDto}.
 */
@Repository
public interface DokumentRepository extends JpaRepository<DokumentDto, UUID> {
  /**
   * Finds a {@link DokumentDto} by its expression ELI (European Legislation Identifier).
   * It takes the newest manifestation if multiple exist.
   *
   * @param expressionEli The ELI to search for.
   * @return An {@link Optional} containing the found {@link DokumentDto} if exists, or empty if not found.
   */
  Optional<DokumentDto> findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
    final String expressionEli
  );

  /**
   * Finds a {@link DokumentDto} by its manifestation ELI (European Legislation Identifier).   *
   * @param manifestationEli The ELI to search for.
   * @return An {@link Optional} containing the found {@link DokumentDto} if exists, or empty if not found.
   */
  Optional<DokumentDto> findByEliDokumentManifestation(final String manifestationEli);
}
