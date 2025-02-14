package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.service.ElementService;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.util.List;
import org.w3c.dom.Node;

/** Use case for getting a list of elements of certain types as {@link Node} from a dokument. */
public interface LoadElementsByTypeUseCase {
  /**
   * Load the list of elements of certain types from the dokument.
   *
   * @return List of elements from the dokument
   * @param query Query used for identifying the elements
   */
  List<Node> loadElementsByType(Query query);

  /**
   * Contains the parameters needed for loading an element from a dokument.
   *
   * @param eli The ELI used to identify the dokument
   * @param elementType The types of the elements. While this is a list of strings, only certain
   *     values are allowed. Check {@link ElementService.ElementType} for the supported types.
   */
  record Query(DokumentExpressionEli eli, List<String> elementType) {}

  /** Indicates that at least one of the requested types is not supported. */
  class UnsupportedElementTypeException
    extends IllegalArgumentException
    implements NormsAppException {

    public UnsupportedElementTypeException(String message) {
      super(message);
    }
  }
}
