package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;

/** Use case for getting {@link Proprietary} metadata from a {@link Norm}. */
public interface LoadProprietaryFromNormUseCase {
  /**
   * Retrieves the {@link Proprietary} metadata from a {@link Norm}.
   *
   * @param query Query used for identifying the element.
   * @return Proprietary metadata of the norm if it has any.
   */
  Proprietary loadProprietaryFromNorm(Query query);

  /**
   * Contains the parameters needed for loading proprietary metadata from a norm.
   *
   * @param eli The ELI used to identify the norm
   */
  record Query(ExpressionEli eli) {}
}
