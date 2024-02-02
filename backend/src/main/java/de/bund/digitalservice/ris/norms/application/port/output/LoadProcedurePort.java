package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Procedure} based on the ELI (European
 * Legislation Identifier). Implementations of this interface should provide functionality to load a
 * procedure using the specified command.
 */
public interface LoadProcedurePort {

  /**
   * Loads a {@link Procedure} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the procedure to be loaded.
   * @return An {@link Optional} containing the loaded {@link Procedure} if found, or empty if not
   *     found.
   */
  Optional<Procedure> loadProcedureByEli(final Command command);

  /**
   * A record representing the command for loading a procedure. The command includes the ELI
   * (European Legislation Identifier) to identify the procedure.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the procedure in the
   *     command.
   */
  record Command(String eli) {}
}
