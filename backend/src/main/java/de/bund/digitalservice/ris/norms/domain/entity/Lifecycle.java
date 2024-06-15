package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the akn:lifecycle */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Lifecycle {

  private final Node node;

  /**
   * Returns a list of {@link EventRef} instance from the {@link Lifecycle}.
   *
   * @return the list of {@link EventRef}
   */
  public List<EventRef> getEventRefs() {
    return NodeParser.getNodesFromExpression("./eventRef", node).stream()
        .map(EventRef::new)
        .toList();
  }
}
