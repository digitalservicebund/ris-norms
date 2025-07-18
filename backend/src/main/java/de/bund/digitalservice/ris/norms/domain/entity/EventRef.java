package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing a akn:eventRef. */
@Getter
@AllArgsConstructor
public class EventRef {

  private final Element element;

  /**
   * Creates a new akn:eventRef element and appends it to the given node.
   * @param parentNode the node under which a new {@link EventRef} should be created.
   * @return the newly created {@link EventRef}
   */
  public static EventRef createAndAppend(Node parentNode) {
    final var element = NodeCreator.createElementWithEidAndGuid(
      Namespace.INHALTSDATEN,
      "eventRef",
      parentNode
    );
    element.setAttribute("source", "attributsemantik-noch-undefiniert");
    return new EventRef(element);
  }

  /**
   * Set the date of the akn:eventRef
   * @param date the date of the event
   */
  public void setDate(String date) {
    element.setAttribute("date", date);
  }

  /**
   * Set the refersTo attribute of the akn:eventRef
   * @param refersTo the value for the attribute
   */
  public void setRefersTo(String refersTo) {
    element.setAttribute("refersTo", refersTo);
  }

  /**
   * Set the type attribute of the akn:eventRef
   * @param type the value for the attribute
   */
  public void setType(String type) {
    element.setAttribute("type", type);
  }

  /**
   * Get the eId of the element.
   * @return the eId
   */
  public EId getEid() {
    return EId.fromMandatoryNode(element);
  }

  /**
   * Returns a date as {@link LocalDate} from a {@link Node} in a {@link Norm}. h
   *
   * @return The date of the event
   */
  public Optional<LocalDate> getDate() {
    return NodeParser.getValueFromExpression("./@date", element).map(LocalDate::parse);
  }
}
