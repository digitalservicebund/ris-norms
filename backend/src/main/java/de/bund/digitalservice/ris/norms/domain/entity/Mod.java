package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/**
 * Class representing an akn:mod.
 *
 * <p>DE: Änderungsbefehl
 *
 * <p>Currently only "aenderungsbefehl-ersetzen" is supported.
 */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
public class Mod {
  private final Node node;

  /**
   * Returns the eId as {@link String}.
   *
   * @return The eId of the mod
   */
  public Optional<String> getEid() {
    return EId.fromNode(getNode()).map(EId::value);
  }

  /**
   * Returns the value of the eId as {@link String}.
   *
   * @return the eId of the mod
   */
  public String getMandatoryEid() {
    return NodeParser.getValueFromMandatoryNodeFromExpression("./@eId", this.node);
  }

  /**
   * Returns the quoted text that should be replaced by this mod as {@link String}.
   *
   * @return The text that will be replaced by this mod
   */
  public Optional<String> getOldText() {
    return NodeParser.getValueFromExpression("normalize-space(./quotedText[1])", this.node);
  }

  /**
   * Returns the quoted text that should be replaced by this mod as {@link String}.
   *
   * @return The text that will be replaced by this mod
   */
  public String getMandatoryOldText() {
    return NodeParser.getValueFromMandatoryNodeFromExpression(
        "normalize-space(./quotedText[1])", this.node);
  }

  /**
   * Sets a quoted text that should be replaced by this mod as {@link String}.
   *
   * @param replacementText the text that should be replaced by this modification
   */
  public void setOldText(String replacementText) {
    NodeParser.getNodeFromExpression("./quotedText[1]", this.node)
        .orElseThrow()
        .setTextContent(replacementText);
  }

  /**
   * Returns the quoted text that will be the new text after the mod is applied as {@link String}.
   *
   * @return The text that will replace the old text
   */
  public Optional<String> getNewText() {
    return NodeParser.getValueFromExpression("normalize-space(./quotedText[2])", this.node);
  }

  /**
   * Returns the href of the target law that is modified.
   *
   * @return The href of the akn:ref of the akn:mod.
   */
  public Optional<Href> getTargetHref() {
    return NodeParser.getValueFromExpression("./ref/@href", this.node).map(Href::new);
  }

  /**
   * Updates the href attribute of akn:ref node within the akn:mode of the body.
   *
   * @param newHref - the new ELI + eId of the target law
   */
  public void setTargetHref(final String newHref) {
    NodeParser.getNodeFromExpression("./ref", this.node)
        .orElseThrow()
        .getAttributes()
        .getNamedItem("href")
        .setNodeValue(newHref);
  }

  /**
   * Updates the quoted text that will be used to replace the old text once the mod is applied.
   *
   * @param newContent - the replacing text
   */
  public void setNewText(final String newContent) {
    final Node newContentNode =
        NodeParser.getNodeFromExpression("./quotedText[2]", this.node).orElseThrow();
    newContentNode.setTextContent(newContent);
  }

  /**
   * Updates the quoted structure that will be used to replace the node once the mod is applied.
   *
   * @param newContent - the replacing text
   */
  public void updateWithQuotedStructure(String newContent) {
    final Node oldQuotedStructure =
        NodeParser.getNodeFromExpression("./quotedStructure", this.node).orElseThrow();
    final Node quotedStructureParent = oldQuotedStructure.getParentNode();
    final Node newQuotedStructure = XmlMapper.toNode(newContent);

    final Node newQuotedStructureImported =
        oldQuotedStructure.getOwnerDocument().importNode(newQuotedStructure, true);
    quotedStructureParent.replaceChild(newQuotedStructureImported, oldQuotedStructure);
  }

  /**
   * Checks weather a quotedText was used for a substitution. If not it is probably a
   * quotedStructure.
   *
   * @return is it using a quotedText structure
   */
  public boolean usesQuotedText() {
    final Optional<Node> newContentNode =
        NodeParser.getNodeFromExpression("./quotedText", this.node);
    return newContentNode.isPresent();
  }

  /**
   * Checks weather a quotedStructure was used for a substitution.
   *
   * @return is it using a quotedStructure
   */
  public boolean usesQuotedStructure() {
    final Optional<Node> newContentNode =
        NodeParser.getNodeFromExpression("./quotedStructure", this.node);
    return newContentNode.isPresent();
  }
}
