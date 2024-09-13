package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing an akn:textualMod. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TextualMod {

  private final Node node;

  /**
   * Returns the eId as {@link String}.
   *
   * @return The eId of the modification
   */
  public Optional<String> getEid() {
    return EId.fromNode(getNode()).map(EId::value);
  }

  /**
   * Returns the type of modification as {@link String}.
   *
   * @return The type of the modification
   */
  public Optional<String> getType() {
    return NodeParser.getValueFromExpression("./@type", this.node);
  }

  /**
   * Returns the source href as {@link String}.
   *
   * @return The source href of the modification
   */
  public Optional<Href> getSourceHref() {
    return NodeParser.getValueFromExpression("./source/@href", this.node).map(Href::new);
  }

  /**
   * Returns the destination href as {@link Href}.
   *
   * @return The destination href of the modification
   */
  public Optional<Href> getDestinationHref() {
    return NodeParser.getValueFromExpression("./destination/@href", this.node).map(Href::new);
  }

  /**
   * Returns the destination upTo as {@link Href}.
   *
   * @return The destination upTo of the modification
   */
  public Optional<Href> getDestinationUpTo() {
    return NodeParser.getValueFromExpression("./destination/@upTo", this.node).map(Href::new);
  }

  private Node getOrCreateDestinationNode() {
    return NodeParser
      .getNodeFromExpression("./destination", this.node)
      .orElseGet(() -> {
        var newElement = getNode().getOwnerDocument().createElement("akn:destination");
        newElement.setAttribute(
          "eId",
          new EId(this.getEid().orElseThrow()).addPart(new EIdPart("destination", "1")).value()
        );
        newElement.setAttribute("GUID", UUID.randomUUID().toString());
        getNode().appendChild(newElement);
        return newElement;
      });
  }

  /**
   * Updates the href attribute of the destination node within the modification
   *
   * @param destinationHref - the new destination href of the modification
   */
  public void setDestinationHref(final String destinationHref) {
    getOrCreateDestinationNode().getAttributes().getNamedItem("href").setNodeValue(destinationHref);
  }

  /**
   * Updates the upTo attribute of the destination node within the modification
   *
   * @param destinationUpTo - the destination href of the last to be replaced element
   */
  public void setDestinationUpTo(final String destinationUpTo) {
    Element element = (Element) getOrCreateDestinationNode();
    if (StringUtils.isNotEmpty(destinationUpTo)) {
      element.setAttribute("upTo", destinationUpTo);
    } else {
      element.removeAttribute("upTo");
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
      .getValueFromExpression("./force/@period", this.node)
      .map(Href::new)
      .flatMap(Href::getEId);
  }

  private Node getOrCreateForceNode() {
    return NodeParser
      .getNodeFromExpression("./force", getNode())
      .orElseGet(() -> {
        var newElement = getNode().getOwnerDocument().createElement("akn:force");
        newElement.setAttribute(
          "eId",
          new EId(this.getEid().orElseThrow()).addPart(new EIdPart("gelzeitnachw", "1")).value()
        );
        newElement.setAttribute("GUID", UUID.randomUUID().toString());
        getNode().appendChild(newElement);
        return newElement;
      });
  }

  /**
   * Updates the period attribute of the force node within the modification
   *
   * @param periodEid - the eId of the new referenced temporal group
   */
  public void setForcePeriodEid(final String periodEid) {
    getOrCreateForceNode()
      .getAttributes()
      .getNamedItem("period")
      .setNodeValue(
        periodEid == null
          ? ""
          : new Href.Builder().setEId(periodEid).buildInternalReference().value()
      );
  }
}
