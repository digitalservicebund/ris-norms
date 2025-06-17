package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.BinaryFileDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link BinaryFileDto}
 * entities.
 */
@Repository
public interface BinaryFileRepository extends JpaRepository<BinaryFileDto, String> {
  /**
   * Finds a {@link BinaryFileDto} by its manifestation ELI (European Legislation Identifier).   *
   * @param manifestationEli The ELI to search for.
   * @return An {@link Optional} containing the found {@link BinaryFileDto} if exists, or empty if not found.
   */
  Optional<BinaryFileDto> findByEliDokumentManifestation(final String manifestationEli);

  List<BinaryFileDto> findAllByEliNormManifestation(final String eliNormManifestation);
}
