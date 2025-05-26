package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Norm} based on the ELI (European Legislation
 * Identifier).
 */
public interface LoadNormPort {
  /**
   * Loads a {@link Norm} based on the provided ELI specified in the options.
   * Loading using a {@link NormWorkEli} returns the latest expression and not necessarily the expression that is
   * currently in force.
   *
   * @param options The options specifying the ELI to identify the norm to be loaded.
   * @return An {@link Optional} containing the loaded {@link Norm} if found, or empty if not found.
   */
  Optional<Norm> loadNorm(final Options options);

  /**
   * A record representing the options for loading a norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm.
   */
  record Options(NormEli eli) {}
}
