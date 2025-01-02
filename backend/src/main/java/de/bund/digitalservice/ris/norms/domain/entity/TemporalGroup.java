package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
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
   * Create a new temporal group within the given {@link TemporalData}.
   * @param temporalData the {@link TemporalData} within which the temporal group should be created.
   */
  public TemporalGroup(TemporalData temporalData) {
    this.node =
    NodeCreator.createElementWithEidAndGuid("akn:temporalGroup", temporalData.getNode());
  }

  /**
   * Returns the eId of the TemporalGroup as {@link String}.
   *
   * @return The eId of the temporal group
   */
  public String getEid() {
    return EId.fromMandatoryNode(getNode()).value();
  }

  /**
   * Returns a {@link TimeInterval} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the TimeInterval node as {@link TimeInterval}
   */
  public TimeInterval getTimeInterval() {
    return NodeParser
      .getNodeFromExpression("./timeInterval", node)
      .map(TimeInterval::new)
      .orElseThrow();
  }
}
