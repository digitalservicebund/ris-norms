package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.service.ElementService;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.util.List;
import javax.annotation.Nullable;
import org.w3c.dom.Node;

/** Use case for getting a list of elements of certain types as {@link Node} from a {@link Norm}. */
public interface LoadElementsByTypeFromNormUseCase {
  /**
   * Load the list of elements of certain types from the norm. Elements can additionally be filtered
   * to include only those touched by a specific amending command.
   *
   * @return List of elements from the norm
   * @param query Query used for identifying the elements
   */
  List<Node> loadElementsByTypeFromNorm(Query query);

  /**
   * Contains the parameters needed for loading an element from a norm.
   *
   * @param eli The ELI used to identify the norm
   * @param elementType The types of the elements. While this is a list of strings, only certain
   *     values are allowed. Check {@link ElementService.ElementType} for the supported types.
   * @param amendedBy EId of an amending command. If provided, filters the list to include only
   *     elements touched by that amending command.
   */
  record Query(ExpressionEli eli, List<String> elementType, @Nullable ExpressionEli amendedBy) {
    public Query(ExpressionEli eli, List<String> elementType) {
      this(eli, elementType, null);
    }
  }

  /** Indicates that at least one of the requested types is not supported. */
  class UnsupportedElementTypeException
    extends IllegalArgumentException
    implements NormsAppException {

    public UnsupportedElementTypeException(String message) {
      super(message);
    }
  }
}
