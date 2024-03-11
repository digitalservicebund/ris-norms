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
   * @param amendingLaw A {@link Document} that contains LDML.de modifications to be applied to the
   *     <code>targetLaw</code>.
   * @param targetLaw The {@link Document} that the modifications will be applied to.
   * @return An {@link Optional} which, on success, contains the {@link Document} that is the result
   *     of applying the <code>amendingLaw</code>'s modifications to the <code>targetLaw</code>. In
   *     case of an error while applying, <code>Optional.empty()</code> is returned.
   */
  public static Optional<Document> applyTimeMachine(
      final Document amendingLaw, final Document targetLaw) {
    try {
      final Document targetLawClone = XmlFunctions.cloneDocument(targetLaw).orElseThrow();

      final Optional<Node> firstModificationNodeInAmendingLaw = getFirstModification(amendingLaw);
      if (firstModificationNodeInAmendingLaw.isEmpty())
        return Optional.of(targetLawClone); // return unmodified

      final String oldText =
          getTextToBeReplaced(firstModificationNodeInAmendingLaw.get()).orElseThrow();
      final String newText =
          getNewTextInReplacement(firstModificationNodeInAmendingLaw.get()).orElseThrow();
      final String modificationHref =
          findHrefInModificationNode(firstModificationNodeInAmendingLaw.get()).orElseThrow();
      final String eId = getEIdfromModificationHref(modificationHref).orElseThrow();
      final Node targetLawNodeToBeModified = findNodeByEId(eId, targetLawClone).orElseThrow();
      final String modifiedTextContent =
          targetLawNodeToBeModified.getTextContent().replaceFirst(oldText, newText);

      // apply modification
      targetLawNodeToBeModified.setTextContent(modifiedTextContent);

      return Optional.of(targetLawClone);
    } catch (Exception e) {
      // There are no requirements on the failure case.
      // We will probably return a tuple of Optional<Document> and some helful information on the
      // error (which may be returned by the API in certain circumstances)
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
