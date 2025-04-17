package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.exception.VerkuendungNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;

/**
 * Interface representing the use case for loading a single {@link Verkuendung}s using its expression eli.
 */
public interface LoadVerkuendungUseCase {
  /**
   * Retrieves an Verkuendung based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the dokument.
   * @return The loaded {@link Verkuendung}
   * @throws VerkuendungNotFoundException if {@link Verkuendung} could not be found
   */
  Verkuendung loadVerkuendung(Query query);

  /**
   * A record representing the query for loading an Verkuendung. The query includes the ELI (European
   * Legislation Identifier) to identify the dokument.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the Verkuendung in the query.
   */
  record Query(NormExpressionEli eli) {}
}
