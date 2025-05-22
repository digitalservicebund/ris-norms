package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Verkuendung} based on the ELI (European
 * Legislation Identifier) of its {@link de.bund.digitalservice.ris.norms.domain.entity.Norm}.
 */
public interface LoadVerkuendungByNormEliPort {
  /**
   * Loads a {@link Verkuendung} based on the provided ELI specified in the options.
   *
   * @param options The options specifying the ELI to identify the norm of the Verkuendung to be
   *     loaded.
   * @return An {@link Optional} containing the loaded {@link Verkuendung} if found, or empty if
   *     not found.
   */
  Optional<Verkuendung> loadVerkuendungByNormEli(final Options options);

  /**
   * A record representing the options for loading an Verkuendung.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the Verkuendung.
   */
  record Options(NormExpressionEli eli) {}
}
