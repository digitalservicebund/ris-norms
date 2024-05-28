package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
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
    return NodeParser.getValueFromExpression("./@eId", this.node);
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
   * Returns the destination href as {@link String}.
   *
   * @return The destination href of the modification
   */
  public Optional<Href> getDestinationHref() {
    return NodeParser.getValueFromExpression("./destination/@href", this.node).map(Href::new);
  }

  /**
   * Updates the href attribute of the destination node within the modification
   *
   * @param destinationHref - the new destination href of the modification
   */
  public void setDestinationHref(final String destinationHref) {
    NodeParser.getNodeFromExpression("./destination", this.node)
        .getAttributes()
        .getNamedItem("href")
        .setNodeValue(destinationHref);
  }

  /**
   * Returns eid of the force as {@link String}. This eid identifies the temporal group of this
   * modification.
   *
   * @return The force eid of the modification
   */
  public Optional<String> getForcePeriodEid() {
    return NodeParser.getValueFromExpression("./force/@period", this.node)
        .map(Href::new)
        .flatMap(Href::getEId);
  }

  /**
   * Updates the period attribute of the force node within the modification
   *
   * @param periodEid - the eId of the new referenced temporal group
   */
  public void setForcePeriodEid(final String periodEid) {
    NodeParser.getNodeFromExpression("./force", this.node)
        .getAttributes()
        .getNamedItem("period")
        .setNodeValue(new Href.Builder().setEId(periodEid).buildRelative().value());
  }
}
