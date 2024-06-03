package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFound;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the akn:metadata */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Meta {

  private final Node node;
  private final String normEli;

  private static final String NODE_NAME = Meta.class.getSimpleName();

  /**
   * Returns a {@link FRBRExpression} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the FRBRExpression node as {@link FRBRExpression}
   */
  public FRBRExpression getFRBRExpression() {
    final String xpath = "./identification/FRBRExpression";
    return NodeParser.getNodeFromExpression(xpath, node)
        .map(expressionNode -> new FRBRExpression(expressionNode, normEli))
        .orElseThrow(() -> new MandatoryNodeNotFound(xpath, NODE_NAME, this.normEli));
  }

  /**
   * Returns a {@link FRBRManifestation} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the FRBRManifestation node as {@link FRBRManifestation}
   */
  public FRBRManifestation getFRBRManifestation() {
    final String xpath = "./identification/FRBRManifestation";
    return NodeParser.getNodeFromExpression(xpath, node)
        .map(manifestationNode -> new FRBRManifestation(manifestationNode, normEli))
        .orElseThrow(() -> new MandatoryNodeNotFound(xpath, NODE_NAME, this.normEli));
  }
}
