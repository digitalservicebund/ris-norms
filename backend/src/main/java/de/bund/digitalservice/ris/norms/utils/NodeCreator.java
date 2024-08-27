package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.domain.entity.EId;
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
   * @param parentNode the element of which this newly created element should be a child
   * @return the newly created element
   */
  public static Element createElementWithEidAndGuid(final String tagName, final Node parentNode) {
    var newElement = parentNode.getOwnerDocument().createElement(tagName);
    newElement.setAttribute("GUID", UUID.randomUUID().toString());
    parentNode.appendChild(newElement);
    EId.forNode(newElement).ifPresent(eId -> newElement.setAttribute("eId", eId.value()));
    return newElement;
  }

  /**
   * Create a new element with both an eId and a GUID. The new element is NOT appended to the given
   * parent node. The parent node is only needed for calculating the eId of the new child
   *
   * @param tagName the tag name of the new element
   * @param eidPartName the name for the last part of the eid for the new element
   * @param parentNode the element of which this newly created element should be a child, just for
   *     calculating the eId
   * @return the newly created element
   */
  public static Element createElementWithStaticEidAndGuidNoAppend(
      final String tagName, final String eidPartName, final Node parentNode) {
    var newElement = parentNode.getOwnerDocument().createElement(tagName);
    newElement.setAttribute("eId", EId.fromMandatoryNode(parentNode) + "_" + eidPartName);
    newElement.setAttribute("GUID", UUID.randomUUID().toString());
    return newElement;
  }
}
