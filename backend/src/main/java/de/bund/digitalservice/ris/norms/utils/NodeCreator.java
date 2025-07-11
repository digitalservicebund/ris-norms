package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.domain.entity.Namespace;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EIdPart;
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
   * @param namespace the namespace the element resides in
   * @param tagName the tag name of the new element, without a prefix like akn or meta. This prefix will be added automatically.
   * @param parentNode the element of which this newly created element should be a child
   * @return the newly created element
   */
  public static Element createElement(
    final Namespace namespace,
    final String tagName,
    final Node parentNode
  ) {
    var newElement = parentNode
      .getOwnerDocument()
      .createElementNS(
        namespace.getNamespaceUri(),
        "%s:%s".formatted(namespace.getPrefix(), tagName)
      );
    parentNode.appendChild(newElement);
    return newElement;
  }

  /**
   * Create a new element with both an eId and a GUID. The new element is appended to the given
   * parent node.
   *
   * @param namespace the namespace of the new element
   * @param tagName the tag name of the new element
   * @param parentNode the element of which this newly created element should be a child
   * @return the newly created element
   */
  public static Element createElementWithEidAndGuid(
    final Namespace namespace,
    final String tagName,
    final Node parentNode
  ) {
    var newElement = createElement(namespace, tagName, parentNode);
    newElement.setAttribute("GUID", UUID.randomUUID().toString());
    EId.forNode(newElement).ifPresent(eId -> newElement.setAttribute("eId", eId.value()));
    return newElement;
  }

  /**
   * Create a new element with both an eId and a GUID. The new element is NOT appended to the given
   * parent node. The parent node is only needed for calculating the eId of the new child
   *
   * @param namespace the namespace of the new element
   * @param tagName the tag name of the new element
   * @param eidPart the last part of the eid for the new element
   * @param parentNode the element of which this newly created element should be a child, just for
   *     calculating the eId
   * @return the newly created element
   */
  public static Element createElementWithStaticEidAndGuidNoAppend(
    final Namespace namespace,
    final String tagName,
    final EIdPart eidPart,
    final Node parentNode
  ) {
    var newElement = parentNode
      .getOwnerDocument()
      .createElementNS(
        namespace.getNamespaceUri(),
        "%s:%s".formatted(namespace.getPrefix(), tagName)
      );
    newElement.setAttribute("eId", EId.fromMandatoryNode(parentNode).addPart(eidPart).toString());
    newElement.setAttribute("GUID", UUID.randomUUID().toString());
    return newElement;
  }
}
