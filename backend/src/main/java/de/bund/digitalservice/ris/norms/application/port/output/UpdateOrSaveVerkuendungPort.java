package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;

/**
 * Interface representing a port for saving or updating a {@link Verkuendung}.
 */
public interface UpdateOrSaveVerkuendungPort {
  /**
   * Updates or saves a {@link Verkuendung} based on the provided data in the options.
   *
   * @param options The options specifying the Verkuendung to be saved.
   * @return the saved {@link Verkuendung}.
   */
  Verkuendung updateOrSaveVerkuendung(final Options options);

  /**
   * A record representing the options for updating or saving an Verkuendung.
   *
   * @param verkuendung The Verkuendung to be saved/updated
   */
  record Options(Verkuendung verkuendung) {}
}
