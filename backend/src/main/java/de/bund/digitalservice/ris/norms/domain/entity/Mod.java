package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;
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

  private static final String REF_XPATH = "./ref";
  private static final String RREF_XPATH = "./rref";

  /**
   * Returns the eId as {@link String}.
   *
   * @return The eId of the mod
   */
  public String getEid() {
    return EId.fromMandatoryNode(getNode()).value();
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
      "normalize-space(./quotedText[1])",
      this.node
    );
  }

  /**
   * Sets a quoted text that should be replaced by this mod as {@link String}.
   *
   * @param replacementText the text that should be replaced by this modification
   */
  public void setOldText(String replacementText) {
    NodeParser
      .getNodeFromExpression("./quotedText[1]", this.node)
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
   * Returns the href of the akn:ref of the target law that is modified.
   *
   * @return The href of the akn:ref of the akn:mod.
   */
  public Optional<Href> getTargetRefHref() {
    return NodeParser.getValueFromExpression(REF_XPATH + "/@href", this.node).map(Href::new);
  }

  /**
   * Updates the href attribute of akn:ref node within the akn:mode of the body.
   *
   * @param newHref - the new ELI + eId of the target law
   */
  public void setTargetRefHref(final Href newHref) {
    NodeParser
      .getNodeFromExpression(REF_XPATH, this.node)
      .orElseThrow()
      .getAttributes()
      .getNamedItem("href")
      .setNodeValue(newHref.toString());
  }

  /**
   * Returns the from of the akn:rref of the target law that is modified.
   *
   * @return The from of the akn:rref of the akn:mod.
   */
  public Optional<Href> getTargetRrefFrom() {
    return NodeParser.getValueFromExpression(RREF_XPATH + "/@from", this.node).map(Href::new);
  }

  /**
   * Updates the from attribute of akn:rref node within the akn:mode of the body.
   *
   * @param newFrom - the new ELI + eId of the target law
   */
  public void setTargetRrefFrom(final Href newFrom) {
    NodeParser
      .getNodeFromExpression(RREF_XPATH, this.node)
      .orElseThrow()
      .getAttributes()
      .getNamedItem("from")
      .setNodeValue(newFrom.toString());
  }

  /**
   * Updates the quoted text that will be used to replace the old text once the mod is applied.
   *
   * @param updatedNewContent - the replacing text
   */
  public void setNewText(final String updatedNewContent) {
    final Node newContentNode = NodeParser
      .getNodeFromExpression("./quotedText[2]", this.node)
      .orElseThrow();
    List<Node> children = NodeParser.nodeListToList(newContentNode.getChildNodes());
    Optional<Node> refNode = children
      .stream()
      .filter(child -> child.getNodeName().equals("akn:ref"))
      .filter(Node::hasChildNodes)
      .filter(ref -> ref.getFirstChild().getNodeType() == Node.TEXT_NODE)
      .findFirst();
    if (refNode.isPresent()) {
      children
        .stream()
        .filter(child -> child.getNodeType() == Node.TEXT_NODE)
        .forEach(newContentNode::removeChild);
      refNode.get().setTextContent(updatedNewContent);
    } else {
      newContentNode.setTextContent(updatedNewContent);
    }
    // Somehow tell the user that the reference might have been changed
  }

  /**
   * Returns the quoted structure as {@link Node}.
   *
   * @return The quotedStructure which content will be replaced
   */
  public Optional<Node> getQuotedStructure() {
    return NodeParser.getNodeFromExpression("./quotedStructure", this.node);
  }

  /**
   * Returns the second quoted text as {@link Node}.
   *
   * @return The second quoted text
   */
  public Optional<Node> getSecondQuotedText() {
    return NodeParser.getNodeFromExpression("./quotedText[2]", this.node);
  }

  /**
   * Checks whether the mod contains an akn:ref
   *
   * @return true or false
   */
  public boolean containsRef() {
    final Optional<Node> quotedTextNode = getSecondQuotedText();
    final Optional<Node> quotedStructureNode = getQuotedStructure();
    if (quotedTextNode.isPresent()) {
      return NodeParser.getNodeFromExpression(REF_XPATH, quotedTextNode.get()).isPresent();
    } else if (quotedStructureNode.isPresent()) {
      return !NodeParser.getNodesFromExpression(".//quotedStructure//ref", this.node).isEmpty();
    }
    return false;
  }

  /**
   * Checks whether a quotedText was used for a substitution. If not it is probably a
   * quotedStructure.
   *
   * @return is it using a quotedText structure
   */
  public boolean usesQuotedText() {
    final Optional<Node> newContentNode = NodeParser.getNodeFromExpression(
      "./quotedText",
      this.node
    );
    return newContentNode.isPresent();
  }

  /**
   * Checks whether a quotedStructure was used for a substitution.
   *
   * @return is it using a quotedStructure
   */
  public boolean usesQuotedStructure() {
    final Optional<Node> newContentNode = NodeParser.getNodeFromExpression(
      "./quotedStructure",
      this.node
    );
    return newContentNode.isPresent();
  }

  /**
   * Checks whether the mod has a range ref (rref)
   *
   * @return whether the mod has a range ref (rref)
   */
  public boolean hasRref() {
    final Optional<Node> rangeRefNode = NodeParser.getNodeFromExpression(RREF_XPATH, this.node);
    return rangeRefNode.isPresent();
  }

  /**
   * Returns the upTo of the akn:rref of the target law that is modified.
   *
   * @return The upTo of the akn:rref of the akn:mod.
   */
  public Optional<Href> getTargetRrefUpTo() {
    return NodeParser.getValueFromExpression("./rref/@upTo", this.node).map(Href::new);
  }

  /**
   * Updates the range ref UpTo attribute
   *
   * @param destinationUpTo - the UpTo attribute that should be updated
   */
  public void setTargetRrefUpTo(final Href destinationUpTo) {
    NodeParser
      .getNodeFromExpression(RREF_XPATH, this.node)
      .orElseThrow()
      .getAttributes()
      .getNamedItem("upTo")
      .setNodeValue(destinationUpTo.toString());
  }

  /**
   * Replaces an akn:ref with an akn:rref and updates the href and upTo attributes with the given
   * values. It also copies for now the text content of the old node.
   *
   * @param destinationFrom the new destination from
   * @param destinationUpTo the new destination upTo
   */
  public void replaceRefWithRref(final Href destinationFrom, final Href destinationUpTo) {
    final Node refNode = NodeParser.getNodeFromExpression(REF_XPATH, this.node).orElseThrow();

    final Element rrefElement = NodeCreator.createElement("akn:rref", this.node);
    rrefElement.setAttribute("GUID", UUID.randomUUID().toString());
    rrefElement.setAttribute("eId", EId.fromMandatoryNode(refNode).value());
    rrefElement.setAttribute("from", destinationFrom.toString());
    rrefElement.setAttribute("upTo", destinationUpTo.toString());

    rrefElement.setTextContent(refNode.getTextContent());

    this.node.replaceChild(rrefElement, refNode);
  }

  /**
   * Replaces an akn:rref with an akn:ref and updates the href and upTo attributes with a new
   * destination href. It also copies for now the text content of the old node.
   *
   * @param destinationHref the new destination href
   */
  public void replaceRrefWithRef(final Href destinationHref) {
    final Node rrefNode = NodeParser.getNodeFromExpression(RREF_XPATH, this.node).orElseThrow();

    final Element refElement = NodeCreator.createElement("akn:ref", this.node);
    refElement.setAttribute("GUID", UUID.randomUUID().toString());
    refElement.setAttribute("eId", EId.fromMandatoryNode(rrefNode).value());
    refElement.setAttribute("href", destinationHref.toString());

    refElement.setTextContent(rrefNode.getTextContent());

    this.node.replaceChild(refElement, rrefNode);
  }
}
