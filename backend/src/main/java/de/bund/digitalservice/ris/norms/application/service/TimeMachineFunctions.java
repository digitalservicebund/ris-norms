package de.bund.digitalservice.ris.norms.application.service;

import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/** TODO */
public class TimeMachineFunctions {

  /**
   * TODO
   *
   * @param amendingLaw
   * @param targetLaw
   * @return TODO
   */
  public static Optional<Document> applyTimeMachine(Document amendingLaw, Document targetLaw) {
    return Optional.of(targetLaw);
  }

  static Optional<Node> getFirstModification(Document amendingLaw){
    Optional<Node> optionalNode = XmlFunctions.getNode("//*[local-name()='mod']", amendingLaw);
    return optionalNode;
  }

  static Optional<String> getEIdfromModificationHref(String modificationEli) {
    try {
      final String[] hrefParts = modificationEli.split("/");
      final String eId = hrefParts[hrefParts.length - 2];
      return Optional.of(eId);
    } catch (Exception e) {
      // TODO: Probably do something with the exception. Logging?
    }

    return Optional.empty();
  }

  static Optional<Node> findNodeByEId(String eId, Node node){
    final String xPathExpresion = "//*[@eId='" + eId + "']";
    final Optional<Node> optionalNode = XmlFunctions.getNode(xPathExpresion, node);
    return optionalNode;
  }

  static Optional<String> getTextToBeReplaced(Node node){
    final String xPathExpresion = "//*[local-name()='quotedText']";
    final Optional<Node> optionalNode = XmlFunctions.getNode(xPathExpresion, node);

    if (optionalNode.isPresent()){
      final String textToBeReplaced = optionalNode.get().getTextContent();
      return Optional.of(textToBeReplaced);
    }

    return Optional.empty();
  }

  static Optional<String> getNewTextInReplacement(Node node) {
    final String xPathExpression = "(//*[local-name()='quotedText'])[2]";
    final Optional<Node> optionalNode = XmlFunctions.getNode(xPathExpression, node);
    
    if (optionalNode.isPresent()){
      final String newText = optionalNode.get().getTextContent();
      return Optional.of(newText);
    }
    return Optional.empty();
  }
}
