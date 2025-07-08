package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.application.service.NormService;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing a port for saving or updating a {@link Norm}.
 * <p>
 *   It is better to use {@link NormService#updateNorm(Norm)} as that method also ensures all preprocessing is done
 *   correctly and that the update writes to the working copy of an expression. That method is then using this port.
 * </p>
 */
public interface UpdateOrSaveNormPort {
  /**
   * Updates or saves a {@link Norm} based on the provided data in the options.
   *
   * @param options The options specifying the norm to be saved.
   * @return the saved {@link Norm}.
   */
  Norm updateOrSave(final Options options);

  /**
   * A record representing the options for updating or saving a norm.
   *
   * @param norm The norm to be saved/updated
   */
  record Options(Norm norm) {}
}
