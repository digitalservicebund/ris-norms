package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import java.util.Optional;

/**
 * Interface representing a port for updating an {@link Verkuendung}.
 */
public interface UpdateVerkuendungPort {
  /**
   * Updates a {@link Verkuendung} based on the provided data in the options.
   *
   * @param options The options specifying the Verkuendung to be updated.
   * @return An {@link Optional} containing the {@link Verkuendung} if found, or empty if not
   *     found.
   */
  Optional<Verkuendung> updateVerkuendung(final Options options);

  /**
   * A record representing the options for updating an Verkuendung.
   *
   * @param verkuendung The updated Verkuendung.
   */
  record Options(Verkuendung verkuendung) {}
}
