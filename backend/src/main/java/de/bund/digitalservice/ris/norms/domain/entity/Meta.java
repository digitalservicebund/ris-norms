package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
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
      NodeParser.getMandatoryElementFromExpression("./identification/FRBRWork", node)
    );
  }

  /**
   * Returns a {@link FRBRExpression} instance from the {@link Meta}.
   *
   * @return the FRBRExpression node as {@link FRBRExpression}
   */
  public FRBRExpression getFRBRExpression() {
    return new FRBRExpression(
      NodeParser.getMandatoryElementFromExpression("./identification/FRBRExpression", node)
    );
  }

  /**
   * Returns a {@link FRBRManifestation} instance from the {@link Meta}.
   *
   * @return the FRBRManifestation node as {@link FRBRManifestation}
   */
  public FRBRManifestation getFRBRManifestation() {
    return new FRBRManifestation(
      NodeParser.getMandatoryElementFromExpression("./identification/FRBRManifestation", node)
    );
  }

  /**
   * Returns a {@link TemporalData} instance from the {@link Meta}.
   *
   * @return the TemporalData node as {@link TemporalData}
   */
  public TemporalData getTemporalData() {
    return new TemporalData(NodeParser.getMandatoryElementFromExpression("./temporalData", node));
  }

  /**
   * Gets the akn:temporalData element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:temporalData element of the norm
   */
  public TemporalData getOrCreateTemporalData() {
    try {
      return getTemporalData();
    } catch (final MandatoryNodeNotFoundException e) {
      final var newElement = NodeCreator.createElementWithEidAndGuid("akn:temporalData", node);
      newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      node.insertBefore(newElement, getOrCreateProprietary().getNode());
      return new TemporalData(newElement);
    }
  }

  /**
   * Returns a {@link Lifecycle} instance from the {@link Meta}.
   *
   * @return the Lifecycle node as {@link Lifecycle}
   */
  public Lifecycle getLifecycle() {
    return new Lifecycle(NodeParser.getMandatoryElementFromExpression("./lifecycle", node));
  }

  /**
   * Gets the akn:lifecycle element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:lifecycle element of the norm
   */
  public Lifecycle getOrCreateLifecycle() {
    try {
      return getLifecycle();
    } catch (final MandatoryNodeNotFoundException e) {
      final var newElement = NodeCreator.createElementWithEidAndGuid("akn:lifecycle", node);
      newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      node.insertBefore(newElement, getOrCreateProprietary().getNode());
      return new Lifecycle(newElement);
    }
  }

  /**
   * Returns a {@link Analysis} instance from the {@link Meta}.
   *
   * @return the Analysis node as {@link Analysis}
   */
  public Optional<Analysis> getAnalysis() {
    return NodeParser.getElementFromExpression("./analysis", node).map(Analysis::new);
  }

  /**
   * Gets the akn:analysis element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:analysis element of the norm
   */
  public Analysis getOrCreateAnalysis() {
    return getAnalysis()
      .orElseGet(() -> {
        final var newElement = NodeCreator.createElementWithEidAndGuid("akn:analysis", node);
        newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);

        // Metadata needs to be in the correct order, so we're inserting it before temporal data, which is the
        // element that has to follow the analysis in a valid document.
        final var insertInOrderSiblibg = getOrCreateTemporalData().getNode();
        node.insertBefore(newElement, insertInOrderSiblibg);

        return new Analysis(newElement);
      });
  }

  /**
   * Gets the akn:proprietary element of the norm, or creates it if it does not yet exist.
   *
   * @return {@link Proprietary} metadata of the norm.
   */
  public Proprietary getOrCreateProprietary() {
    return this.getProprietary()
      .orElseGet(() -> {
        final var newElement = NodeCreator.createElementWithEidAndGuid("akn:proprietary", node);
        newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
        return new Proprietary(newElement);
      });
  }

  /**
   * Gets the akn:proprietary element of the norm.
   *
   * @return {@link Optional} with the {@link Proprietary} metadata of the norm.
   */
  public Optional<Proprietary> getProprietary() {
    return NodeParser.getElementFromExpression("./proprietary", node).map(Proprietary::new);
  }
}
