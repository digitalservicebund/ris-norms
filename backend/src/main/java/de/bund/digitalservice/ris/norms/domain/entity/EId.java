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
    if (!node.hasAttributes()) {
      return Optional.empty();
    }

    var eIdNode = node.getAttributes().getNamedItem("eId");

    if (eIdNode == null) {
      return Optional.empty();
    }

    var eId = eIdNode.getNodeValue();

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
   * <p>See LDML.de 1.6 Section 7.1.1
   *
   * @param node the node for which the eId should be calculated
   * @return the expected EId for that node
   */
  public static Optional<EId> forNode(Node node) {
    final Optional<EId> parentEId = EId.fromNode(node.getParentNode());

    final Optional<EIdPartType> eIdPartType = EIdPartType.forAknElement(node);

    if (eIdPartType.isEmpty()) {
      return Optional.empty();
    }

    var newEIdPart =
        new EIdPart(eIdPartType.get().getName(), findEIdPosition(node, eIdPartType.get()));

    return parentEId
        .map(eId -> eId.addPart(newEIdPart))
        .or(() -> Optional.of(new EId(newEIdPart.value())));
  }

  private static String findEIdPosition(Node node, EIdPartType eIdPartType) {
    if (node.getNodeName().equals("akn:li")
        && node.getParentNode().getNodeName().equals("akn:ol")) {
      return node.getAttributes().getNamedItem("value").getNodeValue().replace(".", "");
    }

    return NodeParser.getValueFromExpression("./num/marker/@name", node)
        .orElseGet(
            () -> {
              var position = 1;
              var previousSibling = node.getPreviousSibling();
              while (previousSibling != null) {
                var eId = EId.fromNode(previousSibling);

                if (eId.isPresent()) {
                  var previousSiblingEIdType = eId.get().getParts().getLast().getType();

                  if (previousSiblingEIdType.equals(eIdPartType.getName())) {
                    position++;
                  }
                }

                previousSibling = previousSibling.getPreviousSibling();
              }

              return "%d".formatted(position);
            });
  }
}
