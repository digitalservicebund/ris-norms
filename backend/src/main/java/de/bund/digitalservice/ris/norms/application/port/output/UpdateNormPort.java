package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.application.service.NormService;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;

/**
 * Interface representing a port for updating a {@link Norm}.
 */
public interface UpdateNormPort {
  /**
   * Updates a {@link Norm} based on the provided data in the options.
   * <p>
   * This method should not be used. Use {@link NormService#updateNorm(Norm)} instead to improve consistency of Norms.
   * @param options The options specifying the norm to be updated.
   * @return An {@link Optional} containing the {@link Norm} if found, or empty if not found.
   */
  Optional<Norm> updateNorm(final Options options);

  /**
   * A record representing the options for updating a norm.
   *
   * @param norm The updated norm.
   */
  record Options(Norm norm) {}
}
