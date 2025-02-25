package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the database and managing {@link ReleaseDto} entities.
 * This interface extends {@link JpaRepository} and focuses on operations related to {@link
 * ReleaseDto}.
 */
@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseDto, UUID> {
  @Query(
    "SELECT r from ReleaseDto r WHERE elements(r.norms) = ANY (SELECT n from NormManifestationDto n where n.expressionEli = :normExpressionEli)"
  )
  List<ReleaseDto> findAllByNormExpressionEli(
    @Param("normExpressionEli") final String normExpressionEli
  );
}
