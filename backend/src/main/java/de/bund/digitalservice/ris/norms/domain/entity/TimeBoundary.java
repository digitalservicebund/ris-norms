package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a time boundary. */
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TimeBoundary {
  @Getter private final Node timeIntervalNode;
  @Getter private final Node eventRefNode;

  /**
   * Returns a date as {@link LocalDate} from a {@link Node} in a {@link Norm}. h
   *
   * @return The date of the event
   */
  public Optional<LocalDate> getDate() {
    return NodeParser.getValueFromExpression("./@date", this.eventRefNode).map(LocalDate::parse);
  }

  /**
   * Returns a eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the event
   */
  public Optional<String> getEventRefEid() {
    return NodeParser.getValueFromExpression("./@eId", this.eventRefNode);
  }

  /**
   * Returns a eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the timeInterval
   */
  public Optional<String> getTimeIntervalEid() {
    return NodeParser.getValueFromExpression("./@eId", this.timeIntervalNode);
  }

  /**
   * Updates the date of the associated akn:eventRef.
   *
   * @param eventRefDate contains the new date to be set
   */
  public void setEventRefDate(LocalDate eventRefDate) {
    eventRefNode.getAttributes().getNamedItem("date").setNodeValue(eventRefDate.toString());
  }
}
