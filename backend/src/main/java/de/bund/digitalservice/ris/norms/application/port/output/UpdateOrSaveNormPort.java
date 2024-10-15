package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing a port for saving or updating a {@link Norm}. Implementations of this
 * interface should provide functionality to update a norm using the specified command.
 */
public interface UpdateOrSaveNormPort {
  /**
   * Updates or saves a {@link Norm} based on the provided data in the command.
   *
   * @param command The command specifying the norm to be saved.
   * @return the saved {@link Norm}.
   */
  Norm updateOrSave(final Command command);

  /**
   * A record representing the command for updating or saving a norm.
   *
   * @param norm The norm to be saved/updated
   */
  record Command(Norm norm) {}
}
