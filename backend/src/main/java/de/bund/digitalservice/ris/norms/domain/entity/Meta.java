package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;

/** Class representing the akn:meta */
@Getter
@AllArgsConstructor
public class Meta {

  private final Element element;

  /**
   * Gets the akn:proprietary element of the norm.
   *
   * @return {@link Optional} with the {@link Proprietary} metadata of the norm.
   */
  public Optional<Proprietary> getProprietary() {
    return NodeParser.getElementFromExpression("./proprietary", element).map(Proprietary::new);
  }
}
