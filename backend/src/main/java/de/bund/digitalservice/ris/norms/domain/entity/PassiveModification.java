package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a passive modification. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class PassiveModification {
  private final Node node;

  /**
   * Returns the eId as {@link String}.
   *
   * @return The eId of the passive modification
   */
  public Optional<String> getEid() {
    return NodeParser.getValueFromExpression("./@eId", this.node);
  }
}
