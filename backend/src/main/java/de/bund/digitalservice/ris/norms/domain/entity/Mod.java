package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/**
 * Class representing an akn:mod. DE: Änderungsbefehl Currently only "aenderungsbefehl-ersetzen" is
 * supported.
 */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Mod {
  private final Node node;

  /**
   * Returns the eId as {@link String}.
   *
   * @return The eId of the mod
   */
  public Optional<String> getEid() {
    return NodeParser.getValueFromExpression("./@eId", this.node);
  }

  /**
   * Returns the quoted text that should be replaced by this mod as {@link String}.
   *
   * @return The text that will be replaced by this mod
   */
  public Optional<String> getOldText() {
    return NodeParser.getValueFromExpression("./quotedText[1]", this.node);
  }

  /**
   * Returns the quoted text that will be the new text after the mod is applied as {@link String}.
   *
   * @return The text that will replace the old text
   */
  public Optional<String> getNewText() {
    return NodeParser.getValueFromExpression("./quotedText[2]", this.node);
  }
}
