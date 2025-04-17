package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import java.util.List;

/** Port interface for loading all {@link Verkuendung}s from the storage. */
public interface LoadAllVerkuendungenPort {
  /**
   * Loads all {@link Verkuendung}s available in the system.
   *
   * @return A {@link List} of {@link Verkuendung}, which may be empty if no Verkuendung is found.
   */
  List<Verkuendung> loadAllVerkuendungen();
}
