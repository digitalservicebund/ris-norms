package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.Optional;

/**
 * Interface representing the use case for loading a {@link Procedure}. Implementations of this
 * interface should provide functionality to load a procedure based on a given query.
 */
public interface LoadProcedureUseCase {

  /**
   * Loads a {@link Procedure} based on the provided query.
   *
   * @param query The query specifying the procedure to be loaded.
   * @return An {@link Optional} containing the loaded {@link Procedure} if found, or empty if not
   *     found.
   */
  Optional<Procedure> loadProcedure(Query query);

  /**
   * A record representing the query for loading a procedure. The query includes the ELI (European
   * Legislation Identifier) to identify the procedure.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the procedure in the
   *     query.
   */
  record Query(String eli) {}
}
