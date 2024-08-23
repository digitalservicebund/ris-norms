package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.w3c.dom.Node;

/**
 * Represents a character range within LDML.de.
 *
 * @param characterRange the value of the character range as {@link String}
 */
public record CharacterRange(String characterRange) {
  static final int ABSOLUTE_POSITION_OF_START = 0;
  static final int ABSOLUTE_POSITION_OF_END = 1;

  /**
   * Returns true if start and end are properly configured
   *
   * @return boolean
   */
  public boolean isEndGreaterStart() {
    String[] splitCharacterRange = characterRange().split("-");
    int start = Integer.parseInt(splitCharacterRange[ABSOLUTE_POSITION_OF_START]);
    int end = Integer.parseInt(splitCharacterRange[ABSOLUTE_POSITION_OF_END]);
    return start < end;
  }

  /**
   * Returns the start value of the character range
   *
   * @return Optional Integer of the start value
   */
  public Integer getStart() {
    String[] splitCharacterRange = characterRange().split("-");
    return Integer.valueOf(splitCharacterRange[ABSOLUTE_POSITION_OF_START]);
  }

  /**
   * Returns the end value of the character range
   *
   * @return Optional Integer of the start value
   */
  public Integer getEnd() {
    String[] splitCharacterRange = characterRange().split("-");
    return Integer.valueOf(splitCharacterRange[ABSOLUTE_POSITION_OF_END]);
  }

  /**
   * Checks whether the character range is valid
   *
   * @return {@link boolean} if the character range is valid
   */
  public boolean isValidCharacterRange() {
    final String regex = "^\\d+-\\d+$";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    return pattern.matcher(characterRange()).matches() && isEndGreaterStart();
  }

  /**
   * Finds the text-content of the range within the given node.
   *
   * @param node the node in which the range should be found
   * @return the text-content of the range
   */
  public String findTextInNode(Node node) {
    var text =
        getNodesInRange(node).stream().map(Node::getTextContent).collect(Collectors.joining());
    node.normalize();
    return text;
  }

  /**
   * Splits the given text node into multiple text nodes and returns the text node that includes the
   * range from start to end of the text content.
   *
   * <p>A call to Node::normalize undoes this.
   *
   * @param textNode A text node
   * @param start start of the text range within the node
   * @param end end (excluding) of the text range within the node
   * @return a text node that represents exactly the range
   */
  private Node extractTextNodeForRange(Node textNode, int start, int end) {
    final var textContent = textNode.getTextContent();

    // text before selection
    if (start != 0) {
      textNode
          .getParentNode()
          .insertBefore(
              textNode.getOwnerDocument().createTextNode(textContent.substring(0, start)),
              textNode);
    }

    // selected text
    final var rangeTextContent = textContent.substring(start, Math.min(textContent.length(), end));
    final var rangeNode =
        textNode
            .getParentNode()
            .insertBefore(textNode.getOwnerDocument().createTextNode(rangeTextContent), textNode);

    // text after selection
    if (end < textContent.length()) {
      textNode
          .getParentNode()
          .insertBefore(
              textNode.getOwnerDocument().createTextNode(textContent.substring(end)), textNode);
    }

    textNode.getParentNode().removeChild(textNode);

    return rangeNode;
  }

  /**
   * Finds the nodes that make up the given range within the given node.
   *
   * <p>SIDE-EFFECT: This might split text-nodes into multiple text nodes to be able to return
   * exactly the range. Please call Node::normalize after you are finished working with the nodes.
   *
   * @param node the node in which the range should be found
   * @return a list of nodes that represent the given range
   */
  public List<Node> getNodesInRange(Node node) throws IndexOutOfBoundsException {
    if (node.getTextContent().length() < getStart()) {
      throw new IndexOutOfBoundsException(
          "Start (%d) is after the end of the text content (length: %d)."
              .formatted(getStart(), node.getTextContent().length()));
    }

    if (node.getTextContent().length() < getEnd()) {
      throw new IndexOutOfBoundsException(
          "End (%d) is after the end of the text content (length: %d)."
              .formatted(getEnd(), node.getTextContent().length()));
    }

    if (getStart() == 0 && getEnd() == node.getTextContent().length()) {
      return List.of(node);
    }

    switch (node.getNodeType()) {
      case Node.TEXT_NODE -> {
        final var rangeNode = extractTextNodeForRange(node, getStart(), getEnd());
        return List.of(rangeNode);
      }
      case Node.ELEMENT_NODE -> {
        int start = getStart();
        int end = getEnd();
        List<Node> nodesInRange = new ArrayList<>();

        for (Node childNode : NodeParser.nodeListToList(node.getChildNodes())) {
          int textContentLength = childNode.getTextContent().length();
          if (start < textContentLength) {
            var newRange =
                new Builder()
                    .start(Math.max(0, start))
                    .end(Math.min(textContentLength, end))
                    .build();

            nodesInRange.addAll(newRange.getNodesInRange(childNode));
          }

          start -= textContentLength;
          end -= textContentLength;

          if (end <= 0) {
            return nodesInRange;
          }
        }

        throw new IndexOutOfBoundsException(
            "Expected end (%d) to be <= 0 after iterating all child nodes.".formatted(end));
      }
      default ->
          throw new UnsupportedOperationException("Unsupported node type: " + node.getNodeType());
    }
  }

  /** Builder for creating a new {@link CharacterRange}. */
  public static class Builder {
    private int start;
    private int end;

    /**
     * Sets the start of the character range
     *
     * @param start the start of the character range
     * @return the builder instance
     */
    public Builder start(int start) {
      this.start = start;
      return this;
    }

    /**
     * Sets the end of the character range
     *
     * @param end the end of the character range
     * @return the builder instance
     */
    public Builder end(int end) {
      this.end = end;
      return this;
    }

    /**
     * Creates an absolute character range with the information provided to this builder.
     *
     * @return a new {@link CharacterRange} instance
     */
    public CharacterRange build() {
      return new CharacterRange(start + "-" + end);
    }
  }

  @Override
  public String toString() {
    return characterRange();
  }
}
