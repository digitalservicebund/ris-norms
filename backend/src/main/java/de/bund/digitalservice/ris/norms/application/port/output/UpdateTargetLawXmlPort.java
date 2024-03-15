package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;

/**
 * Interface representing a port for updating the xml representation of a {@link TargetLaw} based on
 * the ELI (European Legislation Identifier). Implementations of this interface should provide
 * functionality to load a target law using the specified command.
 */
public interface UpdateTargetLawXmlPort {

  /**
   * Updates the xml representation of a {@link TargetLaw} based on the provided ELI specified in
   * the command.
   *
   * @param command The command specifying the ELI to identify the target law to be loaded.
   * @return An {@link Optional} containing the loaded xml representation of a {@link TargetLaw} if
   *     found, or empty if not found.
   */
  Optional<String> updateTargetLawXmlByEli(final Command command);

  /**
   * A record representing the command for loading the xml representation of a target law. The
   * command includes the ELI (European Legislation Identifier) to identify the target law.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the target law in the
   *     command.
   * @param updatedXml The updated xml representation of the target law
   */
  record Command(String eli, String updatedXml) {}
}
