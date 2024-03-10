package de.bund.digitalservice.ris.norms.application.service;

import java.util.Optional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying LDML.de
 * "modifications" to LDML.de files. For details on LDML.de modifications, cf. the <a
 * href="https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads">LDML.de
 * Documentation</a>
 */
public class TimeMachineFunctions {

  // prevent default constructor
  private TimeMachineFunctions() {}

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
    try {

      final Document targetLawClone = XmlFunctions.cloneDocument(targetLaw).orElseThrow();

      final Optional<Node> firstModificationNodeInAmendingLaw = getFirstModification(amendingLaw);
      if (firstModificationNodeInAmendingLaw.isEmpty())
        return Optional.of(targetLawClone); // return unmodified

      final Optional<String> oldText =
          getTextToBeReplaced(firstModificationNodeInAmendingLaw.get());
      final Optional<String> newText =
          getNewTextInReplacement(firstModificationNodeInAmendingLaw.get());
      // not sure how to trigger the "newText" condition separately.
      if (oldText.isEmpty() || newText.isEmpty()) return Optional.empty();

      final Optional<String> modificationHref =
          findHrefInModificationNode(firstModificationNodeInAmendingLaw.get());
      if (modificationHref.isEmpty()) return Optional.empty();

      final Optional<String> eId = getEIdfromModificationHref(modificationHref.get());
      if (eId.isEmpty()) return Optional.empty();

      final Optional<Node> targetLawNodeToBeModified = findNodeByEId(eId.get(), targetLawClone);
      if (targetLawNodeToBeModified.isEmpty()) return Optional.empty();

      final String modifiedTextContent =
          targetLawNodeToBeModified
              .get()
              .getTextContent()
              .replaceFirst(oldText.get(), newText.get());
      targetLawNodeToBeModified.get().setTextContent(modifiedTextContent);

      return Optional.of(targetLawClone);
    } catch (Exception e) {
      // no requiremnt wrt. failure; we will probably return a tuple of Optional<Document> and some
      // helful error message
      return Optional.empty();
    }
  }

  static Optional<Node> getFirstModification(Document amendingLaw) {
    return XmlFunctions.getNodeByXPath("//*[local-name()='mod']", amendingLaw);
  }

  static Optional<String> findHrefInModificationNode(Node modificationNode) {
    Optional<Node> optionalNodeHrefAttribute =
        XmlFunctions.getNodeByXPath("//*[local-name()='ref']/@href", modificationNode);

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
    return XmlFunctions.getNodeByXPath(xPathExpresion, node);
  }

  static Optional<String> getTextToBeReplaced(Node node) {
    // make sure there are two texts
    final String xPathExpressionSecondNode = "(//*[local-name()='quotedText'])[2]";
    final Optional<Node> optionalSecondNode =
        XmlFunctions.getNodeByXPath(xPathExpressionSecondNode, node);
    if (optionalSecondNode.isEmpty()) return Optional.empty();

    // now get the first one
    final String xPathExpresion = "//*[local-name()='quotedText']";
    final Optional<Node> optionalNode = XmlFunctions.getNodeByXPath(xPathExpresion, node);

    if (optionalNode.isPresent()) {
      final String textToBeReplaced = optionalNode.get().getTextContent();
      return Optional.of(textToBeReplaced);
    }

    return Optional.empty();
  }

  static Optional<String> getNewTextInReplacement(Node node) {
    final String xPathExpression = "(//*[local-name()='quotedText'])[2]";
    final Optional<Node> optionalNode = XmlFunctions.getNodeByXPath(xPathExpression, node);

    if (optionalNode.isPresent()) {
      final String newText = optionalNode.get().getTextContent();
      return Optional.of(newText);
    }
    return Optional.empty();
  }
}
