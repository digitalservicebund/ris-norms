package de.bund.digitalservice.ris.norms.domain.entity.eid;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Represents an LDML.de eId.
 *
 * @param value the eId as {@link String}
 */
public record EId(String value) {
  public List<EIdPart> getParts() {
    return Arrays.stream(value.split("_")).map(EIdPart::new).toList();
  }

  /**
   * Creates a new eId that includes all the parts of the current eId and the new part at the end.
   *
   * @param part the new part to append to the eId
   * @return a new eId
   */
  public EId addPart(EIdPart part) {
    return new EId(value + "_" + part);
  }

  @Override
  public String toString() {
    return value;
  }

  /**
   * Gets the parent eId if it exists. This means the EId without the last part.
   * @return the eId without the last part
   */
  public Optional<EId> getParent() {
    int lastUnderscoreIndex = value.lastIndexOf("_");

    if (lastUnderscoreIndex == -1) {
      return Optional.empty();
    }

    return Optional.of(new EId(value.substring(0, lastUnderscoreIndex)));
  }

  /**
   * Get the EId of a node's @eId property.
   *
   * @param node the node with an @eId property
   * @return the eId of the node or empty if no eId could be found.
   */
  public static Optional<EId> fromNode(Node node) {
    if (!(node instanceof Element element)) {
      return Optional.empty();
    }

    var eId = element.getAttribute("eId");

    if (eId.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(new EId(eId));
  }

  /**
   * Get the EId of a node's @eId property which should be there.
   *
   * @param node the node with an @eId property
   * @return the eId of the node
   */
  public static EId fromMandatoryNode(Node node) {
    return new EId(NodeParser.getValueFromMandatoryNodeFromExpression("./@eId", node));
  }

  /**
   * Creates the expected EId for the given node given that the eId of the parent node is correct.
   *
   * <p>See LDML.de 1.8 Section 7.1.1 Syntax eines @eId-Attributs
   *
   * @param element the node for which the eId should be calculated
   * @return the expected EId for that node
   */
  public static Optional<EId> forNode(Element element) {
    final Optional<EIdPartType> eIdPartType = EIdPartType.forAknElement(element);

    if (eIdPartType.isEmpty()) {
      return Optional.empty();
    }

    var newEIdPart = new EIdPart(
      eIdPartType.get().getName(),
      EIdPosition.findEIdPosition(element, eIdPartType.get())
    );

    // articles not nested in quoted structures do not use their parents eIds as part of their own eIds.
    if (eIdPartType.get().equals(EIdPartType.ART) && !isInQuotedStructure(element)) {
      return Optional.of(new EId(newEIdPart.value()));
    }

    final Optional<EId> parentEId = EId.fromNode(element.getParentNode());
    return parentEId
      .map(eId -> eId.addPart(newEIdPart))
      .or(() -> Optional.of(new EId(newEIdPart.value())));
  }

  private static boolean isInQuotedStructure(Node node) {
    return NodeParser.getValueFromExpression("ancestor::quotedStructure", node).isPresent();
  }
}
