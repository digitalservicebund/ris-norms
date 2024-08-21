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
   * Finds the nodes that make up the given range within the given node.
   *
   * <p>SIDE-EFFECT: This might split text-nodes into multiple text nodes to be able to return
   * exactly the range. Please call Node::normalize after you are finished working with the nodes.
   *
   * @param node the node in which the range should be found
   * @return a list of nodes that represent the given range
   */
  public List<Node> getNodesInRange(Node node) {
    switch (node.getNodeType()) {
      case Node.TEXT_NODE -> {
        final var textContent = node.getTextContent();

        if (textContent.length() <= getStart()) {
          return List.of();
        }

        if (getStart() == 0 && getEnd() == textContent.length()) {
          return List.of(node);
        }

        node.getParentNode()
            .insertBefore(
                node.getOwnerDocument().createTextNode(textContent.substring(0, getStart())), node);

        final var rangeNode =
            node.getParentNode()
                .insertBefore(
                    node.getOwnerDocument()
                        .createTextNode(
                            textContent.substring(
                                getStart(), Math.min(textContent.length(), getEnd()))),
                    node);

        if (getEnd() < textContent.length()) {
          node.getParentNode()
              .insertBefore(
                  node.getOwnerDocument()
                      .createTextNode(textContent.substring(getEnd(), textContent.length())),
                  node);
        }

        node.getParentNode().removeChild(node);

        return List.of(rangeNode);
      }
      case Node.ELEMENT_NODE -> {
        var start = getStart();
        var end = getEnd();
        List<Node> nodesInRange = new ArrayList();

        for (Node childNode : NodeParser.nodeListToList(node.getChildNodes())) {
          if (start <= 0 && end >= childNode.getTextContent().length()) {
            nodesInRange.add(childNode);
          } else {
            var newRange =
                new Builder()
                    .start(Math.max(0, start))
                    .end(Math.min(childNode.getTextContent().length(), end))
                    .build();

            nodesInRange.addAll(newRange.getNodesInRange(childNode));
          }

          start -= childNode.getTextContent().length();
          end -= childNode.getTextContent().length();

          if (end <= 0) {
            return nodesInRange;
          }
        }

        throw new RuntimeException("Character Range not found");
      }
      default -> {
        throw new RuntimeException("Unexpected node type");
      }
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
