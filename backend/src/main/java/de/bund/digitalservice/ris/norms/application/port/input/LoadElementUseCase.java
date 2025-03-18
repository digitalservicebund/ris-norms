package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import org.w3c.dom.Node;

/** Use case for getting a single element as a {@link Node} from a dokument. */
public interface LoadElementUseCase {
  /**
   * Retrieves an element inside a norm as a plain XML node based on the provided query.
   *
   * @param query Query used for identifying the element
   * @return The element
   */
  Node loadElement(Query query);

  /**
   * Contains the parameters needed for loading an element from a dokument.
   *
   * @param eli The ELI used to identify the dokument
   * @param eid The eId of the element inside the dokument
   */
  record Query(DokumentExpressionEli eli, EId eid) {}
}
