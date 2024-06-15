package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a akn:timeInterval. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TimeInterval {
  private final Node node;

  /**
   * Returns the eId of the event ref of the temporal group
   *
   * @return The eId of the event ref of this temporal group
   */
  public Optional<String> getEventRefEId() {
    return NodeParser.getValueFromExpression("./@start", this.node)
        .map(Href::new)
        .flatMap(Href::getEId);
  }
}
