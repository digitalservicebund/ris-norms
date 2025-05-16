package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;

/** Class representing the akn:temporalData */
@Getter
@AllArgsConstructor
public class TemporalData {

  private final Element element;

  /**
   * Returns a list of {@link TemporalGroup} instance from the {@link TemporalData}.
   *
   * @return the list of {@link TemporalGroup}
   */
  public List<TemporalGroup> getTemporalGroups() {
    return NodeParser.getElementsFromExpression("./temporalGroup", element)
      .stream()
      .map(TemporalGroup::new)
      .toList();
  }

  /**
   * Add a new akn:temporalGroup element to this element.
   * @return the newly created {@link TemporalGroup}
   */
  public TemporalGroup addTemporalGroup() {
    return TemporalGroup.createAndAppend(getElement());
  }
}
