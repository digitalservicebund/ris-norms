package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;

/**
 * Interface representing the use case for releasing an {@link AmendingLaw} and the corresponding
 * {@link TargetLaw}.
 */
public interface ReleaseAmendingLawAndAllRelatedTargetLawsUseCase {

  /**
   * Releases an {@link AmendingLaw} and the corresponding {@link TargetLaw} based on the provided
   * query.
   *
   * @param query The query specifying the amending law to be loaded.
   * @return A {@link java.util.List} containing the elis of the corresponding {@link TargetLaw}.
   */
  Optional<AmendingLaw> releaseAmendingLaw(Query query);

  /**
   * A record representing the query for releasing an amending law. The query includes the ELI
   * (European Legislation Identifier) to identify the amending law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the amending law in the
   *     query.
   */
  record Query(String eli) {}
}
