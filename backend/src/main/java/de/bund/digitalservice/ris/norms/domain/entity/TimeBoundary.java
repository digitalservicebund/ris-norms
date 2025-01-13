package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a time boundary. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TimeBoundary {

  private final TimeInterval timeInterval;
  private final EventRef eventRef;
  private final TemporalGroup temporalGroup;

  /**
   * Returns a eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the eventRef
   */
  public String getEventRefEid() {
    return EId.fromMandatoryNode(eventRef.getElement()).value();
  }

  /**
   * Returns a eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the timeInterval
   */
  public String getTimeIntervalEid() {
    return EId.fromMandatoryNode(timeInterval.getElement()).value();
  }

  /**
   * Returns a temporal group eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the temporal group
   */
  public String getTemporalGroupEid() {
    return EId.fromMandatoryNode(temporalGroup.getElement()).value();
  }

  /**
   * Updates the date of the associated akn:eventRef.
   *
   * @param eventRefDate contains the new date to be set
   */
  public void setEventRefDate(LocalDate eventRefDate) {
    eventRef.getElement().setAttribute("date", eventRefDate.toString());
  }
}
