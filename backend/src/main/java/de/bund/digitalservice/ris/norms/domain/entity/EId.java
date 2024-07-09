package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
   * Get the EId of a node's @eId property.
   *
   * @param node the node with an @eId property
   * @return the eId of the node or empty if no eId could be found.
   */
  public static Optional<EId> fromNode(Node node) {
    return NodeParser.getValueFromExpression("./@eId", node).map(EId::new);
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
}
