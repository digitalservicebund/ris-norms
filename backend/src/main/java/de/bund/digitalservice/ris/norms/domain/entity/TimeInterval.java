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
   * Create a new temporal group within the given {@link TemporalGroup}.
   * @param temporalGroup the {@link TemporalGroup} element within the {@link TimeInterval} should be created
   * @param start the href to the akn:eventRef of the start date of the akn:timeInterval
   * @param refersTo the refersTo attribute of the akn:timeInterval
   */
  public TimeInterval(TemporalGroup temporalGroup, Href start, String refersTo) {
    Element element = NodeCreator.createElementWithEidAndGuid(
      "akn:timeInterval",
      temporalGroup.getNode()
    );
    element.setAttribute("start", start.toString());
    element.setAttribute("refersTo", refersTo);
    this.node = element;
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
