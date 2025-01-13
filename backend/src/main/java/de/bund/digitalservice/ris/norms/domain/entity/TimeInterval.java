package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing a akn:timeInterval. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TimeInterval {

  private final Node node;

  /**
   * Creates a new akn:timeInterval element and appends it to the given node.
   * @param parentNode the node under which a new {@link TimeInterval} should be created.
   * @return the newly created {@link TimeInterval}
   */
  public static TimeInterval createAndAppend(Node parentNode) {
    return new TimeInterval(
      NodeCreator.createElementWithEidAndGuid("akn:timeInterval", parentNode)
    );
  }

  /**
   * Set the start attribute of the akn:timeInterval
   * @param start the href to the akn:eventRef that includes the start date
   */
  public void setStart(Href start) {
    ((Element) node).setAttribute("start", start.toString());
  }

  /**
   * Set the refersTo attribute of the akn:timeInterval
   * @param refersTo the value of the refersTo attribute
   */
  public void setRefersTo(String refersTo) {
    ((Element) node).setAttribute("refersTo", refersTo);
  }

  /**
   * Returns the eId of the event ref of the temporal group
   *
   * @return The eId of the event ref of this temporal group
   */
  public Optional<String> getEventRefEId() {
    return NodeParser
      .getValueFromExpression("./@start", this.node)
      .map(Href::new)
      .flatMap(Href::getEId);
  }
}
