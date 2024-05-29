package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/**
 * Represents an article inside a law with various attributes. This class is annotated with Lombok
 * annotations for generating getters, setters, constructors, and builder methods.
 */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Article {
  private final Node node;

  /**
   * Returns a GUID as {@link UUID} from a {@link Node} in a {@link Norm}.
   *
   * @return The GUID of the article
   */
  public Optional<String> getGuid() {
    return NodeParser.getValueFromExpression("./@GUID", this.node);
  }

  /**
   * Returns the enumeration as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The enumeration of the article
   */
  public Optional<String> getEnumeration() {
    return NodeParser.getValueFromExpression("./num/marker/@name", this.node);
  }

  /**
   * Returns the eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the article
   */
  public Optional<String> getEid() {
    return NodeParser.getValueFromExpression("./@eId", this.node);
  }

  /**
   * Returns the heading as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The heading of the article
   */
  public Optional<String> getHeading() {
    return NodeParser.getValueFromExpression("./heading/text()", this.node);
  }

  /**
   * Returns the ELI of the affected document as {@link String} from a {@link Node} in a {@link
   * Norm}.
   *
   * @return The ELI of the affected document of the article
   */
  public Optional<String> getAffectedDocumentEli() {
    return NodeParser.getValueFromExpression(".//affectedDocument/@href", this.node);
  }

  /**
   * Returns the refersTo attribute of the affected article as {@link String} from a {@link Node} in
   * a {@link Norm}.
   *
   * @return The refersTo attribute of the article
   */
  public Optional<String> getRefersTo() {
    return NodeParser.getValueFromExpression("./@refersTo", this.node);
  }
}
