package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.List;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link TargetLaw} based on the ELI (European
 * Legislation Identifier). Implementations of this interface should provide functionality to load a
 * target law using the specified command.
 */
public interface LoadTargetLawsForAmendingLawPort {

  /**
   * Loads a {@link TargetLaw} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the target law to be loaded.
   * @return An {@link Optional} containing the loaded {@link TargetLaw} if found, or empty if not
   *     found.
   */
  List<TargetLaw> loadTargetsLawByAmendingLawEli(final Command command);

  /**
   * A record representing the command for loading a target law. The command includes the ELI
   * (European Legislation Identifier) to identify the amending law.
   *
   * @param amendingLawEli The ELI (European Legislation Identifier) used to identify the target law
   *     in the command.
   */
  record Command(String amendingLawEli) {}
}
