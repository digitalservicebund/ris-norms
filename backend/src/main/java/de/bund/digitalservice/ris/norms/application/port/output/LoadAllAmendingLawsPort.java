package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.List;

/** Port interface for loading all amending laws from the storage. */
public interface LoadAllAmendingLawsPort {

  /**
   * Loads all {@link AmendingLaw}s available in the system.
   *
   * @return A {@link List} of {@link AmendingLaw}, which may be empty if no amending laws are
   *     found.
   */
  List<AmendingLaw> loadAllAmendingLaws();
}
