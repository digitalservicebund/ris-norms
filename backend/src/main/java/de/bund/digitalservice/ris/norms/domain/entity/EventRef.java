package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a akn:eventRef. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class EventRef {
  private final Node node;

  public EId getEid() {
    return EId.fromNode(node);
  }

  /**
   * Returns a date as {@link LocalDate} from a {@link Node} in a {@link Norm}. h
   *
   * @return The date of the event
   */
  public Optional<LocalDate> getDate() {
    return NodeParser.getValueFromExpression("./@date", node).map(LocalDate::parse);
  }
}
