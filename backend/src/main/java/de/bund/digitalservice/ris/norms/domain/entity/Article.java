package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
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

  private static final String AFFECTED_DOCUMENT_XPATH = ".//affectedDocument/@href";

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
    return NodeParser.getValueFromExpression("./num/text()", this.node).map(String::trim);
  }

  /**
   * Returns the eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the article
   */
  public String getEid() {
    return EId.fromMandatoryNode(getNode()).value();
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
   * Norm}. If not present returns an Optional.empty
   *
   * @return The ELI of the affected document of the article
   */
  public Optional<ExpressionEli> getAffectedDocumentEli() {
    return NodeParser
      .getValueFromExpression(AFFECTED_DOCUMENT_XPATH, this.node)
      .map(ExpressionEli::fromString);
  }

  /**
   * Returns the ELI of the affected document as {@link String} from a {@link Node} in a {@link
   * Norm}.
   *
   * @return The ELI of the affected document of the article
   */
  public ExpressionEli getMandatoryAffectedDocumentEli() {
    return ExpressionEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression(AFFECTED_DOCUMENT_XPATH, this.node)
    );
  }

  /**
   * Sets a new href for the affected document
   *
   * @param href The ELI of the affected document of the article
   */
  public void setAffectedDocumentEli(String href) {
    Optional<Node> articleAffectedDocument = NodeParser.getNodeFromExpression(
      AFFECTED_DOCUMENT_XPATH,
      this.node
    );
    articleAffectedDocument.ifPresent(value -> value.setTextContent(href));
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

  /**
   * Extracts the {@link Mod} for this article.
   *
   * @return the {@link Mod}
   */
  public List<Mod> getMods() {
    return NodeParser.getNodesFromExpression("./*//mod", this.node).stream().map(Mod::new).toList();
  }

  /**
   * Checks weather the {@link Article} refers to a Geltungszeitregel (which would define when a law e.g. enters
   * into force)
   *
   * @return {@link Boolean}
   */
  public Boolean isGeltungszeitregel() {
    return getRefersTo().orElse("").equals("geltungszeitregel");
  }
}
