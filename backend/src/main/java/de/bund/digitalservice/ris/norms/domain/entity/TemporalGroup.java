package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing a akn:temporalGroup. */
@Getter
@AllArgsConstructor
public class TemporalGroup {

  private final Element element;

  /**
   * Creates a new akn:temporalGroup element and appends it to the given node.
   * @param parentNode the node under which a new {@link TemporalGroup} should be created.
   * @return the newly created {@link TemporalGroup}
   */
  public static TemporalGroup createAndAppend(Node parentNode) {
    return new TemporalGroup(
      NodeCreator.createElementWithEidAndGuid("akn:temporalGroup", parentNode)
    );
  }

  /**
   * Returns the eId of the TemporalGroup as {@link String}.
   *
   * @return The eId of the temporal group
   */
  public String getEid() {
    return EId.fromMandatoryNode(getElement()).value();
  }

  /**
   * Returns a {@link TimeInterval} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the TimeInterval node as {@link TimeInterval}
   */
  public TimeInterval getTimeInterval() {
    return NodeParser
      .getElementFromExpression("./timeInterval", element)
      .map(TimeInterval::new)
      .orElseThrow();
  }

  /**
   * Returns the {@link TimeInterval} instance for this akn:temporalGroup. If no time interval exists a new one is created.
   *
   * @return the TimeInterval
   */
  public TimeInterval getOrCreateTimeInterval() {
    try {
      return getTimeInterval();
    } catch (NoSuchElementException e) {
      return TimeInterval.createAndAppend(getElement());
    }
  }
}
