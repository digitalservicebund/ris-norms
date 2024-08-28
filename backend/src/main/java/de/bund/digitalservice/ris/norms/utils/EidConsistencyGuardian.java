package de.bund.digitalservice.ris.norms.utils;

import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Util class that is responsible for checking and updating the consistency of all eIds, meaning
 * that if node structure was manipulated (new nodes inserted or deleted) the eIds will be updated
 * according to the index position within its containing parent node.
 */
public final class EidConsistencyGuardian {

  private EidConsistencyGuardian() {
    // Should not be instantiated as an object
  }

  /**
   * This method checks for dead references and removes them
   *
   * @param currentXml - the XML in which dead references should be removed
   */
  public static void eliminateDeadReferences(final Document currentXml) {
    Element rootElement = currentXml.getDocumentElement();
    if (rootElement != null) {
      setRemovedReferencesToEmptyStringNew(
          "//textualMod/force", "period", "//temporalData/temporalGroup", "eId", rootElement);
      setRemovedReferencesToEmptyStringNew(
          "//proprietary/legalDocML.de_metadaten_ds/*",
          "start",
          "//lifecycle/eventRef",
          "eId",
          rootElement);
      setRemovedReferencesToEmptyStringNew(
          "//proprietary/legalDocML.de_metadaten_ds/*",
          "end",
          "//lifecycle/eventRef",
          "eId",
          rootElement);
    }
  }

  /**
   * This method traverses all the XML nodes and checks the eId consistency, making the necessary
   * updates of the index positions and taking care of updating also the references in other parts
   * of the XML
   *
   * @param node - the XML-Node to be checked/corrected for eIds consistency
   */
  public static void correctEids(final Node node) {
    final Map<String, String> oldToNewEIdMap = new HashMap<>();
    List<Node> nodesToUpdate = new ArrayList<>();
    nodesToUpdate.add(node);

    while (!nodesToUpdate.isEmpty()) {
      var currentNode = nodesToUpdate.removeFirst();

      if (currentNode instanceof Element currentElement) {
        final var expectedEId = EId.forNode(currentNode);
        final var currentEId = EId.fromNode(currentNode);

        if (expectedEId != currentEId) {
          if (expectedEId.isPresent()) {
            currentElement.setAttribute("eId", expectedEId.get().value());
          }

          if (currentEId.isPresent() && expectedEId.isPresent()) {
            oldToNewEIdMap.put(currentEId.get().value(), expectedEId.get().value());
          }
        }
      }

      nodesToUpdate.addAll(NodeParser.nodeListToList(currentNode.getChildNodes()));
    }

    // Traverse the entire document to find references to the old eId in attributes
    updateReferencesInAttributes(node, oldToNewEIdMap);
  }

  private static void setRemovedReferencesToEmptyStringNew(
      final String elementXPath,
      final String attribute,
      final String targetXpath,
      final String targetAttribute,
      final Element rootElement) {
    // Traverse the document to find the elements with the specified XPath expression
    final List<Node> nodeList = NodeParser.getNodesFromExpression(elementXPath, rootElement);
    for (Node node : nodeList) {
      final Element targetElement = (Element) node;
      final String attributeValue = targetElement.getAttribute(attribute);
      // Check if the attribute contains references
      if (!attributeValue.isEmpty()
          && (!isReferenceValid(attributeValue, targetXpath, targetAttribute, rootElement))) {
        targetElement.setAttribute(attribute, "");
      }
    }
  }

  private static boolean isReferenceValid(
      final String reference,
      final String targetXpath,
      final String targetAttribute,
      final Element rootElement) {
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

  private static void updateReferencesInAttributes(
      final Node element, final Map<String, String> oldToNewEIdMap) {
    final NamedNodeMap attributes = element.getAttributes();

    if (attributes != null) {
      for (int i = 0; i < attributes.getLength(); i++) {
        Node attribute = attributes.item(i);
        if (attribute.getNodeValue().startsWith("#")) {
          Href attributeHref = new Href(attribute.getNodeValue());
          var currentEId = attributeHref.getEId();

          if (currentEId.isPresent() && oldToNewEIdMap.containsKey(currentEId.get())) {
            var newEId = oldToNewEIdMap.get(currentEId.get());
            attribute.setNodeValue(attribute.getNodeValue().replace(currentEId.get(), newEId));
          }
        }
      }
    }

    // Recursively traverse child elements
    final List<Node> childNodes = NodeParser.nodeListToList(element.getChildNodes());
    childNodes.stream()
        .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
        .forEach(node -> updateReferencesInAttributes(node, oldToNewEIdMap));
  }
}
