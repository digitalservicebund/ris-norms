package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;

/**
 * Interface representing a port for saving or updating a {@link Verkuendung}. Implementations of
 * this interface should provide functionality to update an Verkuendung using the specified
 * command.
 */
public interface UpdateOrSaveVerkuendungPort {
  /**
   * Updates or saves a {@link Verkuendung} based on the provided data in the command.
   *
   * @param command The command specifying the Verkuendung to be saved.
   * @return the saved {@link Verkuendung}.
   */
  Verkuendung updateOrSaveVerkuendung(final Command command);

  /**
   * A record representing the command for updating or saving an Verkuendung.
   *
   * @param verkuendung The Verkuendung to be saved/updated
   */
  record Command(Verkuendung verkuendung) {}
}
