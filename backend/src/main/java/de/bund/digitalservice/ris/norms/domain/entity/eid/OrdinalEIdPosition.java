package de.bund.digitalservice.ris.norms.domain.entity.eid;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import org.w3c.dom.Node;

/**
 * The position information of an EId calculated using the position of the element.
 * @param value the position of the element
 */
public record OrdinalEIdPosition(int value) implements EIdPosition {
  static final String PREFIX = "n";

  static OrdinalEIdPosition findEIdPosition(Node node, EIdPartType eIdPartType) {
    // Special handling for articles as they are counted through the whole document and not only in the context of the parent element
    if (eIdPartType.equals(EIdPartType.ART) && !isInQuotedStructure(node)) {
      return new OrdinalEIdPosition(countPrecedingArticlesNotInQuotedStructure(node) + 1);
    }

    return new OrdinalEIdPosition(countPrecedingSiblingsOfSameType(node, eIdPartType) + 1);
  }

  private static int countPrecedingArticlesNotInQuotedStructure(Node node) {
    var precedingArticleElements = NodeParser.getValueFromMandatoryNodeFromExpression(
      "count(preceding::article)",
      node
    );
    var precedingArticleElementsInQuotedStructure =
      NodeParser.getValueFromMandatoryNodeFromExpression(
        "count(preceding::quotedStructure//article)",
        node
      );

    return (
      Integer.parseInt(precedingArticleElements) -
      Integer.parseInt(precedingArticleElementsInQuotedStructure)
    );
  }

  private static int countPrecedingSiblingsOfSameType(Node node, EIdPartType eIdPartType) {
    var count = 0;
    var previousSibling = node.getPreviousSibling();
    while (previousSibling != null) {
      var eId = EId.fromNode(previousSibling);

      if (eId.isPresent()) {
        var previousSiblingEIdType = eId.get().getParts().getLast().getType();

        if (previousSiblingEIdType.equals(eIdPartType.getName())) {
          count++;
        }
      }

      previousSibling = previousSibling.getPreviousSibling();
    }
    return count;
  }

  private static boolean isInQuotedStructure(Node node) {
    return NodeParser.getValueFromExpression("ancestor::quotedStructure", node).isPresent();
  }

  @Override
  public String toString() {
    return PREFIX + value;
  }

  /**
   * Create a new {@link OrdinalEIdPosition} based on a string representation of the position.
   * @param string the position information starting with n
   * @return the {@link OrdinalEIdPosition} for the string
   */
  public static OrdinalEIdPosition fromString(String string) {
    if (!string.startsWith(PREFIX)) {
      throw new IllegalArgumentException("OrdinalEIdPosition must start with '" + PREFIX + "'");
    }

    return new OrdinalEIdPosition(Integer.parseInt(string.substring(1)));
  }
}
