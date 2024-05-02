package de.bund.digitalservice.ris.norms.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Util class that is responsible for checking and updating the consistency of all eIds, meaning
 * that if node structure was manipulated (new nodes inserted or deleted) the eIds will be updated
 * according to the index position within its containing parent node.
 */
public final class EidConsistencyGuardian {

  private EidConsistencyGuardian() {
    // Should not be instantiated as an object
  }

  private static Element rootElement;

  /**
   * This method traverses all the XML nodes and checks the eId consistency, making the necessary
   * updates of the index positions and taking care of updating also the references in other parts
   * of the XML. But first of all it checks if dead references exists
   *
   * @param currentXml - the XML to be checked/corrected for eIds consistency
   * @return the corrected XML
   */
  public static Document correctEids(final Document currentXml) {
    rootElement = currentXml.getDocumentElement();
    // Dead references
    setRemovedReferencesToEmptyStringNew(
        "//textualMod/force", "period", "//temporalData/temporalGroup", "eId");
    setRemovedReferencesToEmptyStringNew(
        "//proprietary/legalDocML.de_metadaten_ds/*", "start", "//lifecycle/eventRef", "eId");
    setRemovedReferencesToEmptyStringNew(
        "//proprietary/legalDocML.de_metadaten_ds/*", "end", "//lifecycle/eventRef", "eId");
    // Check eIds
    updateElementEids(rootElement, null);
    return currentXml;
  }

  private static void setRemovedReferencesToEmptyStringNew(
      final String elementXPath,
      final String attribute,
      final String targetXpath,
      final String targetAttribute) {
    // Traverse the document to find the elements with the specified XPath expression
    final List<Node> nodeList = NodeParser.getNodesFromExpression(elementXPath, rootElement);
    for (Node node : nodeList) {
      final Element targetElement = (Element) node;
      final String attributeValue = targetElement.getAttribute(attribute);
      // Check if the attribute contains references
      if (!attributeValue.isEmpty()
          && (!isReferenceValid(attributeValue, targetXpath, targetAttribute))) {
        targetElement.setAttribute(attribute, "");
      }
    }
  }

  private static boolean isReferenceValid(
      final String reference, final String targetXpath, final String targetAttribute) {
    // Check if the reference exists within the XML document
    final List<Node> nodeList = NodeParser.getNodesFromExpression(targetXpath, rootElement);
    for (Node node : nodeList) {
      Element temporalGroup = (Element) node;
      String eId = temporalGroup.getAttribute(targetAttribute);
      if (reference.contains(eId)) {
        return true;
      }
    }
    return false;
  }

  private static void updateElementEids(final Element element, final String parentEid) {
    final Map<String, Integer> childTypeCount =
        new HashMap<>(); // Track count of child nodes of each type
    final NodeList childNodes = element.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      final Node node = childNodes.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        final Element childElement = (Element) node;

        // Keep trap of index counting per node type
        final String childTagName = childElement.getTagName();
        childTypeCount.put(childTagName, childTypeCount.getOrDefault(childTagName, 0) + 1);

        if (childElement.hasAttribute("eId")) {
          final String currentEid = childElement.getAttribute("eId");
          final String correctEid =
              determineCorrectEid(childTypeCount.get(childTagName), currentEid, parentEid);

          if (!currentEid.equals(correctEid)) {
            updateReferences(currentEid, correctEid);
            childElement.setAttribute("eId", correctEid);
            updateElementEids(
                childElement, correctEid); // Recursively update child elements with corrected eId
          } else {
            updateElementEids(
                childElement, currentEid); // Recursively update child elements with old eId
          }
        } else {
          updateElementEids(childElement, null);
        }
      }
    }
  }

  private static String determineCorrectEid(int currentIndex, String oldEid, String newParentEid) {
    final StringBuilder newEid = new StringBuilder();

    String currentElementId;

    // Check first if eId contains parent eIds
    if (oldEid.contains("_")) {
      // 1. ParentEid is not the same
      final int lastIndex = oldEid.lastIndexOf('_');

      final String oldParentEid = oldEid.substring(0, lastIndex);
      if (!oldParentEid.equals(newParentEid)) {
        newEid.append(newParentEid);
      } else {
        newEid.append(oldParentEid);
      }
      currentElementId = oldEid.substring(lastIndex);
    } else {
      currentElementId = oldEid;
    }

    // 2. Index of current node id not correct
    final String elementEidNoIndex = currentElementId.substring(0, currentElementId.indexOf("-"));
    final String currentElementIndex =
        currentElementId.substring(currentElementId.indexOf("-") + 1);
    if (Integer.parseInt(currentElementIndex) != (currentIndex)) {
      newEid.append(elementEidNoIndex);
      newEid.append("-");
      newEid.append(currentIndex);
    } else {
      newEid.append(currentElementId);
    }
    return newEid.toString();
  }

  private static void updateReferences(final String oldEid, final String newEid) {
    // Traverse the entire document to find references to the old eId in attributes
    updateReferencesInAttributes(rootElement, oldEid, newEid);
  }

  private static void updateReferencesInAttributes(
      final Element element, final String oldId, final String newId) {
    final NamedNodeMap attributes = element.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      Node attribute = attributes.item(i);
      if (attribute.getNodeValue().contains(oldId)) {
        attribute.setNodeValue(attribute.getNodeValue().replace(oldId, newId));
      }
    }

    // Recursively traverse child elements
    final NodeList childNodes = element.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node node = childNodes.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        updateReferencesInAttributes((Element) node, oldId, newId);
      }
    }
  }
}
