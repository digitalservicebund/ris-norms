package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;

/** Class representing the akn:meta */
@Getter
@AllArgsConstructor
public class Meta {

  private static final String ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT =
    "attributsemantik-noch-undefiniert";

  private static final String SOURCE_ATTIBUTE = "source";

  private final Element element;

  /**
   * Returns a {@link FRBRWork} instance from the {@link Meta}.
   *
   * @return the FRBRWork node as {@link FRBRWork}
   */
  public FRBRWork getFRBRWork() {
    return new FRBRWork(
      NodeParser.getMandatoryElementFromExpression("./identification/FRBRWork", element)
    );
  }

  /**
   * Returns a {@link FRBRExpression} instance from the {@link Meta}.
   *
   * @return the FRBRExpression node as {@link FRBRExpression}
   */
  public FRBRExpression getFRBRExpression() {
    return new FRBRExpression(
      NodeParser.getMandatoryElementFromExpression("./identification/FRBRExpression", element)
    );
  }

  /**
   * Returns a {@link FRBRManifestation} instance from the {@link Meta}.
   *
   * @return the FRBRManifestation node as {@link FRBRManifestation}
   */
  public FRBRManifestation getFRBRManifestation() {
    return new FRBRManifestation(
      NodeParser.getMandatoryElementFromExpression("./identification/FRBRManifestation", element)
    );
  }

  /**
   * Returns a {@link TemporalData} instance from the {@link Meta}.
   *
   * @return the TemporalData node as {@link TemporalData}
   */
  public TemporalData getTemporalData() {
    return new TemporalData(
      NodeParser.getMandatoryElementFromExpression("./temporalData", element)
    );
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
      final var newElement = NodeCreator.createElementWithEidAndGuid("akn:temporalData", element);
      newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      element.insertBefore(newElement, getOrCreateProprietary().getElement());
      return new TemporalData(newElement);
    }
  }

  /**
   * Returns a {@link Lifecycle} instance from the {@link Meta}.
   *
   * @return the Lifecycle node as {@link Lifecycle}
   */
  public Lifecycle getLifecycle() {
    return new Lifecycle(NodeParser.getMandatoryElementFromExpression("./lifecycle", element));
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
      final var newElement = NodeCreator.createElementWithEidAndGuid("akn:lifecycle", element);
      newElement.setAttribute(SOURCE_ATTIBUTE, ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT);
      element.insertBefore(newElement, getOrCreateProprietary().getElement());
      return new Lifecycle(newElement);
    }
  }

  /**
   * Gets the akn:proprietary element of the norm, or creates it if it does not yet exist.
   *
   * @return {@link Proprietary} metadata of the norm.
   */
  public Proprietary getOrCreateProprietary() {
    return this.getProprietary().orElseGet(() -> {
        final var newElement = NodeCreator.createElementWithEidAndGuid("akn:proprietary", element);
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
    return NodeParser.getElementFromExpression("./proprietary", element).map(Proprietary::new);
  }
}
