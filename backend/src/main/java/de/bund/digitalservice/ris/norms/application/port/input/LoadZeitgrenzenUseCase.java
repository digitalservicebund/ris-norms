package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;

/**
 * Interface representing the use case for loading a list of {@link Zeitgrenze}.
 */
public interface LoadZeitgrenzenUseCase {
  /**
   * Retrieves a list of time boundaries from a {@link Norm} based on the provided options.
   *
   * @param options The options containing the ELI (European Legislation Identifier) of the {@link Norm}.
   * @return the list of {@link Zeitgrenze}
   */
  List<Zeitgrenze> loadZeitgrenzen(Options options);

  /**
   * A record representing the parameters needed to query time boundaries.
   *
   * @param eli The ELI used to identify the {@link Norm} in the query.
   */
  record Options(NormExpressionEli eli) {}
}
