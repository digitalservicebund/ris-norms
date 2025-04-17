package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import java.util.Optional;

/**
 * Interface representing a port for updating an {@link Verkuendung}. Implementations of this
 * interface should provide functionality to update an Verkuendung using the specified command.
 */
public interface UpdateVerkuendungPort {
  /**
   * Updates a {@link Verkuendung} based on the provided data in the command.
   *
   * @param command The command specifying the Verkuendung to be updated.
   * @return An {@link Optional} containing the {@link Verkuendung} if found, or empty if not
   *     found.
   */
  Optional<Verkuendung> updateVerkuendung(final Command command);

  /**
   * A record representing the command for updating an Verkuendung.
   *
   * @param verkuendung The updated Verkuendung.
   */
  record Command(Verkuendung verkuendung) {}
}
