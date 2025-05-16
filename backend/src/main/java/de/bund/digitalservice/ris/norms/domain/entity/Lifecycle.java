package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;

/** Class representing the akn:lifecycle */
@Getter
@AllArgsConstructor
public class Lifecycle {

  private final Element element;

  /**
   * Returns a list of {@link EventRef} instance from the {@link Lifecycle}.
   *
   * @return the list of {@link EventRef}
   */
  public List<EventRef> getEventRefs() {
    return NodeParser.getElementsFromExpression("./eventRef", element)
      .stream()
      .map(EventRef::new)
      .toList();
  }

  /**
   * Add a new akn:eventRef element to this element.
   * @return the newly created {@link EventRef}
   */
  public EventRef addEventRef() {
    return EventRef.createAndAppend(getElement());
  }
}
