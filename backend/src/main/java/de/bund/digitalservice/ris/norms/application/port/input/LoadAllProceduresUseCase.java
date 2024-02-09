package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.List;

/**
 * Interface representing the use case for loading all {@link Procedure}s. Implementations of this
 * interface should provide functionality to load all procedures available in the system.
 */
public interface LoadAllProceduresUseCase {

  /**
   * Loads all {@link Procedure}s available in the system.
   *
   * @return A {@link List} of {@link Procedure} objects, which may be empty if no procedures are
   *     found.
   */
  List<Procedure> loadAllUnclosedProcedures();
}
