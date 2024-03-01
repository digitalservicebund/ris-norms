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
    // TODO: requires getNode() in xmlFunctions
    return Optional.empty();
  }
}
