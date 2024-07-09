package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a akn:temporalGroup. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TemporalGroup {
  private final Node node;

  /**
   * Returns the eId of the TemporalGroup as {@link String}.
   *
   * @return The eId of the temporal group
   */
  public Optional<String> getEid() {
    return EId.fromNode(getNode()).map(EId::value);
  }

  /**
   * Returns a {@link TimeInterval} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the TimeInterval node as {@link TimeInterval}
   */
  public TimeInterval getTimeInterval() {
    return NodeParser.getNodeFromExpression("./timeInterval", node)
        .map(TimeInterval::new)
        .orElseThrow();
  }
}
