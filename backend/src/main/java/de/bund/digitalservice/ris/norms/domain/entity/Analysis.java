package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

/** Class representing the akn:analysis */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Analysis {

  private final Node node;

  /**
   * Extracts a list of active modifications from the document.
   *
   * @return a list of active modifications.
   */
  public List<TextualMod> getActiveModifications() {
    return NodeParser
      .getNodesFromExpression("./activeModifications/textualMod", node)
      .stream()
      .map(TextualMod::new)
      .toList();
  }

  /**
   * Extracts a list of passive modifications from the document.
   *
   * @return a list of passive modifications.
   */
  public List<TextualMod> getPassiveModifications() {
    return NodeParser
      .getNodesFromExpression("./passiveModifications/textualMod", node)
      .stream()
      .map(TextualMod::new)
      .toList();
  }

  /**
   * Gets the akn:passiveModifications element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:passiveModifications element of the norm
   */
  public Node getOrCreatePassiveModificationsNode() {
    return NodeParser
      .getNodeFromExpression("./passiveModifications", node)
      .orElseGet(() -> NodeCreator.createElementWithEidAndGuid("akn:passiveModifications", node));
  }

  /**
   * Create a new passive modification element
   *
   * @param type the type of the textual mod (this is different from the @refersTo property of an
   *     akn:mod)
   * @param sourceHref the href of the source of the textual mod
   * @param destinationHref the href of the destination of the textual mod
   * @param periodHref the href to the geltungszeitgruppe of the textual mod
   * @param destinationUpTo the upTo of the destination of the textual mod
   * @return the newly create passive modification
   */
  public TextualMod addPassiveModification(
    String type,
    String sourceHref,
    String destinationHref,
    String periodHref,
    String destinationUpTo
  ) {
    var passiveModificationsNode = getOrCreatePassiveModificationsNode();

    var textualMod = NodeCreator.createElementWithEidAndGuid(
      "akn:textualMod",
      passiveModificationsNode
    );
    textualMod.setAttribute("type", type);
    passiveModificationsNode.appendChild(textualMod);

    var source = NodeCreator.createElementWithEidAndGuid("akn:source", textualMod);
    source.setAttribute("href", sourceHref);
    textualMod.appendChild(source);

    var destination = NodeCreator.createElementWithEidAndGuid("akn:destination", textualMod);
    destination.setAttribute("href", destinationHref);
    if (StringUtils.isNotEmpty(destinationUpTo)) {
      destination.setAttribute("upTo", destinationUpTo);
    }
    textualMod.appendChild(destination);

    var force = NodeCreator.createElementWithEidAndGuid("akn:force", textualMod);
    force.setAttribute("period", periodHref);
    textualMod.appendChild(force);

    return new TextualMod(textualMod);
  }
}
