package de.bund.digitalservice.ris.norms.domain.entity;

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
  private static final String UNKNOWN = "UNKNOWN";
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
    return EId.fromNode(getNode()).map(EId::value);
  }

  /**
   * Returns the eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the article
   */
  public String getMandatoryEid() {
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
   * Norm}.
   *
   * @return The ELI of the affected document of the article
   */
  public Optional<String> getAffectedDocumentEli() {
    return NodeParser.getValueFromExpression(".//affectedDocument/@href", this.node);
  }

  /**
   * Sets a new href for the affected document
   *
   * @param href The ELI of the affected document of the article
   */
  public void setAffectedDocumentEli(String href) {
    Optional<Node> articleAffectedDocument =
        NodeParser.getNodeFromExpression(".//affectedDocument/@href", this.node);
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
   * Extracts the {@link Mod} for this article and throws an exception when an article does not
   * contain mods.
   *
   * @return the {@link Mod}
   */
  public List<Mod> getModsOrThrow() {
    List<Mod> modsInArticle = getMods();
    if (modsInArticle.isEmpty()) {
      throw new NullPointerException(
          "For norm with Eli (%s): There is no mod in article with eId %s"
              .formatted(getNormEli().orElse(UNKNOWN), getEid().orElse(UNKNOWN)));
    } else return modsInArticle;
  }

  private Optional<String> getNormEli() {
    Optional<String> eli =
        NodeParser.getValueFromExpression("//FRBRExpression/FRBRthis/@value", node);
    if (eli.isEmpty()) {
      eli =
          NodeParser.getValueFromExpression("//FRBRManifestation/FRBRthis/@value", node)
              .map(m -> m.replace(".xml", ""));
    }
    return eli;
  }
}
