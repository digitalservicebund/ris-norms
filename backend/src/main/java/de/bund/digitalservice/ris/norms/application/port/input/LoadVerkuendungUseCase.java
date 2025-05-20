package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.exception.VerkuendungNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;

/**
 * Interface representing the use case for loading a single {@link Verkuendung}s using its expression eli.
 */
public interface LoadVerkuendungUseCase {
  /**
   * Retrieves an Verkuendung based on the provided options.
   *
   * @param options The options containing the ELI (European Legislation Identifier) of the dokument.
   * @return The loaded {@link Verkuendung}
   * @throws VerkuendungNotFoundException if {@link Verkuendung} could not be found
   */
  Verkuendung loadVerkuendung(Options options);

  /**
   * A record representing the query for loading an Verkuendung.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the Verkuendung.
   */
  record Options(NormExpressionEli eli) {}
}
