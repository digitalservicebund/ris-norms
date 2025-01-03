package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.application.service.NormService;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;

/**
 * Interface representing a port for updating a {@link Norm}. Implementations of this interface
 * should provide functionality to update a norm using the specified command.
 */
public interface UpdateNormPort {
  /**
   * Updates a {@link Norm} based on the provided data in the command.
   * <p>
   * This method should not be used. Use {@link NormService#updateNorm(Norm)} instead to improve consistency of Norms.
   * @param command The command specifying the norm to be updated.
   * @return An {@link Optional} containing the {@link Norm} if found, or empty if not found.
   */
  Optional<Norm> updateNorm(final Command command);

  /**
   * A record representing the command for updating a norm.
   *
   * @param norm The updated norm.
   */
  record Command(Norm norm) {}
}
