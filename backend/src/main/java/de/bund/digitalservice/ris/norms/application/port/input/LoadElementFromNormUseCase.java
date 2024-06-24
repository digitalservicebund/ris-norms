package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.common.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Optional;
import org.w3c.dom.Node;

/** Use case for getting a single element as a {@link Node} from a {@link Norm}. */
public interface LoadElementFromNormUseCase {
  /**
   * Retrieves an element inside a norm as a plain XML node based on the provided query.
   *
   * @param query Query used for identifying the element
   * @return An {@link Optional} containing the element if found, or empty if either the norm or the
   *     element don't exist.
   */
  Optional<Node> loadElementFromNorm(Query query) throws NormNotFoundException;

  /**
   * Contains the parameters needed for loading an element from a norm.
   *
   * @param eli The ELI used to identify the norm
   * @param eid The eId of the element inside the norm
   */
  record Query(String eli, String eid) {}
}
