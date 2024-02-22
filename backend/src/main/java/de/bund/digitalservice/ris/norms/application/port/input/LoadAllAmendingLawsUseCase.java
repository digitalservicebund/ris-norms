package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.List;

/**
 * Interface representing the use case for loading all {@link AmendingLaw}s. Implementations of this
 * interface should provide functionality to load all procedures available in the system.
 */
public interface LoadAllAmendingLawsUseCase {

  /**
   * Loads all {@link AmendingLaw}s available in the system.
   *
   * @return A {@link List} of {@link AmendingLaw} objects, which may be empty if no procedures are
   *     found.
   */
  List<AmendingLaw> loadAllAmendingLaws();
}
