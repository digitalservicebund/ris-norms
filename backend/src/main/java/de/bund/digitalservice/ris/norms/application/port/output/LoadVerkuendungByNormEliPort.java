package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Verkuendung} based on the ELI (European
 * Legislation Identifier) of its {@link de.bund.digitalservice.ris.norms.domain.entity.Norm}.
 * Implementations of this interface should provide functionality to load a Verkuendung using the
 * specified command.
 */
public interface LoadVerkuendungByNormEliPort {
  /**
   * Loads a {@link Verkuendung} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the norm of the Verkuendung to be
   *     loaded.
   * @return An {@link Optional} containing the loaded {@link Verkuendung} if found, or empty if
   *     not found.
   */
  Optional<Verkuendung> loadVerkuendungByNormEli(final Command command);

  /**
   * A record representing the command for loading an Verkuendung. The command includes the ELI
   * (European Legislation Identifier) to identify the Verkuendung.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the Verkuendung in the
   *     command.
   */
  record Command(NormExpressionEli eli) {}
}
