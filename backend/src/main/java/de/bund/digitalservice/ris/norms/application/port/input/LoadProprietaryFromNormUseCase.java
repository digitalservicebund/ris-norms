package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import java.util.Optional;

/** Use case for getting {@link Proprietary} metadata from a {@link Norm}. */
public interface LoadProprietaryFromNormUseCase {
  /**
   * Retrieves the {@link Proprietary} metadata from a {@link Norm}.
   *
   * @param query Query used for identifying the element.
   * @return Proprietary metadata of the norm if it has any.
   * @throws NormNotFoundException if the norm doesn't exist
   */
  Optional<Proprietary> loadProprietaryFromNorm(Query query) throws NormNotFoundException;

  /**
   * Contains the parameters needed for loading proprietary metadata from a norm.
   *
   * @param eli The ELI used to identify the norm
   */
  record Query(String eli) {}
}
