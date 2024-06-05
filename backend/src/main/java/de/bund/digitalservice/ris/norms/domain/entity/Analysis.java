package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
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
    return NodeParser.getNodesFromExpression("./activeModifications/textualMod", node).stream()
        .map(TextualMod::new)
        .toList();
  }

  /**
   * Extracts a list of passive modifications from the document.
   *
   * @return a list of passive modifications.
   */
  public List<TextualMod> getPassiveModifications() {
    return NodeParser.getNodesFromExpression("./passiveModifications/textualMod", node).stream()
        .map(TextualMod::new)
        .toList();
  }
}
