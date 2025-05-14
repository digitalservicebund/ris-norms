package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormEli;
import java.util.UUID;

/**
 * Interface representing the use case for loading a {@link Norm}. Implementations of this interface
 * should provide functionality to load a norm based on a given query.
 */
public interface LoadNormUseCase {
  /**
   * Retrieves a norm based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) or GUID of the norm.
   * @return The loaded {@link Norm}
   * @throws NormNotFoundException if {@link Norm} could not be found
   */
  Norm loadNorm(Query query);

  /**
   * The query for loading a norm.
   */
  sealed interface Query permits EliQuery, GuidQuery {}

  /**
   * The query for loading a norm based on an eli.
   * @param eli the eli to identify the norm
   */
  record EliQuery(NormEli eli) implements Query {}

  /**
   * The query for loading a norm based on a guid.
   * @param guid the guid to identify the norm expression
   */
  record GuidQuery(UUID guid) implements Query {}
}
