package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentEli;

/**
 * Interface representing the use case for loading a {@link Regelungstext}.
 */
public interface LoadRegelungstextUseCase {
  /**
   * Retrieves a regelungstext based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the dokument.
   * @return The loaded {@link Regelungstext}
   * @throws RegelungstextNotFoundException if {@link Regelungstext} could not be found
   */
  Regelungstext loadRegelungstext(Query query);

  /**
   * A record representing the query for loading a regelungstext. The query includes the ELI (European
   * Legislation Identifier) to identify the dokument.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the regelungstext in the query.
   */
  record Query(DokumentEli eli) {}
}
