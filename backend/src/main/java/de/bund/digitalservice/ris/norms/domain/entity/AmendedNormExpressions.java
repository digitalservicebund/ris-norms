package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Collection of {@link NormExpression}s that are amended by this Verkündung. Not implementing AbstractCollection or AbstractSet
 * because all operations adding/removing/contains/find are done with just the expression eli (text content of the child nodes)
 * See also: ADR-0016
 * @param element the element node
 */
public record AmendedNormExpressions(Element element) {
  public static final Namespace NAMESPACE = Namespace.METADATEN_NORMS_APPLICATION_MODS;
  public static final String TAG_NAME = "amended-norm-expressions";

  /**
   * Creates a new norms:amended-norm-expressions element and appends it to the given node.
   *
   * @param parentNode the node under which a new {@link AmendedNormExpressions} should be created.
   * @return the newly created {@link AmendedNormExpressions}
   */
  public static AmendedNormExpressions createAndAppend(Node parentNode) {
    return new AmendedNormExpressions(NodeCreator.createElement(NAMESPACE, TAG_NAME, parentNode));
  }

  /**
   * Adds a new  {@link NormExpression} to this node, if the expression eli was not already present, otherwise it updates the attributes of the already existing node
   *
   * @param normExpressionEli                    - the expression eli
   * @param createdByZeitgrenze                  - if the expression was created by a zeitgrenze
   * @param createdByReplacingExistingExpression - if the expression was created replacing an existing one
   */
  public void add(
    NormExpressionEli normExpressionEli,
    boolean createdByZeitgrenze,
    boolean createdByReplacingExistingExpression
  ) {
    find(normExpressionEli).ifPresentOrElse(
        normExpression -> {
          normExpression.setCreatedByZeitgrenze(createdByZeitgrenze);
          normExpression.setCreatedByReplacingExisting(createdByReplacingExistingExpression);
        },
        () ->
          NormExpression.createAndAppend(
            element,
            normExpressionEli,
            createdByZeitgrenze,
            createdByReplacingExistingExpression
          )
      );
  }

  /**
   * Removes the child when it matches the passed expression eli
   *
   * @param expressionEli the expression eli
   * @return true/false
   */
  public boolean remove(final NormExpressionEli expressionEli) {
    return NodeParser.getNodeFromExpression(
      "./%s[text()='%s']".formatted(NormExpression.TAG_NAME, expressionEli.toString()),
      element()
    )
      .map(node -> {
        node.getParentNode().removeChild(node);
        return true;
      })
      .orElse(false);
  }

  /**
   * Checks if the node contains a {@link NormExpression} with the same expression eli
   *
   * @param normExpressionEli - the expression eli
   * @return true/false
   */
  public boolean contains(NormExpressionEli normExpressionEli) {
    return getNormExpressions()
      .stream()
      .anyMatch(f -> f.getNormExpressionEli().equals(normExpressionEli));
  }

  /**
   * Looks for an entry with the same expression eli
   *
   * @param normExpressionEli - the expression eli
   * @return an {@link Optional} with a possible {@link NormExpression}
   */
  public Optional<NormExpression> find(NormExpressionEli normExpressionEli) {
    return getNormExpressions()
      .stream()
      .filter(e -> e.getNormExpressionEli().equals(normExpressionEli))
      .findFirst();
  }

  /**
   * Retrieves the list of norm expressions, sorted by their expression elis
   * @return the list
   */
  public List<NormExpression> getNormExpressions() {
    return NodeParser.getElementsFromExpression(
      "./%s".formatted(NormExpression.TAG_NAME),
      element()
    )
      .stream()
      .map(NormExpression::new)
      .sorted(Comparator.comparing(NormExpression::getNormExpressionEli))
      .toList();
  }
}
