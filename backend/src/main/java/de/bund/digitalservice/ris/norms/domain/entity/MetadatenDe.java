package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the meta:legalDocML.de_metadaten */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class MetadatenDe {
  private final Node node;

  /**
   * Returns the FNA ("Fundstellennachweis A") of the norm.
   *
   * @return FNA or empty if it doesn't exist.
   */
  public Optional<String> getFna() {
    return NodeParser.getValueFromExpression("./fna", node);
  }
}