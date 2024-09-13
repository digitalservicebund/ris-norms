package de.bund.digitalservice.ris.norms.domain.entity;

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
   * Get the EId of a node's @eId property.
   *
   * <p>the assumption is every node has an eId
   *
   * @param node the node with an @eId property
   * @return the eId of the node or empty if no eId could be found.
   */
  public static EId fromNode(Node node) {
    String eId = node.getAttributes().getNamedItem("eId").getNodeValue();
    return new EId(eId);
  }

  /**
   * Creates the expected EId for the given node given that the eId of the parent node is correct.
   *
   * <p>See LDML.de 1.6 Section 7.1.1
   *
   * @param node the node for which the eId should be calculated
   * @return the expected EId for that node
   */
  public static Optional<EId> createForNode(Node node) {
    final Optional<EIdPartType> eIdPartType = EIdPartType.forAknElement(node);
    if (eIdPartType.isEmpty()) {
      return Optional.empty();
    }
    final EIdPart newEIdPart =
        new EIdPart(eIdPartType.get().getName(), findEIdPosition(node, eIdPartType.get()));

    if ((node.getParentNode() instanceof Element && isContentElement(node.getParentNode()))) {
      final EId parentEId = EId.fromNode(node.getParentNode());
      return Optional.of(parentEId.addPart(newEIdPart));
    } else return Optional.of(new EId(newEIdPart.toString()));
  }

  private static boolean isContentElement(Node currentNode) {
    return !currentNode.getNodeName().equals("root")
        && !currentNode.getNodeName().equals("akn:akomaNtoso")
        && !currentNode.getNodeName().equals("#document")
        && !currentNode.getNodeName().equals("akn:act")
        && !currentNode.getNodeName().equals("meta:legalDocML.de_metadaten")
        && !currentNode.getParentNode().equals("meta:legalDocML.de_metadaten_ds")
        && !currentNode.getNodeName().equals("math")
        && !currentNode.getNodeName().equals("mrow")
        && !currentNode.getNodeName().equals("mi")
        && !currentNode.getNodeName().equals("mo")
        && !currentNode.getNodeName().equals("msup")
        && !currentNode.getNodeName().equals("mfrac")
        && !currentNode.getNodeName().equals("munderover")
        && !currentNode.getNodeName().equals("msqrt")
        && !currentNode.getNodeName().equals("mn");
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
                if (previousSibling.hasAttributes()) { // ignore text nodes
                  var eId = EId.fromNode(previousSibling);
                  var previousSiblingEIdType = eId.getParts().getLast().getType();

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
