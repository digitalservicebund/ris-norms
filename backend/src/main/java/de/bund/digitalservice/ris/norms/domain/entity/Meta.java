package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exception.MandatoryNodeNotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the akn:meta */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Meta {
  private static final String ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT =
      "attributsemantik-noch-undefiniert";

  private static final String SOURCE_ATTIBUTE = "source";

  private final Node node;

  /**
   * Returns a {@link FRBRWork} instance from the {@link Meta}.
   *
   * @return the FRBRWork node as {@link FRBRWork}
   */
  public FRBRWork getFRBRWork() {
    return new FRBRWork(
        NodeParser.getMandatoryNodeFromExpression("./identification/FRBRWork", node));
  }

  /**
   * Returns a {@link FRBRExpression} instance from the {@link Meta}.
   *
   * @return the FRBRExpression node as {@link FRBRExpression}
   */
  public FRBRExpression getFRBRExpression() {
    return new FRBRExpression(
        NodeParser.getMandatoryNodeFromExpression("./identification/FRBRExpression", node));
  }

  /**
   * Returns a {@link FRBRManifestation} instance from the {@link Meta}.
   *
   * @return the FRBRManifestation node as {@link FRBRManifestation}
   */
  public FRBRManifestation getFRBRManifestation() {
    return new FRBRManifestation(
        NodeParser.getMandatoryNodeFromExpression("./identification/FRBRManifestation", node));
  }

  /**
   * Returns a {@link TemporalData} instance from the {@link Meta}.
   *
   * @return the TemporalData node as {@link TemporalData}
   */
  public TemporalData getTemporalData() {
    return new TemporalData(NodeParser.getMandatoryNodeFromExpression("./temporalData", node));
  }

  /**
   * Gets the akn:temporalData element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:temporalData element of the norm
   */
  public TemporalData getOrCreateTemporalDataNode() {
    try {
      return getTemporalData();
    } catch (final MandatoryNodeNotFoundException e) {
      final var newElement =
          NodeCreator.createElementWithEidAndGuid("akn:temporalData", "analysis", node);
      newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      return new TemporalData(newElement);
    }
  }

  /**
   * Returns a {@link Lifecycle} instance from the {@link Meta}.
   *
   * @return the Lifecycle node as {@link Lifecycle}
   */
  public Lifecycle getLifecycle() {
    return new Lifecycle(NodeParser.getMandatoryNodeFromExpression("./lifecycle", node));
  }

  /**
   * Returns a {@link Analysis} instance from the {@link Meta}.
   *
   * @return the Analysis node as {@link Analysis}
   */
  public Optional<Analysis> getAnalysis() {
    return NodeParser.getNodeFromExpression("./analysis", node).map(Analysis::new);
  }

  /**
   * Gets the akn:analysis element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:analysis element of the norm
   */
  public Analysis getOrCreateAnalysis() {
    return getAnalysis()
        .orElseGet(
            () -> {
              final var newElement =
                  NodeCreator.createElementWithEidAndGuid("akn:analysis", "analysis", node);
              newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
              return new Analysis(newElement);
            });
  }

  /**
   * Gets the akn:proprietary element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:proprietary element of the norm
   */
  public Proprietary getOrCreateProprietary() {
    return NodeParser.getNodeFromExpression("./proprietary", node)
        .map(Proprietary::new)
        .orElseGet(
            () -> {
              final var newElement =
                  NodeCreator.createElementWithEidAndGuid("akn:proprietary", "proprietary", node);
              newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
              return new Proprietary(newElement);
            });
  }
}
