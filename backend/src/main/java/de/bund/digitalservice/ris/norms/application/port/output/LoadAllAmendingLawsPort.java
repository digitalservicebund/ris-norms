package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLawWithArticles;
import java.util.List;

/** Port interface for loading all procedures from the storage. */
public interface LoadAllAmendingLawsPort {

  /**
   * Loads all {@link AmendingLawWithArticles}s available in the system.
   *
   * @return A {@link List} of {@link AmendingLawWithArticles}, which may be empty if no procedures
   *     are found.
   */
  List<AmendingLaw> loadAllAmendingLaws();
}
