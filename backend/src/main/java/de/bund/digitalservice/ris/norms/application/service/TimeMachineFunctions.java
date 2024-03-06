package de.bund.digitalservice.ris.norms.application.service;

import java.util.Optional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying LDML.de
 * "modifications" to LDML.de files. For details on LDML.de modifications, cf.
 * https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads
 */
public class TimeMachineFunctions {

  // prevent default constructor
  private TimeMachineFunctions(){}

  /**
   * Applies the modifications of the amending law onto the target law.
   *
   * @param amendingLaw An Document that contains LDML.de modifications to be applied on the target
   *     law
   * @param targetLaw The Document that the modifications will be applied to
   * @return the Document that results in applying the amending law's modifications to the target
   *     law
   */
  public static Optional<Document> applyTimeMachine(
      final Document amendingLaw, final Document targetLaw) {
    // TODO: cover all individual failures in tests

      final Optional<Document> targetLawClone = XmlFunctions.cloneDocument(targetLaw);

      final Optional<Node> firstModificationNodeInAmendingLaw = getFirstModification(amendingLaw);
      if (firstModificationNodeInAmendingLaw.isEmpty()) return targetLawClone;

      final Optional<String> oldText =
          getTextToBeReplaced(firstModificationNodeInAmendingLaw.get());
      final Optional<String> newText =
          getNewTextInReplacement(firstModificationNodeInAmendingLaw.get());
      final Optional<String> modificationHref =
          findHrefInModificationNode(firstModificationNodeInAmendingLaw.get());
      final Optional<String> eId = getEIdfromModificationHref(modificationHref.get());

      final Optional<Node> targetLawNodeToBeModified =
          findNodeByEId(eId.get(), targetLawClone.get());
      final String modifiedTextContent =
          targetLawNodeToBeModified
              .get()
              .getTextContent()
              .replaceFirst(oldText.get(), newText.get());

      targetLawNodeToBeModified.get().setTextContent(modifiedTextContent);
      return targetLawClone;
  }

  static Optional<Node> getFirstModification(Document amendingLaw) {
    return XmlFunctions.getNode("//*[local-name()='mod']", amendingLaw);
  }

  static Optional<String> findHrefInModificationNode(Node modificationNode) {
      Optional<Node> optionalNodeHrefAttribute =
          XmlFunctions.getNode("//*[local-name()='ref']/@href", modificationNode);
      
      if (optionalNodeHrefAttribute.isPresent())
        return Optional.of(optionalNodeHrefAttribute.get().getNodeValue());

    return Optional.empty();
  }

  static Optional<String> getEIdfromModificationHref(String modificationHref) {
      final String[] hrefParts = modificationHref.split("/");

      if (hrefParts.length >= 2) {
        final String eId = hrefParts[hrefParts.length - 2];
        return Optional.of(eId);
      }

    return Optional.empty();
  }

  static Optional<Node> findNodeByEId(String eId, Node node) {
    final String xPathExpresion = "//*[@eId='" + eId + "']";
    return XmlFunctions.getNode(xPathExpresion, node);
  }

  static Optional<String> getTextToBeReplaced(Node node) {
    // make sure there are two texts
    final String xPathExpressionSecondNode = "(//*[local-name()='quotedText'])[2]";
    final Optional<Node> optionalSecondNode = XmlFunctions.getNode(xPathExpressionSecondNode, node);
    if (optionalSecondNode.isEmpty()) return Optional.empty();

    // now get the first one
    final String xPathExpresion = "//*[local-name()='quotedText']";
    final Optional<Node> optionalNode = XmlFunctions.getNode(xPathExpresion, node);

    if (optionalNode.isPresent()) {
      final String textToBeReplaced = optionalNode.get().getTextContent();
      return Optional.of(textToBeReplaced);
    }

    return Optional.empty();
  }

  static Optional<String> getNewTextInReplacement(Node node) {
    final String xPathExpression = "(//*[local-name()='quotedText'])[2]";
    final Optional<Node> optionalNode = XmlFunctions.getNode(xPathExpression, node);

    if (optionalNode.isPresent()) {
      final String newText = optionalNode.get().getTextContent();
      return Optional.of(newText);
    }
    return Optional.empty();
  }
}
