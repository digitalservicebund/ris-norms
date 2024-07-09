package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the akn:temporalData */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TemporalData {

  private final Node node;

  /**
   * Returns a list of {@link TemporalGroup} instance from the {@link TemporalData}.
   *
   * @return the list of {@link TemporalGroup}
   */
  public List<TemporalGroup> getTemporalGroups() {
    return NodeParser.getNodesFromExpression("./temporalGroup", node).stream()
        .map(TemporalGroup::new)
        .toList();
  }
}
