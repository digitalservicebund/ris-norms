package de.bund.digitalservice.ris.norms.application.service;

import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/** 
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying
 * LDML.de "modifications" to LDML.de files.
 * For details on LDML.de modifications, cf. https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads
 */
public class TimeMachineFunctions {

  /**
   * Applies the modifications of the amending law onto the target law.
   *
   * @param amendingLaw An Document that contains LDML.de modifications to be applied on the target law
   * @param targetLaw The Document that the modifications will be applied to
   * @return the Document that results in applying the amending law's modifications to the target law
   */
  public static Optional<Document> applyTimeMachine(
    final Document amendingLaw, final Document targetLaw) {
      // TODO: cover all individual failures in tests
      try {
        final Optional<Document> targetLawClone = XmlFunctions.cloneDocument(targetLaw);
        
        final Optional<Node> firstModificationNodeInAmendingLaw =
          TimeMachineFunctions.getFirstModification(amendingLaw);
        final Optional<String> oldText = getTextToBeReplaced(firstModificationNodeInAmendingLaw.get());
        final Optional<String> newText = getNewTextInReplacement(firstModificationNodeInAmendingLaw.get());
        final Optional<String> modificationHref =
          XmlFunctions.findHrefInModificationNode(firstModificationNodeInAmendingLaw.get());
        final Optional<String> eId =
          TimeMachineFunctions.getEIdfromModificationHref(modificationHref.get());

        final Optional<Node> targetLawNodeToBeModified = findNodeByEId(eId.get(), targetLawClone.get());
        final String modifiedTextContent =
        targetLawNodeToBeModified.get().getTextContent().replaceFirst(oldText.get(), newText.get());
        
        targetLawNodeToBeModified.get().setTextContent(modifiedTextContent);
        return targetLawClone;

      } catch (Exception e) {
        // TODO: probably do something with the exception
        System.out.println("applyTimeMachine throws: " + e.toString());
      }
    return Optional.empty();
  }

  static Optional<Node> getFirstModification(Document amendingLaw) {
    Optional<Node> optionalNode = XmlFunctions.getNode("//*[local-name()='mod']", amendingLaw);
    return optionalNode;
  }

  static Optional<String> getEIdfromModificationHref(String modificationHref) {
    try {
      final String[] hrefParts = modificationHref.split("/");
      final String eId = hrefParts[hrefParts.length - 2];
      return Optional.of(eId);
    } catch (Exception e) {
      // TODO: Probably do something with the exception. Logging?
    }

    return Optional.empty();
  }

  static Optional<Node> findNodeByEId(String eId, Node node) {
    final String xPathExpresion = "//*[@eId='" + eId + "']";
    final Optional<Node> optionalNode = XmlFunctions.getNode(xPathExpresion, node);
    return optionalNode;
  }

  static Optional<String> getTextToBeReplaced(Node node) {
    // make sure there are two texts
    final String xPathExpressionSecondNode = "(//*[local-name()='quotedText'])[2]";
    final Optional<Node> optionalSecondNode = XmlFunctions.getNode(xPathExpressionSecondNode, node);
    if (optionalSecondNode.isEmpty())
      return Optional.empty();

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
