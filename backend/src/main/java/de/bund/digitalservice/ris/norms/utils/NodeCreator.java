package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.EIdPart;
import java.util.Comparator;
import java.util.UUID;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Utility class for creating new nodes. */
public class NodeCreator {

  // Private constructor to hide the implicit public one and prevent instantiation
  private NodeCreator() {}

  /**
   * Create a new element without an eId and a GUID. The new element is appended to the given parent
   * node.
   *
   * @param tagName the tag name of the new element
   * @param parentNode the element of which this newly created element should be a child
   * @return the newly created element
   */
  public static Element createElement(final String tagName, final Node parentNode) {
    var newElement = parentNode.getOwnerDocument().createElement(tagName);
    parentNode.appendChild(newElement);
    return newElement;
  }

  /**
   * Create a new element with both an eId and a GUID. The new element is appended to the given
   * parent node.
   *
   * @param tagName the tag name of the new element
   * @param eidPartName the name for the last part of the eid for the new element
   * @param parentNode the element of which this newly created element should be a child
   * @return the newly created element
   */
  public static Element createElementWithEidAndGuid(
      final String tagName, final String eidPartName, final Node parentNode) {
    var newElement = parentNode.getOwnerDocument().createElement(tagName);
    newElement.setAttribute("eId", calculateNextPossibleEid(parentNode, eidPartName));
    newElement.setAttribute("GUID", UUID.randomUUID().toString());
    parentNode.appendChild(newElement);
    return newElement;
  }

  /**
   * Calculates the next possible eId for the given eIdPartType and parent node.
   *
   * @param parentNode The parent node under which this new eId should be used
   * @param eidPartType The name of the new part of the eId
   * @return The new eId created from the parent node eId, the eidPartType and the next available
   *     position
   */
  public static String calculateNextPossibleEid(Node parentNode, String eidPartType) {
    var lastPosition =
        NodeParser.nodeListToList(parentNode.getChildNodes()).stream()
            .flatMap(node -> EId.fromNode(node).stream())
            .map(eId -> eId.getParts().getLast())
            .filter(eIdPart -> eIdPart.getType().equals(eidPartType))
            .map(EIdPart::getPosition)
            .map(Integer::parseInt)
            .max(Comparator.comparingInt(Integer::intValue))
            .orElse(0);
    var newEidPart = new EIdPart(eidPartType, String.valueOf(lastPosition + 1));

    return EId.fromNode(parentNode)
        .map(parendEId -> parendEId.addPart(newEidPart))
        .map(EId::value)
        .orElseThrow();
  }
}
