package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing a akn:eventRef. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class EventRef {

  private final Node node;

  /**
   * Creates a new {@link EventRef} within the given {@link Lifecycle}.
   * @param lifecycle the {@link Lifecycle} element within the {@link EventRef} should be created
   * @param date the date of the akn:eventRef
   * @param refersTo the refersTo attribute of the akn:eventRef
   * @param type the type attribute of the akn:eventRef
   */
  public EventRef(Lifecycle lifecycle, String date, String refersTo, String type) {
    final Element eventRef = NodeCreator.createElementWithEidAndGuid(
      "akn:eventRef",
      lifecycle.getNode()
    );
    eventRef.setAttribute("date", date);
    eventRef.setAttribute("source", "attributsemantik-noch-undefiniert");
    eventRef.setAttribute("refersTo", refersTo);
    eventRef.setAttribute("type", type);
    this.node = eventRef;
  }

  public EId getEid() {
    return EId.fromMandatoryNode(node);
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
