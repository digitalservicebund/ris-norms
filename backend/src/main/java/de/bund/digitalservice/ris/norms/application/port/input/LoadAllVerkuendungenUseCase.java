package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import java.util.List;

/**
 * Interface representing the use case for loading all {@link Verkuendung}s. Implementations of
 * this interface should provide functionality to load all {@link Verkuendung}s available in the
 * system.
 */
public interface LoadAllVerkuendungenUseCase {
  /**
   * Loads all {@link Verkuendung}s available in the system.
   *
   * @return A {@link List} of {@link Verkuendung} objects, which may be empty if no {@link
   *     Verkuendung}s are found.
   */
  List<Verkuendung> loadAllVerkuendungen();
}
