package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;

/** Class representing an akn:textualMod. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TextualMod {

  private final Element element;

  /**
   * Returns the eId as {@link String}.
   *
   * @return The eId of the modification
   */
  public String getEid() {
    return EId.fromMandatoryNode(getElement()).value();
  }

  /**
   * Returns the type of modification as {@link String}.
   *
   * @return The type of the modification
   */
  public Optional<String> getType() {
    return NodeParser.getValueFromExpression("./@type", this.element);
  }

  /**
   * Returns the source href as {@link String}.
   *
   * @return The source href of the modification
   */
  public Optional<Href> getSourceHref() {
    return NodeParser.getValueFromExpression("./source/@href", this.element).map(Href::new);
  }

  /**
   * Returns the destination href as {@link Href}.
   *
   * @return The destination href of the modification
   */
  public Optional<Href> getDestinationHref() {
    return NodeParser.getValueFromExpression("./destination/@href", this.element).map(Href::new);
  }

  /**
   * Returns the destination upTo as {@link Href}.
   *
   * @return The destination upTo of the modification
   */
  public Optional<Href> getDestinationUpTo() {
    return NodeParser.getValueFromExpression("./destination/@upTo", this.element).map(Href::new);
  }

  private Element getOrCreateDestinationNode() {
    return NodeParser
      .getElementFromExpression("./destination", this.element)
      .orElseGet(() -> NodeCreator.createElementWithEidAndGuid("akn:destination", getElement()));
  }

  /**
   * Updates the href attribute of the destination node within the modification
   *
   * @param destinationHref - the new destination href of the modification
   */
  public void setDestinationHref(final Href destinationHref) {
    getOrCreateDestinationNode().setAttribute("href", destinationHref.toString());
  }

  /**
   * Updates the upTo attribute of the destination node within the modification
   *
   * @param destinationUpTo - the destination href of the last to be replaced element
   */
  public void setDestinationUpTo(final Href destinationUpTo) {
    if (destinationUpTo != null) {
      getOrCreateDestinationNode().setAttribute("upTo", destinationUpTo.toString());
    } else {
      getOrCreateDestinationNode().removeAttribute("upTo");
    }
  }

  /**
   * Returns eid of the force as {@link String}. This eid identifies the temporal group of this
   * modification.
   *
   * @return The force eid of the modification
   */
  public Optional<String> getForcePeriodEid() {
    return NodeParser
      .getValueFromExpression("./force/@period", this.element)
      .map(Href::new)
      .flatMap(Href::getEId);
  }

  private Element getOrCreateForceNode() {
    return NodeParser
      .getElementFromExpression("./force", getElement())
      .orElseGet(() -> NodeCreator.createElementWithEidAndGuid("akn:force", getElement()));
  }

  /**
   * Updates the period attribute of the force node within the modification
   *
   * @param periodEid - the eId of the new referenced temporal group
   */
  public void setForcePeriodEid(final String periodEid) {
    if (periodEid != null) {
      getOrCreateForceNode()
        .setAttribute(
          "period",
          new Href.Builder().setEId(periodEid).buildInternalReference().value()
        );
    } else {
      getOrCreateForceNode().removeAttribute("period");
    }
  }
}
