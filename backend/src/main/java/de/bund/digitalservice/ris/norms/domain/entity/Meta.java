package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
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

  /**
   * Returns a {@link FRBRWork} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the FRBRWork node as {@link FRBRWork}
   */
  public FRBRWork getFRBRWork() {
    return new FRBRWork(
        NodeParser.getMandatoryNodeFromExpression("./identification/FRBRWork", node));
  }

  /**
   * Returns a {@link FRBRExpression} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the FRBRExpression node as {@link FRBRExpression}
   */
  public FRBRExpression getFRBRExpression() {
    return new FRBRExpression(
        NodeParser.getMandatoryNodeFromExpression("./identification/FRBRExpression", node));
  }

  /**
   * Returns a {@link FRBRManifestation} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the FRBRManifestation node as {@link FRBRManifestation}
   */
  public FRBRManifestation getFRBRManifestation() {
    return new FRBRManifestation(
        NodeParser.getMandatoryNodeFromExpression("./identification/FRBRManifestation", node));
  }

  /**
   * Returns a {@link TemporalData} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the TemporalData node as {@link TemporalData}
   */
  public TemporalData getTemporalData() {
    return new TemporalData(NodeParser.getMandatoryNodeFromExpression("./temporalData", node));
  }

  /**
   * Returns a {@link Lifecycle} instance from a {@link Node} in a {@link Meta}.
   *
   * @return the Lifecycle node as {@link Lifecycle}
   */
  public Lifecycle getLifecycle() {
    return new Lifecycle(NodeParser.getMandatoryNodeFromExpression("./lifecycle", node));
  }
}
