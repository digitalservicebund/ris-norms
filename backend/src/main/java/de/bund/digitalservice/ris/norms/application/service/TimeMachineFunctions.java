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
  public static Document applyTimeMachine(Document amendingLaw, Document targetLaw) {
    return targetLaw;
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
}
