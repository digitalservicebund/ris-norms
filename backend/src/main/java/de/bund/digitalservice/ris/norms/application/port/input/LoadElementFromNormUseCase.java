package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import org.w3c.dom.Node;

/** Use case for getting a single element as a {@link Node} from a {@link Norm}. */
public interface LoadElementFromNormUseCase {
  /**
   * Retrieves an element inside a norm as a plain XML node based on the provided query.
   *
   * @param query Query used for identifying the element
   * @return The element
   */
  Node loadElementFromNorm(Query query);

  /**
   * Contains the parameters needed for loading an element from a norm.
   *
   * @param eli The ELI used to identify the norm
   * @param eid The eId of the element inside the norm
   */
  record Query(String eli, String eid) {}
}
