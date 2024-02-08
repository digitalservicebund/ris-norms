package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.List;

/** Port interface for loading all procedures from the storage. */
public interface LoadAllProceduresPort {

  /**
   * Loads all {@link Procedure}s available in the system.
   *
   * @return A {@link List} of {@link Procedure}, which may be empty if no procedures are found.
   */
  List<Procedure> loadAllProcedures();
}
